package be.pxl.student;

import be.pxl.student.util.BudgetPlannerException;
import be.pxl.student.util.BudgetPlannerImporter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BudgetPlanner {
    public static void main(String[] args) {
        Path csv = Paths.get("src/main/resources/account_payments.csv");
        try {
            BudgetPlannerImporter.readCsvFile(csv);
        } catch (BudgetPlannerException e) {
            e.printStackTrace();
        }
    }
}
