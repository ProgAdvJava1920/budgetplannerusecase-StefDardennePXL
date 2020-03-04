package be.pxl.student.util;

public class BudgetPlannerException extends Throwable {
    public BudgetPlannerException(String message) {
        super(message);
    }

    public BudgetPlannerException(String message, Throwable cause) {
        super(message, cause);
    }

    public BudgetPlannerException(Throwable cause) {
        super(cause);
    }
}
