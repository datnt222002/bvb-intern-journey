package com.example.demo.service;

import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // Lấy tất cả giao dịch
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Lấy giao dịch theo ID
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    // Tạo giao dịch mới
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Cập nhật giao dịch
    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        Transaction existing = transactionRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setAccountId(updatedTransaction.getAccountId());
            existing.setAmount(updatedTransaction.getAmount());
            existing.setType(updatedTransaction.getType());
            existing.setDate(updatedTransaction.getDate());
            return transactionRepository.save(existing);
        }
        return null;
    }

    // Xoá giao dịch
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    // Lấy danh sách giao dịch theo ID tài khoản
    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}
public Page<Transaction> getTransactionsByAccountNumber(String accountNumber, Pageable pageable) {
    return transactionRepository.findByAccount_AccountNumber(accountNumber, pageable);
}
public Page<Transaction> getTransactionsByAccountNumber(String accountNumber, Pageable pageable) {
    return transactionRepository.findByAccount_AccountNumber(accountNumber, pageable);
}