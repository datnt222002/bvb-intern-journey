package com.example.demo.dto;

/**
 * DTO dùng cho báo cáo giao dịch theo tháng.
 */
public class TransactionReportDTO {

    private String month;               // Ví dụ: "2024-04"
    private Long totalTransactions;     // Tổng số giao dịch trong tháng
    private Double totalDeposits;       // Tổng tiền đã gửi
    private Double totalWithdrawals;    // Tổng tiền đã rút

    public TransactionReportDTO(String month, Long totalTransactions, Double totalDeposits, Double totalWithdrawals) {
        this.month = month;
        this.totalTransactions = totalTransactions;
        this.totalDeposits = totalDeposits;
        this.totalWithdrawals = totalWithdrawals;
    }

    // Getters & Setters
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(Long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public Double getTotalDeposits() {
        return totalDeposits;
    }

    public void setTotalDeposits(Double totalDeposits) {
        this.totalDeposits = totalDeposits;
    }

    public Double getTotalWithdrawals() {
        return totalWithdrawals;
    }

    public void setTotalWithdrawals(Double totalWithdrawals) {
        this.totalWithdrawals = totalWithdrawals;
    }
}
