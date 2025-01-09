package com.bx.implatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bx.implatform.dto.QuickAnswerMessageDTO;
import com.bx.implatform.entity.QuickAnswerMessage;
import com.bx.implatform.mapper.QuickAnswerMessageMapper;
import com.bx.implatform.service.IQuickAnswerMessageService;
import com.bx.implatform.util.BeanUtils;
import com.bx.implatform.vo.QuickAnswerMessageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuickAnswerMessageServiceImpl extends ServiceImpl<QuickAnswerMessageMapper, QuickAnswerMessage> implements IQuickAnswerMessageService {

    @Override
    public List<QuickAnswerMessageVO> findWelcomeMessage() {
        LambdaQueryWrapper<QuickAnswerMessage> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(QuickAnswerMessage::getStatus,0)
                .orderByAsc(QuickAnswerMessage::getId);
        List<QuickAnswerMessage> QuickAnswerMessages = this.list(queryWrapper);
        List<QuickAnswerMessageVO> QuickAnswerMessageInfos = QuickAnswerMessages.stream().map(m -> BeanUtils.copyProperties(m, QuickAnswerMessageVO.class)).collect(Collectors.toList());

        return QuickAnswerMessageInfos;
    }

    @Override
    public List<QuickAnswerMessageVO> findAllActivedQuickAnswerMessage() {
        LambdaQueryWrapper<QuickAnswerMessage> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(QuickAnswerMessage::getStatus,0).orderByAsc(QuickAnswerMessage::getId);
        List<QuickAnswerMessage> QuickAnswerMessages = this.list(queryWrapper);
        List<QuickAnswerMessageVO> QuickAnswerMessageInfos = QuickAnswerMessages.stream().map(m -> BeanUtils.copyProperties(m, QuickAnswerMessageVO.class)).collect(Collectors.toList());

        return QuickAnswerMessageInfos;
    }

    @Override
    public List<QuickAnswerMessageVO> findAutoMessage(String sendMessage) {
        LambdaQueryWrapper<QuickAnswerMessage> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(QuickAnswerMessage::getStatus,0)
                .eq(QuickAnswerMessage::getContent,sendMessage)
                .orderByAsc(QuickAnswerMessage::getId);
        List<QuickAnswerMessage> QuickAnswerMessages = this.list(queryWrapper);
        List<QuickAnswerMessageVO> QuickAnswerMessageInfos = QuickAnswerMessages.stream().map(m -> BeanUtils.copyProperties(m, QuickAnswerMessageVO.class)).collect(Collectors.toList());

        return QuickAnswerMessageInfos;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateQuickAnswerMessage(QuickAnswerMessageDTO dto) {
//        QuickAnswerMessage QuickAnswerMessage = BeanUtils.copyProperties(dto,QuickAnswerMessage.class);
        if(dto.getId().compareTo(0l) > 0){
            QuickAnswerMessage QuickAnswerMessage = this.getById(dto.getId());
            QuickAnswerMessage.setContent(dto.getContent());
            this.updateById(QuickAnswerMessage);
        }else {
            QuickAnswerMessage QuickAnswerMessage = new QuickAnswerMessage();
            QuickAnswerMessage.setContent(dto.getContent());
            QuickAnswerMessage.setStatus(0);
            QuickAnswerMessage.setCreatedTime(new Date());
            this.save(QuickAnswerMessage);
        }
    }

    @Override
    public void deleteQuickAnswerMessage(Long id) {
        this.removeById(id);
    }
}
