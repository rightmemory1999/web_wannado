package com.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainMemberDto {


    private Long id;

    private String name;

    private String email;

    private String password;

    private String address;



    @QueryProjection
    public MainMemberDto(Long id, String name, String email, String password, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;

    }
}
