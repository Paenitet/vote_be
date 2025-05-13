package com.example.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class CustomerVO {

    private int idx;
    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDate createdAt;
    private boolean isActive;

}
