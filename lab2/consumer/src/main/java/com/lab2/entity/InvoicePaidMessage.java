package com.lab2.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class InvoicePaidMessage {

    private String invoiceNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate paidDate;
    private String paymentNumber;

    public InvoicePaidMessage() {
    }

    public InvoicePaidMessage(String invoiceNumber, LocalDate paidDate, String paymentNumber) {
        this.invoiceNumber = invoiceNumber;
        this.paidDate = paidDate;
        this.paymentNumber = paymentNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    @Override
    public String toString() {
        return "InvoicePaidMessage{" +
                "invoiceNumber='" + invoiceNumber + '\'' +
                ", paidDate=" + paidDate +
                ", paymentNumber='" + paymentNumber + '\'' +
                '}';
    }
}
