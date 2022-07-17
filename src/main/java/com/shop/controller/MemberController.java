package com.shop.controller;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping("/new")
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
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginMember() {
        return "/member/memberLoginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }

    @GetMapping("/update")
    public String updateM(Authentication authentication,Model model){
        Member member = memberService.getMemberByEmail(authentication.getName());
        model.addAttribute("memberFormDto",member);
        return "member/memberUpdateForm";
    }
    @PostMapping("/update")
    public String updateUser(@Valid MemberFormDto memberFormDto,RedirectAttributes redirectAttributes){
        memberService.updateMember(memberFormDto);
        redirectAttributes.addAttribute("memberFormDto",memberFormDto.getEmail());
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteM(){
        return "/member/memberDeleteForm";
    }
    @PostMapping("/delete")
    public String deleteUser(Authentication authentication, HttpSession session, MemberFormDto memberFormDto, Model model){
        Member deleteMem = memberService.getMemberByEmail(authentication.getName());
        String oriPass = deleteMem.getPassword();
        String inputPass = memberFormDto.getPassword();
        if (passwordEncoder.matches(inputPass,oriPass) == false){
            model.addAttribute("message","비밀번호를 확인해주세요");
            return "/member/memberDeleteForm";
        }
        memberService.deleteMember(deleteMem);
        session.invalidate();
        return "redirect:/";
    }

}
