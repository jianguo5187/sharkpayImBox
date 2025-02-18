package com.bx.implatform.controller;

import com.bx.implatform.entity.Friend;
import com.bx.implatform.result.Result;
import com.bx.implatform.result.ResultUtils;
import com.bx.implatform.service.IFriendService;
import com.bx.implatform.service.IUserService;
import com.bx.implatform.session.SessionContext;
import com.bx.implatform.vo.FriendVO;
import com.bx.implatform.vo.UpdateFriendRemarkVO;
import com.bx.implatform.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "好友")
@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {

    private final IFriendService friendService;
    private final IUserService userService;

    @GetMapping("/list")
    @ApiOperation(value = "好友列表", notes = "获取好友列表")
    public Result<List<FriendVO>> findFriends() {
        List<Friend> friends = friendService.findFriendByUserId(SessionContext.getSession().getUserId());
        List<FriendVO> vos = friends.stream().map(f -> {
            FriendVO vo = new FriendVO();
            vo.setId(f.getFriendId());
            vo.setHeadImage(f.getFriendHeadImage());
            if(StringUtils.isNotEmpty(f.getRemarkName())){
                vo.setNickName(f.getRemarkName());
            }else{
                vo.setNickName(f.getFriendNickName());
            }
            vo.setRemarkName(f.getRemarkName());
            UserVO friendUser = userService.findUserById(f.getFriendId());
            vo.setUserIpAddress(friendUser.getUserIpAddress());
            vo.setThirdUserId(friendUser.getThirdUserId());
            return vo;
        }).collect(Collectors.toList());
        return ResultUtils.success(vos);
    }


    @PostMapping("/add")
    @ApiOperation(value = "添加好友", notes = "双方建立好友关系")
    public Result addFriend(@NotEmpty(message = "好友id不可为空") @RequestParam("friendId") Long friendId) {
        friendService.addFriend(friendId);
        return ResultUtils.success();
    }

    @GetMapping("/find/{friendId}")
    @ApiOperation(value = "查找好友信息", notes = "查找好友信息")
    public Result<FriendVO> findFriend(@NotEmpty(message = "好友id不可为空") @PathVariable("friendId") Long friendId) {
        return ResultUtils.success(friendService.findFriend(friendId));
    }


    @DeleteMapping("/delete/{friendId}")
    @ApiOperation(value = "删除好友", notes = "解除好友关系")
    public Result delFriend(@NotEmpty(message = "好友id不可为空") @PathVariable("friendId") Long friendId) {
        friendService.delFriend(friendId);
        return ResultUtils.success();
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新好友信息", notes = "更新好友头像或昵称")
    public Result modifyFriend(@Valid @RequestBody FriendVO vo) {
        friendService.update(vo);
        return ResultUtils.success();
    }

    @PutMapping("/updateRemark")
    @ApiOperation(value = "更新好友备注", notes = "更新好友备注")
    public Result modifyFriendRemark(@Valid @RequestBody UpdateFriendRemarkVO vo) {
        friendService.modifyFriendRemark(vo);
        return ResultUtils.success();
    }


}

