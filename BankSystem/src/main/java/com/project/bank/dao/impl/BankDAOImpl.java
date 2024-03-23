package com.project.bank.dao.impl;

import com.project.bank.dao.BankDAO;
import com.project.bank.dao.connection.ConnectionPool;
import com.project.bank.dao.connection.ConnectionPoolRuntimeException;
import com.project.bank.dao.exception.DAOException;
import com.project.bank.entity.Account;
import com.project.bank.entity.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankDAOImpl implements BankDAO {

    private static final String SQL_TABLE_BANK_ID = "id_banks";
    private static final String SQL_TABLE_BANK_NAME = "name_banks";
    private static final String SQL_SELECT_BANK = "SELECT * FROM banks";
    private static final String INSERT_NAME_BANK = "INSERT INTO banks (name_banks) VALUES(?)";

    @Override
    public void createBank(String nameBank) throws DAOException {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnectionPool connectionPool = null;

        try {

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();

            ps = connection.prepareStatement(INSERT_NAME_BANK);
            ps.setString(1, nameBank);
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
    public List<Bank> getAllBank() throws DAOException {
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        ConnectionPool connectionPool = null;
        List<Bank> banks = null;
        Bank bank = null;

        try {

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();

            ps = connection.prepareStatement(SQL_SELECT_BANK);
            rs = ps.executeQuery();

            banks = new ArrayList<>();

            while (rs.next()) {

                bank = new Bank();
                bank.setId(rs.getInt(SQL_TABLE_BANK_ID));
                bank.setName(rs.getString(SQL_TABLE_BANK_NAME));
                banks.add(bank);
            }

        } catch (ConnectionPoolRuntimeException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return banks;
    }
}
