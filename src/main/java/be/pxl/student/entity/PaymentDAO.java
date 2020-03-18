package be.pxl.student.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class PaymentDAO implements DAO<Payment, PaymentException> {

    public static final String SELECT_BY_ID = "select * from Account where id = ?";
    private String url;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public PaymentDAO(String url) {
        this.url = url;
    }

    @Override
    public Payment create(Payment payment) throws PaymentException {
        throw new PaymentException("not yet implemented");

    }

    @Override
    public Payment getById(int id) throws PaymentException {
        throw new PaymentException("not yet implemented");
    }

    @Override
    public List<Payment> getAll() throws PaymentException {
        throw new PaymentException("not yet implemented");
    }

    @Override
    public Payment update(Payment payment) throws PaymentException {
        throw new PaymentException("not yet implemented");
    }

    @Override
    public Payment delete(Payment payment) throws PaymentException {
        throw new PaymentException("not yet implemented");
    }
}
