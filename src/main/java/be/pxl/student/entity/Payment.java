package be.pxl.student.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@NamedQuery(name = "findAllPayments", query = "select p from Payment as p")
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "counterAccountId")
    private Account counterAccount;

    private String IBAN;
    private Date date;
    private float amount;
    private String currency;
    private String detail;

    public Payment() {
    }

    public Payment(String IBAN, Date date, float amount, String currency, String detail) {
        this.IBAN = IBAN;
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        this.detail = detail;
    }

    public Payment(int id, String IBAN, Date date, float amount, String currency, String detail) {
        this.id = id;
        this.IBAN = IBAN;
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getCounterAccount() {
        return counterAccount;
    }

    public void setCounterAccount(Account counterAccount) {
        this.counterAccount = counterAccount;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Float.compare(payment.amount, amount) == 0 &&
                Objects.equals(IBAN, payment.IBAN) &&
                Objects.equals(date, payment.date) &&
                Objects.equals(currency, payment.currency) &&
                Objects.equals(detail, payment.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IBAN, date, amount, currency, detail);
    }

    @Override
    public String toString() {
        return "{" +
                "Id=" + id +
                "date=" + date +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
