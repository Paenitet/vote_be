package com.example.demo.dao;

import com.example.demo.domain.CustomerVO;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
@RequiredArgsConstructor
public class CustomerDAO {

    @Autowired
    private ConnectionUtil connectionUtil;

    public void signUp(CustomerVO vo) throws Exception {

        // SQL 정리
        String customerSQL = "insert into customers (name, email, phone, is_active) values (?, ?, ?, ?)";
        String authSQL = "insert into auth_credentials (customer_id, password) values (?, ?)";

        Connection conn = null;
        PreparedStatement customerStmt = null;
        PreparedStatement authStmt = null;
        ResultSet rs = null;

        try {
            conn = connectionUtil.getConnection();
            conn.setAutoCommit(false);

            customerStmt = conn.prepareStatement(customerSQL, Statement.RETURN_GENERATED_KEYS);
            customerStmt.setString(1, vo.getName());
            customerStmt.setString(2, vo.getEmail());
            customerStmt.setString(3, vo.getPhone());
            customerStmt.setBoolean(4, true);
            customerStmt.executeUpdate();

            rs = customerStmt.getGeneratedKeys();
            int customerId = 0;
            if (rs.next()) {
                customerId = rs.getInt(1);
            } else {
                throw new SQLException("고객 ID 생성 실패");
            }

            String hashedPassword = hashPassword(vo.getPassword());
            authStmt = conn.prepareStatement(authSQL);
            authStmt.setInt(1, customerId);
            authStmt.setString(2, hashedPassword);
            authStmt.executeUpdate();

            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (customerStmt != null) customerStmt.close();
            if (authStmt != null) authStmt.close();
            if (conn != null) {
                conn.setAutoCommit(true); // 다시 돌아감.
                conn.close();
            }
        }
    }

    public boolean login(String email, String password) throws Exception {
        String sql = """
        SELECT ac.password
        FROM auth_credentials ac
        JOIN customers c ON ac.customer_id = c.customer_id
        WHERE c.email = ?
        """;

            @Cleanup Connection conn = connectionUtil.getConnection();
            @Cleanup PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            @Cleanup ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String hashed = rs.getString("password");
                return checkPassword(password, hashed);
            }
            throw new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        private String hashPassword(String plainPassword) {
            return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        }

        private boolean checkPassword(String input, String hashed) {
            return BCrypt.checkpw(input, hashed);
        }
    }
