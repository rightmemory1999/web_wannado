package com.shop.controller;

import com.shop.dto.MemberFormDto;
import com.shop.dto.MemberSearchDto;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/members/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping("/members/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "member/memberForm";
        }
        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.joinMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        model.addAttribute("msg", "success");

        return "member/memberForm";
    }

    @GetMapping("/members/login")
    public String loginMember() {
        return "member/memberLoginForm";
    }

    @GetMapping("/members/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "member/memberLoginForm";
    }


    @GetMapping(value = {"/admin/members", "/admin/members/{page}"})
    public String memberManage(MemberSearchDto memberSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.orElse(0), 3);
        Page<Member> members = memberService.getAdminMemberPage(memberSearchDto, pageable);

        model.addAttribute("members", members);
        model.addAttribute("memberSearchDto", memberSearchDto);
        model.addAttribute("maxPage", 5);

        return "member/memberMng";
    }


    @GetMapping("/members/update")
    public String updateM(Authentication authentication, Model model) {
        Member member = memberService.getMemberByEmail(authentication.getName());
        model.addAttribute("memberFormDto", member);
        return "member/memberUpdateForm";
    }

    @PostMapping("/members/update")
    public String updateUser(@Valid MemberFormDto memberFormDto, Model model) {
        memberService.updateMember(memberFormDto);
        model.addAttribute("msgU", "updateCom");
        return "member/memberUpdateForm";
    }

    @GetMapping("/members/delete")
    public String deleteM() {
        return "member/memberDeleteForm";
    }

    @PostMapping("/members/delete")
    @ResponseBody
    public int deleteUser(Authentication authentication, MemberFormDto memberFormDto) {
        Member deleteMem = memberService.getMemberByEmail(authentication.getName());
        String oriPass = deleteMem.getPassword();
        String inputPass = memberFormDto.getPassword();
        boolean b = passwordEncoder.matches(inputPass, oriPass);
        int result = b ? 1 : 0;
        return result;
    }

    @PostMapping("/members/check")
    public String checkMember(Authentication authentication, HttpSession session) {
        Member deleteMember = memberService.getMemberByEmail(authentication.getName());
        memberService.deleteMember(deleteMember);
        session.invalidate();
        return "redirect:/";
    }
}
