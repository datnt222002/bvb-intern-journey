package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Lấy tất cả tài khoản
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Lưu tài khoản mới
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    // Tìm kiếm theo số tài khoản hoặc tên tài khoản (có chứa)
    public List<Account> searchAccounts(String keyword) {
        return accountRepository.findByAccountNumberContainingIgnoreCaseOrAccountNameContainingIgnoreCase(keyword, keyword);
    }
}
