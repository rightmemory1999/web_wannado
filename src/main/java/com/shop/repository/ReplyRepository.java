package com.shop.repository;

import com.shop.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ReplyRepository extends JpaRepository<Reply, Long>, QuerydslPredicateExecutor<Reply>, ReplyRepositoryCustom {
}
