package com.shop.service;

import com.shop.dto.ReplyFormDto;
import com.shop.entity.Reply;
import com.shop.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    public Long postReply(ReplyFormDto replyFormDto) {

        Reply reply = replyFormDto.createReply();
        replyRepository.save(reply);

        return reply.getId();
    }
}
