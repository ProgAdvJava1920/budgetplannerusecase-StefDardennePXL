package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BudgetPlannerMapper {

    private static final String DATE_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN, Locale.US);
    private static final int CSV_ITEM_COUNT = 7;

    private Map<String, Account> accountMap = new HashMap<>();

    public List<Account> mapAccounts(List<String> accountLines) {
        for (String accountLine : accountLines) {
            try {
                Account account = mapDataLineToAccount(accountLine);
                accountMap.putIfAbsent(account.getIBAN(), account);
            } catch (BudgetPlannerException | ParseException e) {
                System.out.println("Could not parse line " + accountLine);
            }
        }
        return new ArrayList<>(accountMap.values());
    }

    public Account mapDataLineToAccount(String accountLine) throws BudgetPlannerException, ParseException {
        String[] items = accountLine.split(",");

        if (items.length != CSV_ITEM_COUNT) {
            throw new BudgetPlannerException(String.format("Invalid line, expected %d items but was %d", CSV_ITEM_COUNT, items.length));
        }

        String name = items[0];
        String IBAN = items[1];

        Account account = accountMap.getOrDefault(IBAN, new Account(name, IBAN));
        Payment payment = mapItemsToPayment(items);
        account.getPayments().add(payment);

        return account;
    }

    public Payment mapItemsToPayment(String[] items) throws ParseException {
        return new Payment(
                items[2],
                convertToDate(items[3]),
                Float.parseFloat(items[4]),
                items[5],
                items[6]
        );
    }

    public static Date convertToDate(String dateString) throws ParseException {
        return DATE_FORMAT.parse(dateString);
    }

    public static String convertDateToString(Date date) {
        return DATE_FORMAT.format(date);
    }
}
