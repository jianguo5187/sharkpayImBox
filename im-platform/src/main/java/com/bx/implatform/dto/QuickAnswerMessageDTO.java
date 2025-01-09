package com.bx.implatform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel("快捷回复消息DTO")
public class QuickAnswerMessageDTO {

    @ApiModelProperty(value = "快捷回复信息id")
    private Long id;

    @Length(max = 1024, message = "问题不得大于1024")
    @NotEmpty(message = "问题不可为空")
    @ApiModelProperty(value = "问题内容")
    private String content;
}
