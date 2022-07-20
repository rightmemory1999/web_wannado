package com.shop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.dto.ReplySearchDto;
import com.shop.entity.QItem;
import com.shop.entity.QReply;
import com.shop.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class ReplyRepositoryCustomImpl implements ReplyRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ReplyRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchReplyByLike(String searchBy, String searchQuery) {

        if(StringUtils.equals("itemId", searchBy)){
            return QReply.reply.item.id.eq(Long.valueOf(searchQuery));
        }
        return null;
    }

    @Override
    public Page<Reply> getReplyListPage(ReplySearchDto replySearchDto, Pageable pageable) {

        List<Reply> replyList = queryFactory
                .selectFrom(QReply.reply)
                .where(searchReplyByLike(replySearchDto.getSearchReplyBy(),
                        replySearchDto.getSearchReplyQuery()))
                .orderBy(QReply.reply.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.select(Wildcard.count).from(QReply.reply)
                .where(searchReplyByLike(replySearchDto.getSearchReplyBy(), replySearchDto.getSearchReplyQuery()))
                .fetchOne();

        return new PageImpl<>(replyList, pageable, total);
    }

}
