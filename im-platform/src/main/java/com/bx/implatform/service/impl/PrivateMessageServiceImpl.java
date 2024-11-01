package com.bx.implatform.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bx.imclient.IMClient;
import com.bx.imcommon.contant.IMConstant;
import com.bx.imcommon.enums.IMTerminalType;
import com.bx.imcommon.model.IMGroupMessage;
import com.bx.imcommon.model.IMPrivateMessage;
import com.bx.imcommon.model.IMUserInfo;
import com.bx.implatform.dto.NoAuthNoReadCntDto;
import com.bx.implatform.dto.PrivateMessageDTO;
import com.bx.implatform.entity.Friend;
import com.bx.implatform.entity.PrivateMessage;
import com.bx.implatform.entity.User;
import com.bx.implatform.enums.MessageStatus;
import com.bx.implatform.enums.MessageType;
import com.bx.implatform.enums.ResultCode;
import com.bx.implatform.exception.GlobalException;
import com.bx.implatform.mapper.PrivateMessageMapper;
import com.bx.implatform.service.IDefaultMessageService;
import com.bx.implatform.service.IFriendService;
import com.bx.implatform.service.IPrivateMessageService;
import com.bx.implatform.service.IUserService;
import com.bx.implatform.session.SessionContext;
import com.bx.implatform.session.UserSession;
import com.bx.implatform.util.BeanUtils;
import com.bx.implatform.util.SensitiveFilterUtil;
import com.bx.implatform.vo.DefaultMessageVO;
import com.bx.implatform.vo.GroupMessageVO;
import com.bx.implatform.vo.PrivateMessageVO;
import com.bx.implatform.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivateMessageServiceImpl extends ServiceImpl<PrivateMessageMapper, PrivateMessage> implements IPrivateMessageService {

    private final IFriendService friendService;
    private final IMClient imClient;
    private final SensitiveFilterUtil sensitiveFilterUtil;
    private final IUserService userService;
    private final IDefaultMessageService defaultMessageService;

    @Override
    public Long sendMessage(PrivateMessageDTO dto) {
        UserSession session = SessionContext.getSession();
        Boolean isFriends = friendService.isFriend(session.getUserId(), dto.getRecvId());
        if (Boolean.FALSE.equals(isFriends)) {
            throw new GlobalException(ResultCode.PROGRAM_ERROR, "您已不是对方好友，无法发送消息");
        }
        UserVO sendUser = userService.findUserById(session.getUserId());
        // 保存消息
        PrivateMessage msg = BeanUtils.copyProperties(dto, PrivateMessage.class);
        msg.setSendId(session.getUserId());
        msg.setStatus(MessageStatus.UNSEND.code());
        msg.setSendTime(new Date());
        this.save(msg);
        // 过滤消息内容
        String content = sensitiveFilterUtil.filter(dto.getContent());
        msg.setContent(content);
        // 推送消息
        PrivateMessageVO msgInfo = BeanUtils.copyProperties(msg, PrivateMessageVO.class);
        IMPrivateMessage<PrivateMessageVO> sendMessage = new IMPrivateMessage<>();

        // 玩家的时候固定推送PC端
        if(sendUser.getType() == 2){
            sendMessage.setSender(new IMUserInfo(session.getUserId(), 1));
        }else{
            sendMessage.setSender(new IMUserInfo(session.getUserId(), session.getTerminal()));
        }
        sendMessage.setRecvId(msgInfo.getRecvId());
        sendMessage.setSendToSelf(true);
        sendMessage.setData(msgInfo);
        sendMessage.setSendResult(true);
        imClient.sendPrivateMessage(sendMessage);
        log.info("发送私聊消息，发送id:{},接收id:{}，内容:{}", session.getUserId(), dto.getRecvId(), dto.getContent());

        //只判断玩家发送的信息，去自动回复
        if(sendUser.getType() == 2){
            //获取自动回复信息
            List<DefaultMessageVO> defaultMessages = defaultMessageService.findAutoMessage(content);
            for(DefaultMessageVO defaultMessageVO : defaultMessages){
                // 保存消息
                PrivateMessage autoAnswerMsg = BeanUtils.copyProperties(dto, PrivateMessage.class);
                autoAnswerMsg.setSendId(dto.getRecvId());
                autoAnswerMsg.setRecvId(session.getUserId());
                autoAnswerMsg.setContent(defaultMessageVO.getAnswerContent());
                autoAnswerMsg.setStatus(MessageStatus.UNSEND.code());
                autoAnswerMsg.setSendTime(new Date());
                this.save(autoAnswerMsg);
                // 过滤消息内容
                String autoAnswerContent = sensitiveFilterUtil.filter(defaultMessageVO.getAnswerContent());
                autoAnswerMsg.setContent(autoAnswerContent);
                // 推送消息
                PrivateMessageVO autoAnswerMsgInfo = BeanUtils.copyProperties(autoAnswerMsg, PrivateMessageVO.class);
                IMPrivateMessage<PrivateMessageVO> autoAnswerSendMessage = new IMPrivateMessage<>();
                autoAnswerSendMessage.setSender(new IMUserInfo(dto.getRecvId(), 1));
                autoAnswerSendMessage.setRecvId(session.getUserId());
                autoAnswerSendMessage.setSendToSelf(true);
                autoAnswerSendMessage.setData(autoAnswerMsgInfo);
                autoAnswerSendMessage.setSendResult(true);
                imClient.sendPrivateMessage(autoAnswerSendMessage);
                log.info("发送私聊消息，发送id:{},接收id:{}，内容:{}", dto.getRecvId(), session.getUserId(), defaultMessageVO.getAnswerContent());
            }
        }
        return msg.getId();
    }

    @Override
    public void recallMessage(Long id) {
        UserSession session = SessionContext.getSession();
        PrivateMessage msg = this.getById(id);
        if (Objects.isNull(msg)) {
            throw new GlobalException(ResultCode.PROGRAM_ERROR, "消息不存在");
        }
        if (!msg.getSendId().equals(session.getUserId())) {
            throw new GlobalException(ResultCode.PROGRAM_ERROR, "这条消息不是由您发送,无法撤回");
        }
        if (System.currentTimeMillis() - msg.getSendTime().getTime() > IMConstant.ALLOW_RECALL_SECOND * 1000) {
            throw new GlobalException(ResultCode.PROGRAM_ERROR, "消息已发送超过5分钟，无法撤回");
        }
        // 修改消息状态
        msg.setStatus(MessageStatus.RECALL.code());
        this.updateById(msg);
        // 推送消息
        PrivateMessageVO msgInfo = BeanUtils.copyProperties(msg, PrivateMessageVO.class);
        msgInfo.setType(MessageType.RECALL.code());
        msgInfo.setSendTime(new Date());
        msgInfo.setContent("对方撤回了一条消息");

        IMPrivateMessage<PrivateMessageVO> sendMessage = new IMPrivateMessage<>();
        sendMessage.setSender(new IMUserInfo(session.getUserId(), session.getTerminal()));
        sendMessage.setRecvId(msgInfo.getRecvId());
        sendMessage.setSendToSelf(false);
        sendMessage.setData(msgInfo);
        sendMessage.setSendResult(false);
        imClient.sendPrivateMessage(sendMessage);

        // 推给自己其他终端
        msgInfo.setContent("你撤回了一条消息");
        sendMessage.setSendToSelf(true);
        sendMessage.setRecvTerminals(Collections.emptyList());
        imClient.sendPrivateMessage(sendMessage);
        log.info("撤回私聊消息，发送id:{},接收id:{}，内容:{}", msg.getSendId(), msg.getRecvId(), msg.getContent());
    }


    @Override
    public List<PrivateMessageVO> findHistoryMessage(Long friendId, Long page, Long size) {
        page = page > 0 ? page : 1;
        size = size > 0 ? size : 10;
        Long userId = SessionContext.getSession().getUserId();
        long stIdx = (page - 1) * size;
        QueryWrapper<PrivateMessage> wrapper = new QueryWrapper<>();
        wrapper.lambda().and(wrap -> wrap.and(
                wp -> wp.eq(PrivateMessage::getSendId, userId)
                        .eq(PrivateMessage::getRecvId, friendId))
                .or(wp -> wp.eq(PrivateMessage::getRecvId, userId)
                        .eq(PrivateMessage::getSendId, friendId)))
                .ne(PrivateMessage::getStatus, MessageStatus.RECALL.code())
                .orderByDesc(PrivateMessage::getId)
                .last("limit " + stIdx + "," + size);

        List<PrivateMessage> messages = this.list(wrapper);
        List<PrivateMessageVO> messageInfos = messages.stream().map(m -> BeanUtils.copyProperties(m, PrivateMessageVO.class)).collect(Collectors.toList());
        log.info("拉取聊天记录，用户id:{},好友id:{}，数量:{}", userId, friendId, messageInfos.size());
        return messageInfos;
    }


    @Override
    public List<PrivateMessageVO> loadMessage(Long minId) {
        UserSession session = SessionContext.getSession();
        List<Friend> friends = friendService.findFriendByUserId(session.getUserId());
        if (friends.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> friendIds = friends.stream().map(Friend::getFriendId).collect(Collectors.toList());
        // 获取当前用户的消息
        LambdaQueryWrapper<PrivateMessage> queryWrapper = Wrappers.lambdaQuery();
        Date minDate = null;
//        if(session.getUserId() == 1 || session.getUserId() == 2){
//            // 客服拉取最近取最近1个月的10条消息
//            minDate = DateUtils.addMonths(new Date(), -1);
//        }else{
//            // 客服拉取最近取最近2天的10条消息
//            minDate = DateUtils.addDays(new Date(), -1);
//        }
        queryWrapper.gt(PrivateMessage::getId, minId)
//                .ge(PrivateMessage::getSendTime, minDate)
                .ne(PrivateMessage::getStatus, MessageStatus.RECALL.code())
                .and(wrap -> wrap.and(
                        wp -> wp.eq(PrivateMessage::getSendId, session.getUserId())
                                .in(PrivateMessage::getRecvId, friendIds))
                        .or(wp -> wp.eq(PrivateMessage::getRecvId, session.getUserId())
                                .in(PrivateMessage::getSendId, friendIds)))
                .orderByAsc(PrivateMessage::getId)
                .last("limit 10");

        List<PrivateMessage> messages = this.list(queryWrapper);
        // 更新发送状态
        List<Long> ids = messages.stream()
                .filter(m -> !m.getSendId().equals(session.getUserId()) && m.getStatus().equals(MessageStatus.UNSEND.code()))
                .map(PrivateMessage::getId)
                .collect(Collectors.toList());
        if (!ids.isEmpty()) {
            LambdaUpdateWrapper<PrivateMessage> updateWrapper = Wrappers.lambdaUpdate();
            updateWrapper.in(PrivateMessage::getId, ids)
                    .set(PrivateMessage::getStatus, MessageStatus.SENDED.code());
            this.update(updateWrapper);
        }
        log.info("拉取消息，用户id:{},数量:{}", session.getUserId(), messages.size());
        return messages.stream().map(m -> BeanUtils.copyProperties(m, PrivateMessageVO.class)).collect(Collectors.toList());
    }

    @Override
    public void pullOfflineMessage(Long minId) {
        UserSession session = SessionContext.getSession();
        if(!imClient.isOnline(session.getUserId())){
            throw new GlobalException(ResultCode.PROGRAM_ERROR, "网络连接失败，无法拉取离线消息");
        }

        // 查询用户好友列表
        List<Friend> friends = friendService.findFriendByUserId(session.getUserId());
        if (friends.isEmpty()) {
            return;
        }
        // 开启加载中标志
        this.sendLoadingMessage(true);
        List<Long> friendIds = friends.stream().map(Friend::getFriendId).collect(Collectors.toList());
        // 获取当前用户的消息
        LambdaQueryWrapper<PrivateMessage> queryWrapper = Wrappers.lambdaQuery();
        Date minDate = null;

        if(session.getUserId() == 1 || session.getUserId() == 2){
            // 客服拉取最近取最近1个月的10条消息
            minDate = DateUtils.addMonths(new Date(), -1);
        }else{
            // 客服拉取最近取最近2天的10条消息
            minDate = DateUtils.addDays(new Date(), -2);
        }
        queryWrapper.gt(PrivateMessage::getId, minId)
            .ge(PrivateMessage::getSendTime, minDate)
            .ne(PrivateMessage::getStatus, MessageStatus.RECALL.code())
            .and(wrap -> wrap.and(
                    wp -> wp.eq(PrivateMessage::getSendId, session.getUserId())
                        .in(PrivateMessage::getRecvId, friendIds))
                .or(wp -> wp.eq(PrivateMessage::getRecvId, session.getUserId())
                    .in(PrivateMessage::getSendId, friendIds)))
            .orderByDesc(PrivateMessage::getId)
//            .last("limit 10")
        ;
        List<PrivateMessage> messages = this.list(queryWrapper);
        // 消息顺序从小到大
        CollectionUtil.reverse(messages);
        // 推送消息
        for(PrivateMessage m:messages ){
            PrivateMessageVO vo = BeanUtils.copyProperties(m, PrivateMessageVO.class);
            IMPrivateMessage<PrivateMessageVO> sendMessage = new IMPrivateMessage<>();
            sendMessage.setSender(new IMUserInfo(m.getSendId(), IMTerminalType.WEB.code()));
            sendMessage.setRecvId(session.getUserId());
            sendMessage.setRecvTerminals(Arrays.asList(session.getTerminal()));
            sendMessage.setSendToSelf(false);
            sendMessage.setData(vo);
            sendMessage.setSendResult(true);
            imClient.sendPrivateMessage(sendMessage);
        }
        // 关闭加载中标志
        this.sendLoadingMessage(false);
        log.info("拉取私聊消息，用户id:{},数量:{}", session.getUserId(), messages.size());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void readedMessage(Long friendId) {
        UserSession session = SessionContext.getSession();
        // 推送消息给自己，清空会话列表上的已读数量
        PrivateMessageVO msgInfo = new PrivateMessageVO();
        msgInfo.setType(MessageType.READED.code());
        msgInfo.setSendId(session.getUserId());
        msgInfo.setRecvId(friendId);
        IMPrivateMessage<PrivateMessageVO> sendMessage = new IMPrivateMessage<>();
        sendMessage.setData(msgInfo);
        sendMessage.setSender(new IMUserInfo(session.getUserId(), session.getTerminal()));
        sendMessage.setRecvId(session.getUserId());
        sendMessage.setSendToSelf(false);
        sendMessage.setSendResult(false);
        imClient.sendPrivateMessage(sendMessage);
        // 推送回执消息给对方，更新已读状态
        msgInfo = new PrivateMessageVO();
        msgInfo.setType(MessageType.RECEIPT.code());
        msgInfo.setSendId(session.getUserId());
        msgInfo.setRecvId(friendId);
        sendMessage = new IMPrivateMessage<>();
        sendMessage.setSender(new IMUserInfo(session.getUserId(), session.getTerminal()));
        sendMessage.setRecvId(friendId);
        sendMessage.setSendToSelf(false);
        sendMessage.setSendResult(false);
        sendMessage.setData(msgInfo);
        imClient.sendPrivateMessage(sendMessage);
        // 修改消息状态为已读
        LambdaUpdateWrapper<PrivateMessage> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(PrivateMessage::getSendId, friendId)
                .eq(PrivateMessage::getRecvId, session.getUserId())
                .eq(PrivateMessage::getStatus, MessageStatus.SENDED.code())
                .set(PrivateMessage::getStatus, MessageStatus.READED.code());
        this.update(updateWrapper);
        log.info("消息已读，接收方id:{},发送方id:{}", session.getUserId(), friendId);
    }


    @Override
    public Long getMaxReadedId(Long friendId) {
        UserSession session = SessionContext.getSession();
        LambdaQueryWrapper<PrivateMessage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(PrivateMessage::getSendId, session.getUserId())
                .eq(PrivateMessage::getRecvId, friendId)
                .eq(PrivateMessage::getStatus, MessageStatus.READED.code())
                .orderByDesc(PrivateMessage::getId)
                .select(PrivateMessage::getId)
                .last("limit 1");
        PrivateMessage message = this.getOne(wrapper);
        if(Objects.isNull(message)){
            return -1L;
        }
        return message.getId();
    }

    private void sendLoadingMessage(Boolean isLoadding){
        UserSession session = SessionContext.getSession();
        PrivateMessageVO msgInfo = new PrivateMessageVO();
        msgInfo.setType(MessageType.LOADDING.code());
        msgInfo.setContent(isLoadding.toString());
        IMPrivateMessage sendMessage = new IMPrivateMessage<>();
        sendMessage.setSender(new IMUserInfo(session.getUserId(), session.getTerminal()));
        sendMessage.setRecvId(session.getUserId());
        sendMessage.setRecvTerminals(Arrays.asList(session.getTerminal()));
        sendMessage.setData(msgInfo);
        sendMessage.setSendToSelf(false);
        sendMessage.setSendResult(false);
        imClient.sendPrivateMessage(sendMessage);
    }

    @Override
    public Integer noAuthNoReadCnt(NoAuthNoReadCntDto vo) {
        if(vo.getKefuUserId() == null || StringUtils.isEmpty(vo.getRecvUserId())){
            throw new GlobalException(ResultCode.PROGRAM_ERROR, "客服ID或客户ID不能为空");
        }

        UserVO sendUser = userService.findUserById(vo.getKefuUserId());
        if(sendUser == null){
            throw new GlobalException(ResultCode.PROGRAM_ERROR, "客服用户不存在，请联系管理员。");
        }

        User recvUser = userService.findUserByThirdUserId(vo.getRecvUserId());
        if(recvUser == null){
            throw new GlobalException(ResultCode.PROGRAM_ERROR, "客户用户不存在，请联系管理员。");
        }

        // 获取当前用户的消息
        LambdaQueryWrapper<PrivateMessage> queryWrapper = Wrappers.lambdaQuery();
        List<Integer> status = new ArrayList<>();
        status.add(MessageStatus.SENDED.code());
        status.add(MessageStatus.UNSEND.code());
        // 只能拉取最近1个月的
        queryWrapper.eq(PrivateMessage::getSendId, sendUser.getId())
                .eq(PrivateMessage::getRecvId, recvUser.getId())
                .in(PrivateMessage::getStatus,status);
        List<PrivateMessage> messages = this.list(queryWrapper);
        return messages.size();
    }

    @Override
    public Integer noAuthKefuNoReadCnt(NoAuthNoReadCntDto vo) {
        if(vo.getKefuUserId() == null){
            throw new GlobalException(ResultCode.PROGRAM_ERROR, "客服ID不能为空");
        }

        UserVO sendUser = userService.findUserById(vo.getKefuUserId());
        if(sendUser == null){
            throw new GlobalException(ResultCode.PROGRAM_ERROR, "客服用户不存在，请联系管理员。");
        }

        // 获取当前用户的消息
        LambdaQueryWrapper<PrivateMessage> queryWrapper = Wrappers.lambdaQuery();
        List<Integer> status = new ArrayList<>();
        status.add(MessageStatus.SENDED.code());
        status.add(MessageStatus.UNSEND.code());
        queryWrapper.eq(PrivateMessage::getRecvId, sendUser.getId())
                .in(PrivateMessage::getStatus,status);
        List<PrivateMessage> messages = this.list(queryWrapper);
        return messages.size();
    }

    @Override
    public Integer getNoReadCnt() {
        UserSession session = SessionContext.getSession();
        // 获取当前用户的消息
        LambdaQueryWrapper<PrivateMessage> queryWrapper = Wrappers.lambdaQuery();
        List<Integer> status = new ArrayList<>();
        status.add(MessageStatus.SENDED.code());
        status.add(MessageStatus.UNSEND.code());
        // 只能拉取最近1个月的
        queryWrapper.eq(PrivateMessage::getRecvId, session.getUserId())
                .in(PrivateMessage::getStatus,status);
//        List<PrivateMessage> messages = this.count(queryWrapper);
        return this.count(queryWrapper);
    }

    @Override
    public void deleteOneDayBeforeMessage() {
        LambdaQueryWrapper<PrivateMessage> wrapper = Wrappers.lambdaQuery();
        Date minDate = DateUtils.addDays(new Date(), -1);
        wrapper.lt(PrivateMessage::getSendTime, minDate);
        this.remove(wrapper);
    }

    @Override
    public void deleteThreeDayBeforeMessage() {
        LambdaQueryWrapper<PrivateMessage> wrapper = Wrappers.lambdaQuery();
        Date minDate = DateUtils.addDays(new Date(), -3);
        wrapper.lt(PrivateMessage::getSendTime, minDate);
        this.remove(wrapper);
    }
}
