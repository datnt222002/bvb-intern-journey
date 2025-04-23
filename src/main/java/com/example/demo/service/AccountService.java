package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Lấy danh sách tất cả tài khoản
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Thêm tài khoản mới
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    // Tìm kiếm theo số hoặc tên tài khoản
    public List<Account> searchAccounts(String keyword) {
        return accountRepository.findByAccountNumberContainingIgnoreCaseOrAccountNameContainingIgnoreCase(keyword, keyword);
    }

    // Lấy tài khoản theo ID
    @Cacheable(value = "accounts", key = "#id")
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    // Cập nhật thông tin tài khoản
    @CacheEvict(value = "accounts", key = "#id")
    public Account updateAccount(Long id, Account updatedAccount) {
        Optional<Account> existing = accountRepository.findById(id);
        if (existing.isPresent()) {
            Account account = existing.get();
            account.setAccountNumber(updatedAccount.getAccountNumber());
            account.setAccountName(updatedAccount.getAccountName());
            return accountRepository.save(account);
        }
        return null;
    }

    // Xoá tài khoản
    @CacheEvict(value = "accounts", key = "#id")
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
