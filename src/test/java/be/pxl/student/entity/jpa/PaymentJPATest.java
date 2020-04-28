package be.pxl.student.entity.jpa;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.DAO;
import be.pxl.student.entity.Payment;
import be.pxl.student.entity.exception.PaymentException;
import be.pxl.student.util.BudgetPlannerMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class PaymentJPATest {

    private DAO<Payment, PaymentException> dao;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("budgetplanner_test");
        entityManager = entityManagerFactory.createEntityManager();
        dao = new PaymentJPA(entityManager);
    }

    @AfterEach
    void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void it_should_return_the_newly_created_payment() throws ParseException, PaymentException {
        Payment paymentToCreate = new Payment("blabla", BudgetPlannerMapper.convertToDate("Thu Feb 13 05:47:35 CET 2020"), Float.parseFloat("100.50"), "EUR", "paymentdetails");
        paymentToCreate.setAccount(new Account(1, "fromaccount", "accountnamex"));
        paymentToCreate.setCounterAccount(new Account(2, "toaccount", "accountnamey"));
        Payment paymentCreated = dao.create(paymentToCreate);
        assertEquals(paymentCreated, paymentToCreate);
    }

    @Test
    void it_should_return_the_payment_with_given_id() throws PaymentException {
        int id = 1;
        Payment paymentWithId = dao.getById(id);
        assertEquals(paymentWithId.getId(), id);
    }

    @Test
    void it_should_return_2_payments() throws PaymentException {
        int expectedCount = 2;
        int paymentCount = dao.getAll().size();
        assertEquals(paymentCount, expectedCount);
    }

    @Test
    void it_should_update_the_given_payment() throws PaymentException {
        String newIBAN = "other";
        Payment paymentToUpdate = dao.getById(1);
        paymentToUpdate.setIBAN(newIBAN);
        dao.update(paymentToUpdate);
        entityManager.clear();
        Payment updatedPayment = dao.getById(1);
        assertEquals(newIBAN, updatedPayment.getIBAN());
    }

    @Test
    void it_should_delete_the_given_payment() throws PaymentException {
        Payment paymentToDelete = dao.getById(1);
        dao.delete(paymentToDelete);
        assertNull(dao.getById(1));
    }
}