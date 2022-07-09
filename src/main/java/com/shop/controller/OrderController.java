package com.shop.controller;

import com.shop.dto.OrderDto;
import com.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * 주문관련 요청을 처리하기 위한 클래스
 * 상품주문에서 웹페이지의 새로고침 없이 서버에 주문을 요청하기 위해 비동기 방식을 사용한다.
 */
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 화면으로부터 입력된 주문정보를 이용하여 주문 로직을 호출하고 주문번호를 생성한다.
     * 1. 주문정보를 받는 orderDto 에 바인딩 에러가 있는지 검증한다.
     * 2. 에러가 있다면 ResponseEntity 에 담아 반환한다.
     * 3. 현재 로그인한 유저의 이메일 정보를 얻는다.
     * 4. 주문정보와 회원이메일로 주문로직을 호출한다.
     * 5. 주문로직으로 생성된 주문번호와 요청성공 Http 응답상태코드를 반환한다.
     * @param orderDto 입력받은 주문정보
     * @param bindingResult 스프링 데이터검증 객체
     * @param principal 스프링 시큐리티 사용자정보를 얻기 위한 객체
     * @return 주문번호, 요청성공메세지
     */
    @PostMapping(value = "/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto
            , BindingResult bindingResult, Principal principal){

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long orderId;

        try {
            orderId = orderService.order(orderDto, email);
        } catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

}
