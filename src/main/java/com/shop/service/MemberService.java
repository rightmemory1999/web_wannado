package com.shop.service;

import com.shop.dto.MemberFormDto;
import com.shop.dto.MemberSearchDto;
import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member joinMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException(email);
        } else if (member.isDeleted()) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<Member> getAdminMemberPage(MemberSearchDto memberSearchDto, Pageable pageable) {
        return memberRepository.getAdminMemberPage(memberSearchDto, pageable);

    }

    @Transactional
    public void updateMember(MemberFormDto memberFormDto) {
        Member member = memberRepository.findByEmail(memberFormDto.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        member.update(
                memberFormDto.getName(),
                memberFormDto.getEmail(),
                encoder.encode(memberFormDto.getPassword()),
                memberFormDto.getAddress(),
                memberFormDto.getDetailAddress(),
                memberFormDto.getExtraAddress()
        );
    }

    @Transactional
    public Member getMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        return member;
    }

    @Transactional
    public void deleteMember(Member member){
        member.setDeleted(true);
    }
}
