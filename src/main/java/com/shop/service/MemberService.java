package com.shop.service;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    @Transactional
    public void updateMember(MemberFormDto memberFormDto){
        Member member = memberRepository.findByEmail(memberFormDto.getEmail());
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
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
    public Member getMemberByEmail(String email){
        Member member = memberRepository.findByEmail(email);
        return member;
    }

//    @Transactional
//    public void deleteMember(String email){
//        Member member = memberRepository.findByEmail(email);
//        memberRepository.delete(member);
//    }

//    @Transactional
//    public void deleteMember(MemberUpdateDto memberUpdateDto){
//        memberRepository.deleteMemberByEmail(memberUpdateDto.getEmail());
//    }
}
