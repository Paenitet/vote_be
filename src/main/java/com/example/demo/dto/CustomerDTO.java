package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private int idx;
    private String name;
    private String password;
    private String email;
    private String phone;
    private LocalDate createdAt;
    private boolean isActive;
}

