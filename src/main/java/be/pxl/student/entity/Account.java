package be.pxl.student.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@NamedQueries({
        @NamedQuery(name = "findAllAccounts", query = "select a from Account as a"),
        @NamedQuery(name = "findByIban", query = "select a from Account as a where a.IBAN= :iban")
})
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String IBAN;
    private String name;

    @OneToMany(mappedBy = "account", orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    public Account() {
    }

    public Account(String iban) {
        this.IBAN = iban;
    }

    public Account(String name, String IBAN) {
        this.IBAN = IBAN;
        this.name = name;
    }

    public Account(int id, String IBAN, String name) {
        this.id = id;
        this.IBAN = IBAN;
        this.name = name;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(IBAN, account.IBAN) &&
                Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IBAN, name, payments);
    }

    @Override
    public String toString() {
        return "Account{" +
                "Id='" + id + '\'' +
                "IBAN='" + IBAN + '\'' +
                ", name='" + name + '\'' +
                ", payments=[" + payments.stream().map(Payment::toString).collect(Collectors.joining(",")) + "]}";
    }
}
