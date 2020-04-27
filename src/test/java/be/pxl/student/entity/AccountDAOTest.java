package be.pxl.student.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountDAOTest {

    private static final String DB_URL = "jdbc:h2:mem:test;MODE=MySQL;INIT=RUNSCRIPT FROM 'classpath:BudgetPlannerTest.sql'";
    private AccountDAO dao;
    private DAOManager manager;

    @BeforeEach
    void setUp() {
        manager = new DAOManager(DB_URL);
        dao = new AccountDAO(manager);
    }

    @AfterEach
    void tearDown() {
        manager.close();
    }

    @Test
    void it_should_return_the_created_account() throws AccountException {
        Account accountToCreate = new Account("IBANDUMMYCREATE", "NAMECREATE");
        Account createdAccount = dao.create(accountToCreate);
        assertEquals(accountToCreate, createdAccount);
    }

    @Test
    void it_should_return_3_items() throws AccountException {
        List<Account> accounts = dao.getAll();
        System.out.println(accounts);
        assertEquals(3, accounts.size());
    }

    @Test
    void it_should_return_the_account_with_id() throws AccountException {
        Account account = dao.getById(1);
        Account expected = new Account(1, "DummyIBAN", "DummyName");
        assertEquals(expected, account);
    }

    @Test
    void it_should_return_the_updated_account() throws AccountException {
        Account accountToUpdate = dao.getById(1);
        accountToUpdate.setName("EDITED");
        accountToUpdate.setIBAN("EDITED");
        Account updatedAccount = dao.update(accountToUpdate);
        assertEquals(updatedAccount, accountToUpdate);
    }

    @Test
    void it_should_return_the_deleted_account() throws AccountException {
        int accountCount = dao.getAll().size();
        dao.delete(dao.getById(3));
        int accountCountAfterDelete = dao.getAll().size();
        assertEquals(accountCountAfterDelete, accountCount - 1);
    }
}