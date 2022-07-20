package com.shop.dto;

import com.shop.entity.BaseEntity;
import com.shop.entity.Reply;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReplyFormDto {

    private Long id;

    @NotNull(message = "상품 아이디는 필수 값입니다.")
    private Long itemId;

    @NotNull(message = "별점은 필수 입력 값입니다.")
    @Min(value = 1, message = "최소 별점은 1점 입니다.")
    @Max(value = 5, message = "최대 별점은 5점 입니다.")
    private Long star;

    private String replyContent;

    private static ModelMapper modelMapper = new ModelMapper();

    public Reply createReply() {
        return modelMapper.map(this, Reply.class);
    }

    public static ReplyFormDto of(Reply reply) {
        return modelMapper.map(reply, ReplyFormDto.class);
    }
}
