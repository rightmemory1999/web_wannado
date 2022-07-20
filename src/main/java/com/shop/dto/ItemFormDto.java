package com.shop.dto;

import com.shop.constant.CoffeeBean;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotBlank(message = "맛과향은 필수 입력 값입니다.")
    private String coffeeTaste;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "설명은 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    @NotBlank(message = "로스터리 이름은 필수 입력 값입니다.")
    private String roasteryNm;

    @NotBlank(message = "로스터리 소개는 필수 입력 값입니다.")
    private String roasteryDetail;

    @NotBlank(message = "추출방법은 필수 입력 값입니다.")
    private String extraction;

    @NotBlank(message = "원산지는 필수 입력 값입니다.")
    private String origin;

    @NotBlank(message = "가공방식은 필수 입력 값입니다.")
    private String process;

    @NotBlank(message = "품종은 필수 입력 값입니다.")
    private String kind;

    private ItemSellStatus itemSellStatus;

    private CoffeeBean coffeeBean;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();    // 상품 저장 후 수정할 때 상품 이미지 정보를 저장하는 리스트

    private List<Long> itemImgIds = new ArrayList<>();  // 상품의 이미지 아이디를 저장하는 리스트, 상품 등록시에는 비어있고 수정시에 이미지 아이디를 담아둠

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem() {  // 엔티티 객체와 DTO 객체 간의 데이터를 복사하여 복사한 객체를 반환
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }
}
