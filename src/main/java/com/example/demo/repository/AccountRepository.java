package com.example.demo.repository;

import com.example.demo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // Tìm kiếm tài khoản theo số tài khoản hoặc tên tài khoản (dùng LIKE)
    List<Account> findByAccountNumberContainingIgnoreCaseOrAccountNameContainingIgnoreCase(
        String accountNumber,
        String accountName
    );
}
