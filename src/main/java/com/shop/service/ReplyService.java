package com.shop.service;

import com.shop.dto.ReplyFormDto;
import com.shop.dto.ReplySearchDto;
import com.shop.entity.Reply;
import com.shop.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

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

    @Transactional(readOnly = true)
    public ReplyFormDto getReplyDtl(Long replyId) {

        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(EntityNotFoundException::new);

        ReplyFormDto replyFormDto = ReplyFormDto.of(reply);

        return replyFormDto;
    }

    public Long updateReply(ReplyFormDto replyFormDto) {
        Reply reply = replyRepository.findById(replyFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        reply.updateReply(replyFormDto);

        return reply.getId();
    }

    @Transactional(readOnly = true)
    public Page<Reply> replyPage(ReplySearchDto replySearchDto, Pageable pageable) {
        return replyRepository.getReplyListPage(replySearchDto, pageable);
    }
}
