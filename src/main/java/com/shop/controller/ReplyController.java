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
    public String postReply(@Valid ReplyFormDto replyFormDto, @PathVariable Long itemId, BindingResult bindingResult, Model model) {

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
}
