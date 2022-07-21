package com.shop.repository;


import com.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member>, MemberRepositoryCustom {

    // 중복회원검사 쿼리 by email
    Member findByEmail(String email);



/*    List<Member> findByName(String name);

    List<Member> findByNameOrEmail(String name, String Email);

    List<Member> findByAddress(String address);

    List<Member> findByRole(String role);

    @Query("select i from Item i where i.memberDetail like %:memberDetail% order by i.price desc")
    List<Member> findByMemberDetail(@Param("memberDetail") String memberDetail);

    @Query(value = "select * from item i where i.member_detail like %:memberDetail% order by i.price desc", nativeQuery = true)
    List<Member> findByMemberDetailByNative(@Param("memberDetail") String memberDetail);*/

}
