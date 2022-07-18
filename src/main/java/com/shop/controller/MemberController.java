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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.PrintWriter;

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
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){

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
        return "redirect:/members/new/check";
    }

    @GetMapping("/new/check")
    public void newCheck(HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println("<script>"+"alert('회원 가입을 축하드립니다.');"+"location.href='/'"+"</script>");
        printWriter.flush();
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
    public String updateUser(@Valid MemberFormDto memberFormDto){
        memberService.updateMember(memberFormDto);
        return "redirect:/members/update/check";
    }

    @GetMapping("/update/check")
    public void updateCheck(HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println("<script>"+"alert('회원 수정하셨습니다.');"+"location.href='/'"+"</script>");
        printWriter.flush();
    }

    @GetMapping("/delete")
    public String deleteM(){
        return "member/memberDeleteForm";
    }
    @PostMapping("/delete")
    @ResponseBody
    public int deleteUser(Authentication authentication,MemberFormDto memberFormDto){
        Member deleteMem = memberService.getMemberByEmail(authentication.getName());
        String oriPass = deleteMem.getPassword();
        String inputPass = memberFormDto.getPassword();
        boolean b = passwordEncoder.matches(inputPass,oriPass);
        int result = b ? 1 : 0;
        return result;
    }

    @PostMapping("/check")
    public String checkMember(Authentication authentication, HttpSession session){
        Member deleteMember = memberService.getMemberByEmail(authentication.getName());
        memberService.deleteMember(deleteMember);
        session.invalidate();
        return "redirect:/";
    }
}
