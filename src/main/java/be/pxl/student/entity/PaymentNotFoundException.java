package be.pxl.student.entity;

public class PaymentNotFoundException extends PaymentException {
    public PaymentNotFoundException() { super(); }

    public PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentNotFoundException(Throwable cause) {
        super(cause);
    }

}