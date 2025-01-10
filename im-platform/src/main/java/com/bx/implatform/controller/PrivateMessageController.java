package com.bx.implatform.controller;

import com.bx.implatform.dto.NoAuthNoReadCntDto;
import com.bx.implatform.dto.PrivateMessageDTO;
import com.bx.implatform.dto.PrivateReadedMessageDTO;
import com.bx.implatform.result.Result;
import com.bx.implatform.result.ResultUtils;
import com.bx.implatform.service.IPrivateMessageService;
import com.bx.implatform.vo.PrivateMessageVO;
import com.bx.implatform.vo.PrivateReadedMessageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "私聊消息")
@RestController
@RequestMapping("/message/private")
@RequiredArgsConstructor
public class PrivateMessageController {

    private final IPrivateMessageService privateMessageService;

    @PostMapping("/send")
    @ApiOperation(value = "发送消息", notes = "发送私聊消息")
    public Result<Long> sendMessage(@Valid @RequestBody PrivateMessageDTO vo) {
        return ResultUtils.success(privateMessageService.sendMessage(vo));
    }


    @PostMapping("/sendReaded")
    @ApiOperation(value = "发送已读消息", notes = "发送私聊消息")
    public Result<PrivateReadedMessageVO> sendReadedMessage(@Valid @RequestBody PrivateReadedMessageDTO vo) {
        return ResultUtils.success(privateMessageService.sendReadedMessage(vo));
    }

    @DeleteMapping("/recall/{id}")
    @ApiOperation(value = "撤回消息", notes = "撤回私聊消息")
    public Result<Long> recallMessage(@NotNull(message = "消息id不能为空") @PathVariable Long id) {
        privateMessageService.recallMessage(id);
        return ResultUtils.success();
    }


    @GetMapping("/loadMessage")
    @ApiOperation(value = "拉取消息", notes = "拉取消息,一次最多拉取100条")
    public Result<List<PrivateMessageVO>> loadMessage(@RequestParam Long minId) {
        return ResultUtils.success(privateMessageService.loadMessage(minId));
    }

    @GetMapping("/pullOfflineMessage")
    @ApiOperation(value = "拉取离线消息", notes = "拉取离线消息,消息将通过webscoket异步推送")
    public Result pullOfflineMessage(@RequestParam Long minId) {
        privateMessageService.pullOfflineMessage(minId);
        return ResultUtils.success();
    }

    @PutMapping("/readed")
    @ApiOperation(value = "消息已读", notes = "将会话中接收的消息状态置为已读")
    public Result readedMessage(@RequestParam Long friendId) {
        privateMessageService.readedMessage(friendId);
        return ResultUtils.success();
    }

    @GetMapping("/maxReadedId")
    @ApiOperation(value = "获取最大已读消息的id",notes="获取某个会话中已读消息的最大id")
    public Result<Long> getMaxReadedId(@RequestParam Long friendId){
        return ResultUtils.success(privateMessageService.getMaxReadedId(friendId));
    }

    @GetMapping("/history")
    @ApiOperation(value = "查询聊天记录", notes = "查询聊天记录")
    public Result<List<PrivateMessageVO>> recallMessage(@NotNull(message = "好友id不能为空") @RequestParam Long friendId,
                                                        @NotNull(message = "页码不能为空") @RequestParam Long page,
                                                        @NotNull(message = "size不能为空") @RequestParam Long size) {
        return ResultUtils.success(privateMessageService.findHistoryMessage(friendId, page, size));
    }

    @GetMapping("/noReadCnt")
    @ApiOperation(value = "未读件数", notes = "拉取离线消息,消息将通过webscoket异步推送")
    public Result noReadCnt() {
        return ResultUtils.success(privateMessageService.getNoReadCnt());
    }

    @DeleteMapping("/onDeleteAllChatRecord")
    @ApiOperation(value = "删除聊天记录", notes = "删除聊天记录")
    public Result onDeleteAllChatRecord() {
        privateMessageService.onDeleteAllChatRecord();
        return ResultUtils.success();
    }

    @DeleteMapping("/deleteOneDayBeforeMessage")
    @ApiOperation(value = "删除1天前数据", notes = "删除1天前数据")
    public Result deleteOneDayBeforeMessage() {
        privateMessageService.deleteOneDayBeforeMessage();
        return ResultUtils.success();
    }

    @DeleteMapping("/deleteThreeDayBeforeMessage")
    @ApiOperation(value = "删除3天前数据", notes = "删除3天前数据")
    public Result deleteThreeDayBeforeMessage() {
        privateMessageService.deleteThreeDayBeforeMessage();
        return ResultUtils.success();
    }
}

