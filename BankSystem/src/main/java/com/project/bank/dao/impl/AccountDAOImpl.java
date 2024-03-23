package com.project.bank.dao.impl;

import com.project.bank.dao.AccountDAO;
import com.project.bank.dao.connection.ConnectionPool;
import com.project.bank.dao.connection.ConnectionPoolRuntimeException;
import com.project.bank.dao.exception.DAOException;
import com.project.bank.entity.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {
    private static final String INSERT_ACCOUNT_INFO = "INSERT INTO accounts " +
            "(balance_accounts,created_accounts,currencies_id_currencies,users_id_users,banks_id_banks) " +
            "VALUES(?,?,?,?,?)";

    private static final String SQL_SELECT_ACCOUNT_INFO_DETAIL_3 ="select  acc.id_accounts,acc.created_accounts,acc.balance_accounts,acc.users_id_users,acc.banks_id_banks,acc.currencies_id_currencies, \n" +
            "            transactions.id_transactions,transactions.amount_transactions,transactions.date_transactions,transactions.accounts_id_accounts,transactions.to_id_accounts,transactions.type_transactions_id_type_transactions,transactions.currencies_id_currencies, \n" +
            "            accounts.id_accounts,accounts.users_id_users,accounts.banks_id_banks, \n" +
            "            currencies.id_currencies,currencies.name_currencies, \n" +
            "            type_transactions.id_type_transactions,type_transactions.name_type_transactions, \n" +
            "            banks.id_banks,banks.name_banks, \n" +
            "            from_users.id_users as from_user_id, from_users.first_name_users as from_user_first_nane, from_users.last_name_users as from_user_last_name, \n" +
            "            to_users.id_users as to_user_id,to_users.first_name_users as to_user_first_name, to_users.last_name_users as to_user_last_name \n" +
            "            from accounts acc left join transactions   \n" +
            "                        on  transactions.accounts_id_accounts=acc.id_accounts  \n" +
            "                        left join accounts  \n" +
            "                        on(transactions.to_id_accounts is not null and accounts.id_accounts=transactions.to_id_accounts)  \n" +
            "                       left join currencies on currencies.id_currencies=transactions.currencies_id_currencies  \n" +
            "                      left  join type_transactions on type_transactions.id_type_transactions=transactions.type_transactions_id_type_transactions  \n" +
            "                      left  join banks on acc.banks_id_banks=banks.id_banks  \n" +
            "                        left join users as from_users on acc.users_id_users=from_users.id_users  \n" +
            "                        left join users as to_users on accounts.users_id_users=to_users.id_users  \n" +
            "                        where acc.id_accounts=? or transactions.to_id_accounts=?";

    private static final String DEPOSIT_SQL = "{call bank_sys_db.deposit(?,?,?)}";
    private static final String SQL_COLUMN_ACCOUNT_BALANCE = "balance_accounts" ;
    private static final String SQL_COLUMN_ACCOUNT_DATE ="created_accounts";
    private static final String SQL_COLUMN_CURRENCY_ID = "id_currencies";
    private static final String SQL_COLUMN_CURRENCY_NAME = "name_currencies";
    private static final String SQL_COLUMN_TRANSACTION_DATE = "date_transactions";
    private static final String SQL_COLUMN_TRANSACTION_ID = "id_transactions";
    private static final String SQL_COLUMN_TRANSACTION_AMOUNT = "amount_transactions";
    private static final String SQL_COLUMN_TRANSACTION_TYPE_ID = "id_type_transactions";
    private static final String SQL_COLUMN_TRANSACTION_TYPE_NEMA = "name_type_transactions";
    private static final String SQL_COLUMN_TRANSACTION_TO_ACCOUNT = "to_id_accounts";
    private static final String SQL_COLUMN_USER_ID = "id_users";
    private static final String SQL_COLUMN_USER_FIRST_NAME = "first_name_users";
    private static final String SQL_COLUMN_USER_LAST_NAME = "last_name_users";
    private static final String SQL_COLUMN_FROM_USER_ID = "from_user_id";
    private static final String SQL_COLUMN_FROM_USER_FIRST_NAME = "from_user_first_nane";
    private static final String SQL_COLUMN_FROM_USER_LAST_NAME = "from_user_last_name";
    private static final String SQL_COLUMN_TO_USER_ID = "to_user_id";
    private static final String SQL_COLUMN_TO_USER_FIRST_NAME = "to_user_first_name";
    private static final String SQL_COLUMN_TO_USER_LAST_NAME = "to_user_last_name";
    private static final String WITHDRAWAL_SQL = "{call bank_sys_db.withdraw(?,?,?)}";
    private static final String TRANSFER_SQL = "{call bank_sys_db.transfer(?,?,?,?,?)}";
    private static final String SQL_SELECT_TRANSACTION = "select * from transactions left  join accounts \n" +
            "on(transactions.to_id_accounts is not null and accounts.id_accounts=transactions.to_id_accounts)\n" +
            "join currencies on currencies.id_currencies=transactions.currencies_id_currencies\n" +
            "join type_transactions on type_transactions.id_type_transactions=transactions.type_transactions_id_type_transactions\n" +
            "left join banks on banks.id_banks=accounts.banks_id_banks\n" +
            " left join users on accounts.users_id_users=users.id_users " +
            " where transactions.id_transactions=?";
    private static final String SQL_SELECT_ACCOUNT_SHORT_INFO = "select id_accounts,created_accounts,balance_accounts,name_banks,name_currencies " +
            "from accounts " +
            "join banks on banks_id_banks=banks.id_banks " +
            "join currencies on currencies_id_currencies=currencies.id_currencies " +
            "where users_id_users=? ";
    private static final String SQL_COLUMN_ACCOUNT_ID = "id_accounts";
    private static final String SQL_COLUMN_BANK_NAME = "name_banks";
    private static final String SQL_COLUMN_TRANSACTION_FROM_ACCOUNT = "accounts_id_accounts";
    private static final String SQL_COLUMN_BANK_ID = "id_banks";
    private static final String SQL_SELECT_ACCOUNT = "select * from accounts acc \n" +
            "join banks on acc.banks_id_banks=banks.id_banks  \n" +
            "join currencies on acc.currencies_id_currencies=currencies.id_currencies where acc.id_accounts=?";
    private static final String SQL_SELECT_ALL_TRANSACTION_INFO = "select   acc.id_accounts,acc.users_id_users,\n" +
            "            transactions.id_transactions,transactions.amount_transactions,transactions.date_transactions,transactions.accounts_id_accounts,transactions.to_id_accounts,transactions.type_transactions_id_type_transactions,transactions.currencies_id_currencies, \n" +
            "            accounts.id_accounts,accounts.users_id_users,accounts.banks_id_banks, \n" +
            "            currencies.id_currencies,currencies.name_currencies, \n" +
            "            type_transactions.id_type_transactions,type_transactions.name_type_transactions, \n" +
            "            from_users.id_users as from_user_id, from_users.first_name_users as from_user_first_nane, from_users.last_name_users as from_user_last_name, \n" +
            "            to_users.id_users as to_user_id,to_users.first_name_users as to_user_first_name, to_users.last_name_users as to_user_last_name \n" +
            "            from  accounts acc left join transactions   \n" +
            "                        on  transactions.accounts_id_accounts=acc.id_accounts     \n" +
            "                        left join accounts  \n" +
            "                        on(transactions.to_id_accounts is not null and accounts.id_accounts=transactions.to_id_accounts)  \n" +
            "                        join currencies on currencies.id_currencies=transactions.currencies_id_currencies  \n" +
            "                        join type_transactions on type_transactions.id_type_transactions=transactions.type_transactions_id_type_transactions  \n" +
            "\t\t\t\t\t\tjoin users as from_users on acc.users_id_users=from_users.id_users  \n" +
            "\t\t\t\t\t\tjoin users as to_users on accounts.users_id_users=to_users.id_users  \n" +
            "                        where transactions.accounts_id_accounts=? or transactions.to_id_accounts=?";
    private static final String UPDATE_ACCOUNT_BALANCE = "UPDATE accounts SET balance_accounts = balance_accounts + (balance_accounts * ?)";


    @Override
    public void createAccount(int currencyId, int bankId, int userId) throws DAOException {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnectionPool connectionPool = null;

        try {

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(INSERT_ACCOUNT_INFO);

            ps.setBigDecimal(1, BigDecimal.ZERO);
            ps.setDate(2,new java.sql.Date(new java.util.Date().getTime()));
            ps.setInt(3,currencyId);
            ps.setInt(4,userId);
            ps.setInt(5,bankId);
            ps.executeUpdate();

        } catch (ConnectionPoolRuntimeException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
    }

    @Override
    public int deposit(BigDecimal amount, int accountId) throws DAOException {

        CallableStatement cs = null;
        ConnectionPool connectionPool = null;
        Connection connection = null;
        int transactionId;

        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
             cs = connection.prepareCall(DEPOSIT_SQL);

            cs.setBigDecimal(1,amount);
            cs.setInt(2,accountId);
            cs.registerOutParameter(3,Types.INTEGER);
            cs.execute();
            transactionId=cs.getInt(3);

        } catch (ConnectionPoolRuntimeException e) {
            throw new DAOException("Error in Connection pool while deposit", e);
        } catch (SQLException e) {
            throw new DAOException("Error while deposit", e);
        } finally {
            connectionPool.closeConnection(connection, cs);
        }
        return transactionId;
    }

    @Override
    public int transfer(BigDecimal amount, int fromAccountId, int toAccountId, BigDecimal exchangeRate) throws DAOException {

        CallableStatement cs = null;
        ConnectionPool connectionPool = null;
        Connection connection = null;
        int transactionId;

        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            cs = connection.prepareCall(TRANSFER_SQL);

            cs.setBigDecimal(1,amount);
            cs.setInt(2,fromAccountId);
            cs.setInt(3,toAccountId);
            cs.setBigDecimal(4,exchangeRate);
            cs.registerOutParameter(5,Types.INTEGER);
            cs.execute();
            transactionId=cs.getInt(5);

        } catch (ConnectionPoolRuntimeException e) {
            throw new DAOException("Error in Connection pool while transfer", e);
        } catch (SQLException e) {
            throw new DAOException("Error while transfer", e);
        } finally {
            connectionPool.closeConnection(connection, cs);
        }
        return transactionId;
    }

    @Override
    public int withdrawal(BigDecimal amount, int accountId) throws DAOException {

        CallableStatement cs = null;
        ConnectionPool connectionPool = null;
        Connection connection = null;
        int transactionId;

        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            cs = connection.prepareCall(WITHDRAWAL_SQL);

            cs.setBigDecimal(1,amount);
            cs.setInt(2,accountId);
            cs.registerOutParameter(3,Types.INTEGER);
            cs.execute();
            transactionId=cs.getInt(3);

        } catch (ConnectionPoolRuntimeException e) {
            throw new DAOException("Error in Connection pool while withdrawal", e);
        } catch (SQLException e) {
            throw new DAOException("Error while withdrawal", e);
        } finally {
            connectionPool.closeConnection(connection, cs);
        }
        return transactionId;
    }

    @Override
    public void calculateInterest(double percentage) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnectionPool connectionPool = null;

        try {

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE);

            ps.setDouble(1, percentage);
            ps.executeUpdate();

        } catch (ConnectionPoolRuntimeException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }

    }

    @Override
    public Account getAccountById(int idAccount) throws DAOException {
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        ConnectionPool connectionPool = null;

        Account account = null;
        Bank bank = null;
        Currency currency = null;

        try {

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQL_SELECT_ACCOUNT);

            ps.setInt(1, idAccount);
            rs = ps.executeQuery();

            while (rs.next()) {

                account = new Account();
                account.setId(idAccount);
                account.setBalance(rs.getBigDecimal(SQL_COLUMN_ACCOUNT_BALANCE));
                account.setCreatedDate(rs.getDate(SQL_COLUMN_ACCOUNT_DATE));

                Currency currencyAccount = new Currency();
                currencyAccount.setId(rs.getInt(SQL_COLUMN_CURRENCY_ID));
                currencyAccount.setName(rs.getString(SQL_COLUMN_CURRENCY_NAME));
                account.setCurrency(currencyAccount);

                bank = new Bank();
                bank.setId(rs.getInt(SQL_COLUMN_BANK_ID));
                bank.setName(rs.getString(SQL_COLUMN_BANK_NAME));
                account.setBank(bank);
            }

        } catch (ConnectionPoolRuntimeException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return account;
    }

    @Override
    public List<Transaction> getAllTransactionByAccountId(int idAccount) throws DAOException {
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        ConnectionPool connectionPool = null;

        Transaction transaction = null;
        TransactionType transactionType = null;
        Currency currency = null;
        UserShortInfo userShortInfo = null;
        List<Transaction> transactions = new ArrayList<>();

        try {

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQL_SELECT_ALL_TRANSACTION_INFO);

            ps.setInt(1, idAccount);
            ps.setInt(2,idAccount);
            rs = ps.executeQuery();

            while (rs.next()) {

                transaction = new Transaction();
                transactionType = new TransactionType();
                currency = new Currency();

                transaction.setId(rs.getInt(SQL_COLUMN_TRANSACTION_ID));
                transaction.setDateTransaction(rs.getTimestamp(SQL_COLUMN_TRANSACTION_DATE).toInstant().atZone(ZoneOffset.UTC).toLocalDateTime());
                transaction.setAmount(rs.getBigDecimal(SQL_COLUMN_TRANSACTION_AMOUNT));

                transactionType.setId(rs.getInt(SQL_COLUMN_TRANSACTION_TYPE_ID));
                transactionType.setName(rs.getString(SQL_COLUMN_TRANSACTION_TYPE_NEMA));
                transaction.setTransactionType(transactionType);

                currency.setId(rs.getInt(SQL_COLUMN_CURRENCY_ID));
                currency.setName(rs.getString(SQL_COLUMN_CURRENCY_NAME));
                transaction.setCurrency(currency);

                if (rs.getInt(SQL_COLUMN_TRANSACTION_TO_ACCOUNT)!=0 && rs.getInt(SQL_COLUMN_TRANSACTION_TO_ACCOUNT)!=idAccount) {

                    userShortInfo = new UserShortInfo();
                    userShortInfo.setAccountId(rs.getInt(SQL_COLUMN_TRANSACTION_FROM_ACCOUNT));
                    userShortInfo.setToAccountId(rs.getInt(SQL_COLUMN_TRANSACTION_TO_ACCOUNT));
                    userShortInfo.setUserId(rs.getInt(SQL_COLUMN_TO_USER_ID));
                    userShortInfo.setFirstName(rs.getString(SQL_COLUMN_TO_USER_FIRST_NAME));
                    userShortInfo.setLastName(rs.getString(SQL_COLUMN_TO_USER_LAST_NAME));
                    transaction.setToUserShortInfo(userShortInfo);
                } else if (rs.getInt(SQL_COLUMN_TRANSACTION_TO_ACCOUNT)!=0) {
                    userShortInfo = new UserShortInfo();
                    userShortInfo.setAccountId(rs.getInt(SQL_COLUMN_TRANSACTION_FROM_ACCOUNT));
                    userShortInfo.setToAccountId(rs.getInt(SQL_COLUMN_TRANSACTION_TO_ACCOUNT));
                    userShortInfo.setUserId(rs.getInt(SQL_COLUMN_FROM_USER_ID));
                    userShortInfo.setFirstName(rs.getString(SQL_COLUMN_FROM_USER_FIRST_NAME));
                    userShortInfo.setLastName(rs.getString(SQL_COLUMN_FROM_USER_LAST_NAME));
                    transaction.setToUserShortInfo(userShortInfo);
                }
                transactions.add(transaction);
            }
        } catch (ConnectionPoolRuntimeException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return transactions;
    }

    @Override
    public Transaction getTransactionById(int transactionId) throws DAOException {
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        ConnectionPool connectionPool = null;
        Transaction transaction = null;
        TransactionType transactionType = null;
        Currency currency = null;
        UserShortInfo userShortInfo = null;

       try{

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();

            ps = connection.prepareStatement(SQL_SELECT_TRANSACTION);
            ps.setInt(1,transactionId);
            rs=ps.executeQuery();

            transaction = new Transaction();
            transactionType = new TransactionType();
            currency = new Currency();

            while (rs.next()){

                transaction.setId(rs.getInt(SQL_COLUMN_TRANSACTION_ID));
                transaction.setDateTransaction(rs.getTimestamp(SQL_COLUMN_TRANSACTION_DATE).toInstant().atZone(ZoneOffset.UTC).toLocalDateTime());
                transaction.setAmount(rs.getBigDecimal(SQL_COLUMN_TRANSACTION_AMOUNT));

                transactionType.setId(rs.getInt(SQL_COLUMN_TRANSACTION_TYPE_ID));
                transactionType.setName(rs.getString(SQL_COLUMN_TRANSACTION_TYPE_NEMA));
                transaction.setTransactionType(transactionType);

                currency.setId(rs.getInt(SQL_COLUMN_CURRENCY_ID));
                currency.setName(rs.getString(SQL_COLUMN_CURRENCY_NAME));
                transaction.setCurrency(currency);

                if (rs.getInt(SQL_COLUMN_TRANSACTION_TO_ACCOUNT)!=0) {

                    userShortInfo = new UserShortInfo();
                    userShortInfo.setAccountId(rs.getInt(SQL_COLUMN_ACCOUNT_ID));
                    userShortInfo.setUserId(rs.getInt(SQL_COLUMN_USER_ID));
                    userShortInfo.setFirstName(rs.getString(SQL_COLUMN_USER_FIRST_NAME));
                    userShortInfo.setLastName(rs.getString(SQL_COLUMN_USER_LAST_NAME));
                    transaction.setToUserShortInfo(userShortInfo);
                }
            }
    } catch (ConnectionPoolRuntimeException e) {
        throw new DAOException(e);
    } catch (SQLException e) {
        throw new DAOException(e);
    } finally {
        connectionPool.closeConnection(connection, ps, rs);
    }
        return transaction;
    }

    @Override
    public Account getAccountInfoDetailById(int id) throws DAOException {
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        ConnectionPool connectionPool = null;

        Account account = null;
        Transaction transaction = null;
        TransactionType transactionType = null;
        Bank bank = null;
        Currency currency = null;
        UserShortInfo userShortInfo = null;
        List<Transaction> groupTransaction = new ArrayList<>();

        try {

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();

            ps = connection.prepareStatement(SQL_SELECT_ACCOUNT_INFO_DETAIL_3);

            ps.setInt(1, id);
            ps.setInt(2,id);
            rs = ps.executeQuery();

            rs.next();

            account = new Account();
            account.setId(id);
            account.setBalance(rs.getBigDecimal(SQL_COLUMN_ACCOUNT_BALANCE));
            account.setCreatedDate(rs.getDate(SQL_COLUMN_ACCOUNT_DATE));
            
            Currency currencyAccount = new Currency();
            currencyAccount.setId(rs.getInt(SQL_COLUMN_CURRENCY_ID));
            currencyAccount.setName(rs.getString(SQL_COLUMN_CURRENCY_NAME));
            account.setCurrency(currencyAccount);

            bank = new Bank();
            bank.setId(rs.getInt(SQL_COLUMN_BANK_ID));
            bank.setName(rs.getString(SQL_COLUMN_BANK_NAME));
            account.setBank(bank);

            while (rs.next()) {

                transaction = new Transaction();
                transactionType = new TransactionType();
                currency = new Currency();

                transaction.setId(rs.getInt(SQL_COLUMN_TRANSACTION_ID));
                transaction.setDateTransaction(rs.getTimestamp(SQL_COLUMN_TRANSACTION_DATE).toInstant().atZone(ZoneOffset.UTC).toLocalDateTime());
                transaction.setAmount(rs.getBigDecimal(SQL_COLUMN_TRANSACTION_AMOUNT));

                transactionType.setId(rs.getInt(SQL_COLUMN_TRANSACTION_TYPE_ID));
                transactionType.setName(rs.getString(SQL_COLUMN_TRANSACTION_TYPE_NEMA));
                transaction.setTransactionType(transactionType);

                currency.setId(rs.getInt(SQL_COLUMN_CURRENCY_ID));
                currency.setName(rs.getString(SQL_COLUMN_CURRENCY_NAME));
                transaction.setCurrency(currency);

                if (rs.getInt(SQL_COLUMN_TRANSACTION_TO_ACCOUNT)!=0 && rs.getInt(SQL_COLUMN_TRANSACTION_TO_ACCOUNT)!=id) {

                    userShortInfo = new UserShortInfo();
                    userShortInfo.setAccountId(rs.getInt(SQL_COLUMN_TRANSACTION_FROM_ACCOUNT));
                    userShortInfo.setToAccountId(rs.getInt(SQL_COLUMN_TRANSACTION_TO_ACCOUNT));
                    userShortInfo.setUserId(rs.getInt(SQL_COLUMN_TO_USER_ID));
                    userShortInfo.setFirstName(rs.getString(SQL_COLUMN_TO_USER_FIRST_NAME));
                    userShortInfo.setLastName(rs.getString(SQL_COLUMN_TO_USER_LAST_NAME));
                    transaction.setToUserShortInfo(userShortInfo);
                } else if (rs.getInt(SQL_COLUMN_TRANSACTION_TO_ACCOUNT)!=0) {
                    userShortInfo = new UserShortInfo();
                    userShortInfo.setAccountId(rs.getInt(SQL_COLUMN_TRANSACTION_FROM_ACCOUNT));
                    userShortInfo.setToAccountId(rs.getInt(SQL_COLUMN_TRANSACTION_TO_ACCOUNT));
                    userShortInfo.setUserId(rs.getInt(SQL_COLUMN_FROM_USER_ID));
                    userShortInfo.setFirstName(rs.getString(SQL_COLUMN_FROM_USER_FIRST_NAME));
                    userShortInfo.setLastName(rs.getString(SQL_COLUMN_FROM_USER_LAST_NAME));
                    transaction.setToUserShortInfo(userShortInfo);
                }
                groupTransaction.add(transaction);
            }
            account.setTransactions(groupTransaction);

        } catch (ConnectionPoolRuntimeException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return account;
    }
    @Override
    public List<AccountShortInfo> getAllAccountByUserId(int userId) throws DAOException {
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        ConnectionPool connectionPool = null;

        AccountShortInfo accountShortInfo = null;
        List<AccountShortInfo> groupAccountShortInfo = new ArrayList<>();

        try {

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQL_SELECT_ACCOUNT_SHORT_INFO);

            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()){
                accountShortInfo = new AccountShortInfo();
                accountShortInfo.setId(rs.getInt(SQL_COLUMN_ACCOUNT_ID));
                accountShortInfo.setCreatedDate(rs.getDate(SQL_COLUMN_ACCOUNT_DATE));
                accountShortInfo.setBalance(rs.getBigDecimal(SQL_COLUMN_ACCOUNT_BALANCE));
                accountShortInfo.setCurrency(rs.getString(SQL_COLUMN_CURRENCY_NAME));
                accountShortInfo.setNameBank(rs.getString(SQL_COLUMN_BANK_NAME));
                groupAccountShortInfo.add(accountShortInfo);
            }
        } catch (ConnectionPoolRuntimeException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return groupAccountShortInfo;
    }
}
