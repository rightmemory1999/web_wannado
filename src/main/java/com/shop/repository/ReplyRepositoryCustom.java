package com.shop.repository;

import com.shop.dto.ReplySearchDto;
import com.shop.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyRepositoryCustom {

    Page<Reply> getReplyListPage(ReplySearchDto replySearchDto, Pageable pageable);
}
