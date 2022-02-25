package com.lab2.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class InvoiceCanceledMessage {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate cancelDate;
    private String invoiceNumber;
    private String cancelNumber;
    private String reason;

    public InvoiceCanceledMessage() {
    }

    public InvoiceCanceledMessage(LocalDate cancelDate, String invoiceNumber, String cancelNumber, String reason) {
        this.cancelDate = cancelDate;
        this.invoiceNumber = invoiceNumber;
        this.cancelNumber = cancelNumber;
        this.reason = reason;
    }

    public LocalDate getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(LocalDate cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCancelNumber() {
        return cancelNumber;
    }

    public void setCancelNumber(String cancelNumber) {
        this.cancelNumber = cancelNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "InvoiceCanceledMessage{" +
                "cancelDate=" + cancelDate +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", cancelNumber='" + cancelNumber + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
