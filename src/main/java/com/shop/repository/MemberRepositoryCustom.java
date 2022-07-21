package com.shop.repository;


import com.shop.dto.MainMemberDto;
import com.shop.dto.MemberSearchDto;
import com.shop.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {

    /*
    itemSearchDto : 상품 조회 조건을 담고 있다
    pageable : 페이징 정보를 담고 있다
    위의 두 객체를 파라미터로 받아서 Page<Item> 를 반환하는 메서드
     */
    Page<Member> getAdminMemberPage(MemberSearchDto memberSearchDto, Pageable pageable);

    Page<MainMemberDto> getMainMemberPage(MemberSearchDto memberSearchDto, Pageable pageable);

}
