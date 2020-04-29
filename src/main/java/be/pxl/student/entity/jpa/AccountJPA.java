package be.pxl.student.entity.jpa;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.DAO;
import be.pxl.student.entity.exception.AccountException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccountJPA implements DAO<Account, AccountException> {

    private EntityManager entityManager;

    public AccountJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Account create(Account account) {
        entityManager.getTransaction().begin();
        entityManager.persist(account);
        entityManager.getTransaction().commit();
        return account;
    }

    @Override
    public Account getById(int id) {
        return entityManager.find(Account.class, id);
    }

    public Account getByIBAN(String iban) {
        TypedQuery<Account> query = entityManager.createNamedQuery("findByIban", Account.class);
        query.setParameter("iban", iban);
        return query.getSingleResult();
    }

    @Override
    public List<Account> getAll() {
        TypedQuery<Account> query = entityManager.createNamedQuery("findAllAccounts", Account.class);
        return query.getResultList();
    }

    @Override
    public Account update(Account account) {
        entityManager.getTransaction().begin();
        entityManager.persist(account);
        entityManager.getTransaction().commit();
        return account;
    }

    @Override
    public Account delete(Account account) {
        entityManager.getTransaction().begin();
        Account accountToDelete = entityManager.find(Account.class, account.getId());
        entityManager.remove(accountToDelete);
        entityManager.getTransaction().commit();
        return account;
    }
}