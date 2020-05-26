package be.pxl.student.rest.DTO;

import java.util.Date;

public class PaymentDTO {
    public String IBAN;
    public Date date;
    public float amount;
    public String currency;
    public String detail;
    public String counterIBAN;

    public PaymentDTO(String IBAN, Date date, float amount, String currency, String detail, String counterIBAN) {
        this.IBAN = IBAN;
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        this.detail = detail;
        this.counterIBAN = counterIBAN;
    }

    public String getCounterIBAN() {
        return counterIBAN;
    }

    public void setCounterIBAN(String counterIBAN) {
        this.counterIBAN = counterIBAN;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
