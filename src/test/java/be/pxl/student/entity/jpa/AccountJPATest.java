package be.pxl.student.entity.jpa;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.DAO;
import be.pxl.student.entity.exception.AccountException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountJPATest {

    private DAO<Account, AccountException> dao;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("budgetplanner_test");
        entityManager = entityManagerFactory.createEntityManager();
        dao = new AccountJPA(entityManager);
    }

    @AfterEach
    void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void it_should_return_the_newly_created_account() throws AccountException {
        Account accountToCreate = new Account("DummyIBAN", "DummyName");
        Account accountCreated = dao.create(accountToCreate);
        assertEquals(accountCreated, accountToCreate);
    }

    @Test
    void it_should_return_the_account_with_given_id() throws AccountException {
        Account account = dao.getById(1);
        Account expected = new Account(1, "DummyIBAN", "DummyName");
        assertEquals(expected, account);
    }

    @Test
    void it_should_return_3_accounts() throws AccountException {
        List<Account> accounts = dao.getAll();
        System.out.println(accounts);
        assertEquals(3, accounts.size());
    }

    @Test
    void it_should_update_the_given_account() throws AccountException {
        String newName = "othername";
        Account accountToUpdate = dao.getById(1);
        accountToUpdate.setName(newName);
        dao.update(accountToUpdate);
        entityManager.clear();
        Account updatedAccount = dao.getById(1);
        assertEquals(newName, updatedAccount.getName());
    }

    @Test
    void it_should_delete_the_given_account() throws AccountException {
        Account accountToDelete = dao.getById(1);
        dao.delete(accountToDelete);
        assertNull(dao.getById(1));
    }
}