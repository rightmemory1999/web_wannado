package com.shop.dto;

import com.shop.constant.CoffeeBean;
import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {

    private String searchDateType;  // 등록일 기준 조회

    private ItemSellStatus searchSellStatus;    // SELL or SOLD_OUT

    private CoffeeBean searchCoffeeBean;    // DECAFFEIN or SINGLE_ORIGIN or BLEND

    private String searchTaste;    // 맛과향 : 달콤, 쌉쌀, 깔끔한, 밸런스 ...

    private String searchExtraction;  // 추출방식
    
    private String searchOrigin;  // 원산지

    private String searchBy;    // 조회 유형 : itemNm, createBy

    private String searchQuery = "";    // 조회할 검색어를 저장할 변수, searchBy 에 따라 기준이 바뀜
}
