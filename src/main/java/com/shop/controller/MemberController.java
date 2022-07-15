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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String updateM(Authentication authentication, Model model){
        Member member = memberService.getMemberByEmail(authentication.getName());
        model.addAttribute("memberUpdateDto",member);
        return "member/memberUpdateForm";
    }
    @PostMapping("/update")
    public String updateUser(MemberFormDto memberFormDto){
        memberService.updateMember(memberFormDto);
        return "redirect:/";
    }

//    @GetMapping("/delete")
//    public String deleteM(Authentication authentication,Model model){
//        Member member =memberService.getMemberByEmail(authentication.getName());
//        model.addAttribute("memberUpdateDto",member);
//        return "redirect:/";
//    }
//    @PostMapping("/delete")
//    public String deleteUser(SecurityContextLogoutHandler handler, HttpServletRequest req, HttpServletResponse res,
//                           Authentication authentication,RedirectAttributes redirectAttributes,MemberUpdateDto memberUpdateDto){
//        memberService.deleteMember(authentication.getName());
//        redirectAttributes.addAttribute("memberUpdateDto",memberUpdateDto.getEmail());
//        handler.logout(req, res, authentication);
//        return "redirect:/";
//    }

//    @GetMapping("/delete")
//    public String deleteM(Authentication authentication,Model model){
//        Member member = memberService.getMemberByEmail(authentication.getName());
//        model.addAttribute("memberUpdateDto",member);
//        return "/member/memberUpdateForm";
//    }
//    @PostMapping("/delete")
//    public String deleteUser(MemberUpdateDto memberUpdateDto,RedirectAttributes redirectAttributes){
//        memberService.deleteMember(memberUpdateDto);
//        redirectAttributes.addAttribute("memberUpdateDto",memberUpdateDto.getEmail());
//        return "redirect:/";
//    }
}
