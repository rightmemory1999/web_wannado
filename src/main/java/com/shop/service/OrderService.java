package com.shop.service;

import com.shop.dto.OrderDto;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.entity.Order;
import com.shop.entity.OrderItem;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 주문 로직을 작성하는 클래스
 */
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    /**
     *  입력 받은 주문정보를 저장하는 메서드
     *  1. orderDto 에서 전달받은 상품아이디로 주문상품을 조회한다.
     *  2. email 로 주문자 회원정보를 조회한다.
     *  3. 조회했던 주문상품과 주문수량으로 주문상품 리스트를 생성한다.
     *  4. 회원정보와 주문상품 리스트로 주문 엔티티를 생성한다.
     *  5. 생성한 주문 엔티티를 orderRepository 에 저장한다.
     * @param orderDto 입력 받은 주문정보
     * @param email 주문고객 이메일
     * @return 주문번호
     */
    public Long order(OrderDto orderDto, String email){

        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);

        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }
}
