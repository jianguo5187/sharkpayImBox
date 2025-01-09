package com.bx.implatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bx.implatform.dto.QuickAnswerMessageDTO;
import com.bx.implatform.entity.QuickAnswerMessage;
import com.bx.implatform.vo.QuickAnswerMessageVO;

import java.util.List;

public interface IQuickAnswerMessageService extends IService<QuickAnswerMessage> {
    List<QuickAnswerMessageVO> findWelcomeMessage();

    List<QuickAnswerMessageVO> findAllActivedQuickAnswerMessage();

    List<QuickAnswerMessageVO> findAutoMessage(String sendMessage);

    void saveOrUpdateQuickAnswerMessage(QuickAnswerMessageDTO dto);

    void deleteQuickAnswerMessage(Long id);
}
