package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    // 상품코드

    @Column(nullable = false)
    private String itemNm;  // 상품명

    @Column(name="price", nullable = false)
    private int price;  // 가격

    @Column(nullable = false)
    private int stockNumber;    // 재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail;  //상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;  // 상품 판매 상태

    public void updateItem(ItemFormDto itemFormDto) {
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    /**
     * 상품주문시 상품재고를 감소시키는 메서드 (엔티티 클래스 안에 비즈니스 로직을 작성 -> 코드 재사용성 증가 + 데이터 변경포인트를 한군데서 관리)
     * 1. 상품재고수량에서 주문 후 남은 재고수량을 구한다.
     * 2. 상품재고가 주문수량보다 적을 경우 OutOfStockException 을 발생시킨다.
     * 3. 주문 후 남은 재고수량을 상품의 현재재고 값으로 할당한다.
     * @param stockNumber 재고수량
     */
    public void removeStock(int stockNumber){
        int restStock = this.stockNumber - stockNumber;
        if(restStock<0){
            throw new OutOfStockException("상품의 재고가 부족 합니다. (현재 재고 수량: " + this.stockNumber + ")");
        } else if (restStock == 0) {
            itemSellStatus = ItemSellStatus.SOLD_OUT;
        }
        this.stockNumber = restStock;
    }

}
