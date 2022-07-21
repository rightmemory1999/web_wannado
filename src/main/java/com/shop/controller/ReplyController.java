package com.shop.controller;

import com.shop.dto.ReplyFormDto;
import com.shop.dto.ReplySearchDto;
import com.shop.entity.Reply;
import com.shop.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Optional;

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
    public String postReply(@Valid ReplyFormDto replyFormDto, BindingResult bindingResult, Model model) {

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
        return "redirect:/item/{itemId}";
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

    @GetMapping(value = {"/item/{itemId}/replyList", "/item/{itemId}/replyList/{page}"})
    public String replyList(ReplySearchDto replySearchDto,
                            @PathVariable("itemId") Long itemId,
                            @PathVariable("page") Optional<Integer> page,
                            Model model) {

        Pageable pageable = PageRequest.of(page.orElse(0), 3);
        Page<Reply> replyPage = replyService.replyPage(replySearchDto, pageable);

        model.addAttribute("replyPage", replyPage);
        model.addAttribute("replySearchDto", replySearchDto);
        model.addAttribute("maxPage", 5);

        return "reply/replyList";

    }
}
