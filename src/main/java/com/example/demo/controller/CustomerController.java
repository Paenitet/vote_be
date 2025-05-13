package com.example.demo.controller;

import com.example.demo.dao.CustomerDAO;
import com.example.demo.domain.CustomerVO;
import com.example.demo.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerDAO cdao;

    // ✅ 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody CustomerVO vo) {
        try {
            cdao.signUp(vo);
            return ResponseEntity.ok("회원가입 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패: " + e.getMessage());
        }
    }

    // ✅ 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {
        try {
            boolean result = cdao.login(dto.getEmail(), dto.getPassword());
            if (result) {
                return ResponseEntity.ok("로그인 성공!");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 비밀번호가 틀렸습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 실패: " + e.getMessage());
        }
    }
}
