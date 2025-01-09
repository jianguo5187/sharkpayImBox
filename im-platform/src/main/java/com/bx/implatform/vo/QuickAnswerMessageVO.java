package com.bx.implatform.vo;

import com.bx.imcommon.serializer.DateToLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("快捷回复VO")
public class QuickAnswerMessageVO {

    @ApiModelProperty(value = " 消息id")
    private Long id;

    @ApiModelProperty(value = " 发送内容")
    private String content;

    @ApiModelProperty(value = " 状态 0:有效 1:无效")
    private Integer status;

    @ApiModelProperty(value = " 创建时间")
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date createdTime;
}
