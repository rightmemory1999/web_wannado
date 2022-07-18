package com.shop.controller;

import com.shop.dto.ReplyFormDto;
import com.shop.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping(value = "/item/{itemId}/reply/new")
    public String replyForm(Model model, @PathVariable Long itemId) {
        model.addAttribute("replyFormDto", new ReplyFormDto());
        model.addAttribute("itemId", itemId);
        return "reply/replyForm";
    }

    @PostMapping(value = "/item/{itemId}/reply/new")
    public String postReply(@Valid ReplyFormDto replyFormDto, @PathVariable("itemId") Long itemId, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldErrors());
            return "reply/replyForm";
        }

        try {
            replyService.postReply(replyFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "리뷰 등록 중 에러가 발생하였습니다.");
            return "reply/replyForm";
        }
        return "reply/replyForm";
    }

    @GetMapping(value = "/item/{itemId}/reply/{replyId}")
    public String replyDtl(@PathVariable("itemId") Long itemId,
                           @PathVariable("replyId") Long replyId,
                           Model model) {
        try {
            ReplyFormDto replyFormDto = replyService.getReplyDtl(replyId);
            model.addAttribute("replyFormDto", replyFormDto);
            model.addAttribute("itemId", itemId);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 리뷰입니다.");
            model.addAttribute("replyFormDto", new ReplyFormDto());
            return "reply/replyForm";
        }
        return "reply/replyForm";
    }

    @PostMapping(value = "/item/{itemId}/reply/{replyId}")
    public String replyUpdate(@Valid ReplyFormDto replyFormDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldErrors());
            return "reply/replyForm";
        }

        try {
            replyService.updateReply(replyFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "리뷰 수정 중 에러가 발생하였습니다.");
            return "reply/replyForm";
        }
        return "reply/replyForm";

    }
}
