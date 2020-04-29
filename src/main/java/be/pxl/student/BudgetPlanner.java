package be.pxl.student;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.DAO;
import be.pxl.student.entity.Payment;
import be.pxl.student.entity.exception.AccountException;
import be.pxl.student.entity.exception.PaymentException;
import be.pxl.student.entity.jpa.AccountJPA;
import be.pxl.student.entity.jpa.PaymentJPA;
import be.pxl.student.util.BudgetPlannerException;
import be.pxl.student.util.BudgetPlannerImporter;
import be.pxl.student.util.BudgetPlannerMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class BudgetPlanner {
    private static Logger logger = LogManager.getLogger(BudgetPlanner.class);
    public static void main(String[] args) {
        Path csv = Paths.get("src/main/resources/account_payments.csv");
        try {
            logger.info("Starting CSV import...");
            List<String> csvLines = BudgetPlannerImporter.readCsvFile(csv);
            logger.info("CSV import done!");

            logger.info("Starting Account mapping...");
            List<Account> accountList = new BudgetPlannerMapper().mapAccounts(csvLines);
            logger.info("Account mapping done!");

            logger.info("Inserting Accounts into DB...");
            insertIntoDatabase(accountList);
            logger.info("Inserting Accounts into DB done!");

        } catch (BudgetPlannerException | AccountException | PaymentException e) {
            e.printStackTrace();
        }
    }

    private static void insertIntoDatabase(List<Account> accountList) throws AccountException, PaymentException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("budgetplanner");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        AccountJPA accountDAO = new AccountJPA(entityManager);
        PaymentJPA paymentDAO = new PaymentJPA(entityManager);

        for (Account account : accountList) {
            accountDAO.create(account);
            for (Payment payment : account.getPayments()) {
                payment.setAccount(account);
                Account counterAccount;
                Optional<Account> optional = accountList.stream().filter(a -> a.getIBAN().equals(payment.getIBAN())).findFirst();
                counterAccount = optional.orElseGet(() -> accountDAO.create(new Account(payment.getIBAN())));
                payment.setCounterAccount(counterAccount);
                paymentDAO.create(payment);
            }
        }

    }
}
