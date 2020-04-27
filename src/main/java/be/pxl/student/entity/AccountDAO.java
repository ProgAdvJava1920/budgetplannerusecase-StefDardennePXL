package be.pxl.student.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements DAO<Account, AccountException> {

    private static Logger logger = LogManager.getLogger(AccountDAO.class);

    private static final String SELECT_BY_ID = "select * from Account where id = ?";
    private static final String SELECT_ALL = "select * from Account";
    private static final String CREATE_ACCOUNT = "insert into Account (`IBAN`, `name` ) values(?, ?)";
    private static final String DELETE_ACCOUNT = "delete from Account where id = ?";
    private static final String UPDATE_ACCOUNT = "update Account set iban = ?, name = ? where id = ?";


    private DAOManager manager;

    public AccountDAO(DAOManager manager) {
        this.manager = manager;
    }

    @Override
    public Account create(Account account) throws AccountException {
        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(CREATE_ACCOUNT)) {
            preparedStatement.setString(1, account.getIBAN());
            preparedStatement.setString(2, account.getName());
            int result = preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.first()) {
                int accountId = generatedKeys.getInt(1);
                account.setId(accountId);
            }
            if (result == 1) {
                return account;
            }
            manager.commit();
        } catch (SQLException e) {
            manager.rollback(e);
            throw new AccountException(String.format("Error creating account [%s]", account.toString()), e);
        }
        throw new AccountException("Could not create account");
    }

    @Override
    public Account getById(int id) throws AccountException {
        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id); // telt vanaf 1 niet 0
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.first()) {
                return new Account(
                        resultSet.getInt("id"),
                        resultSet.getString("IBAN"),
                        resultSet.getString("name")
                );
            } else {
                throw new AccountNotFoundException(String.format("Account with id [%d] not found", id));
            }
        } catch (SQLException e) {
            throw new AccountException(String.format("Error while retrieving account with id [%d]", id), e);
        }
    }

    @Override
    public List<Account> getAll() throws AccountException {
        List<Account> accountList = new ArrayList<>();
        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accountList.add(new Account(
                                resultSet.getInt("id"),
                                resultSet.getString("IBAN"),
                                resultSet.getString("name")
                        )
                );
            }
        } catch (SQLException e) {
            throw new AccountException("Error retrieving accounts", e);
        }
        return accountList;
    }

    @Override
    public Account update(Account account) throws AccountException {
        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(UPDATE_ACCOUNT)) {
            preparedStatement.setString(1, account.getIBAN());
            preparedStatement.setString(2, account.getName());
            preparedStatement.setInt(3, account.getId());
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return account;
            }
        } catch (SQLException e) {
            throw new AccountException(String.format("Error while updating account [%s]", account.toString()), e);
        }
        throw new AccountException("Could not update account");
    }

    @Override
    public Account delete(Account account) throws AccountException {
        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement(DELETE_ACCOUNT)) {
            preparedStatement.setInt(1, account.getId());
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return account;
            }
        } catch (SQLException e) {
            throw new AccountException(String.format("Error deleting account [%s]", account.toString()), e);
        }
        throw new AccountException("Could not delete account");
    }
}
