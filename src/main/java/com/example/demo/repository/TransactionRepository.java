package com.example.demo.repository;

import com.example.demo.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Lấy danh sách giao dịch theo ID tài khoản
    List<Transaction> findByAccountId(Long accountId);

    // Lọc, phân trang theo số tài khoản
    Page<Transaction> findByAccount_AccountNumberContaining(String accountNumber, Pageable pageable);

    // Phân trang theo ID tài khoản
    Page<Transaction> findByAccountId(Long accountId, Pageable pageable);

    // Sửa lại đúng tên field: transactionType
    Page<Transaction> findByAccountIdAndTransactionType(Long accountId, String transactionType, Pageable pageable);

    // Lọc theo khoảng thời gian dựa vào timestamp
    @Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId AND t.timestamp BETWEEN :startDate AND :endDate")
    Page<Transaction> findByDateRange(
            @Param("accountId") Long accountId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    // Báo cáo theo tháng
    @Query(value = """
        SELECT 
            DATE_FORMAT(t.timestamp, '%Y-%m') AS month,
            COUNT(*) AS totalTransactions,
            SUM(CASE WHEN t.transaction_type = 'DEPOSIT' THEN t.amount ELSE 0 END) AS totalDeposits,
            SUM(CASE WHEN t.transaction_type = 'WITHDRAW' THEN t.amount ELSE 0 END) AS totalWithdrawals
        FROM transaction t
        GROUP BY month
        ORDER BY month DESC
        """, nativeQuery = true)
    List<Object[]> getMonthlyReportRaw();
}
