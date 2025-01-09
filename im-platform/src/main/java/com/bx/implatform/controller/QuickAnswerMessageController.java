package com.bx.implatform.controller;

import com.bx.implatform.dto.QuickAnswerMessageDTO;
import com.bx.implatform.result.Result;
import com.bx.implatform.result.ResultUtils;
import com.bx.implatform.service.IQuickAnswerMessageService;
import com.bx.implatform.vo.QuickAnswerMessageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Api(tags = "设置快捷回复内容")
@RestController
@RequestMapping("/quickAnswerMessage")
@RequiredArgsConstructor
public class QuickAnswerMessageController {
    private final IQuickAnswerMessageService QuickAnswerMessageService;


    @PostMapping("/saveQuickAnswerMessage")
    @ApiOperation(value = "保存快捷回复设置信息")
    public Result<Long>  saveQuickAnswerMessage(@Valid @RequestBody QuickAnswerMessageDTO dto){
        QuickAnswerMessageService.saveOrUpdateQuickAnswerMessage(dto);
        return ResultUtils.success();
    }

    @DeleteMapping("/deleteQuickAnswerMessage/{id}")
    @ApiOperation(value = "删除快捷回复信息")
    public Result deleteQuickAnswerMessage(@NotEmpty(message = "信息id不可为空") @PathVariable("id") Long id) {
        QuickAnswerMessageService.deleteQuickAnswerMessage(id);
        return ResultUtils.success();
    }

    @GetMapping("/loadAllQuickAnswerMessage")
    @ApiOperation(value = "拉取快捷回复设置消息")
    public Result<List<QuickAnswerMessageVO>>  loadAllQuickAnswerMessage(){
        return ResultUtils.success(QuickAnswerMessageService.findAllActivedQuickAnswerMessage());
    }
}
