package com.example.demo.service;

import com.example.demo.dto.TransactionReportDTO;
import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

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
            existing.setAccount(updatedTransaction.getAccount());
            existing.setAmount(updatedTransaction.getAmount());
            existing.setTransactionType(updatedTransaction.getTransactionType());
            existing.setTimestamp(updatedTransaction.getTimestamp());
            return transactionRepository.save(existing);
        }
        return null;
    }

    // Xoá giao dịch
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    // Lấy danh sách giao dịch theo ID tài khoản (không phân trang)
    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    // Phân trang tìm kiếm theo số tài khoản
    public Page<Transaction> getTransactionsByAccountNumber(String accountNumber, Pageable pageable) {
        return transactionRepository.findByAccount_AccountNumberContaining(accountNumber, pageable);
    }

    // ✅ Phân trang và lọc nâng cao theo accountId, type, date range
    public Page<Transaction> getFilteredTransactions(Long accountId, String type, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        if (type != null && !type.isEmpty()) {
            return transactionRepository.findByAccountIdAndTransactionType(accountId, type, pageable);
        } else if (startDate != null && endDate != null) {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
            return transactionRepository.findByDateRange(accountId, startDateTime, endDateTime, pageable);
        } else {
            return transactionRepository.findByAccountId(accountId, pageable);
        }
    }

    // ✅ Báo cáo giao dịch theo tháng
    public List<TransactionReportDTO> getMonthlyReport() {
        List<Object[]> results = transactionRepository.getMonthlyReportRaw();
        List<TransactionReportDTO> reports = new ArrayList<>();

        for (Object[] row : results) {
            String month = (String) row[0];
            Long totalTransactions = ((Number) row[1]).longValue();
            Double totalDeposits = ((Number) row[2]).doubleValue();
            Double totalWithdrawals = ((Number) row[3]).doubleValue();

            TransactionReportDTO report = new TransactionReportDTO(month, totalTransactions, totalDeposits, totalWithdrawals);
            reports.add(report);
        }

        return reports;
    }
}
