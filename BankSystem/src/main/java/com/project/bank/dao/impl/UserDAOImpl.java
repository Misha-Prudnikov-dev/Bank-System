package com.project.bank.dao.impl;

import com.project.bank.dao.UserDAO;
import com.project.bank.dao.connection.ConnectionPool;
import com.project.bank.dao.connection.ConnectionPoolRuntimeException;
import com.project.bank.dao.exception.DAOException;
import com.project.bank.dao.exception.PasswordAlreadyExistsDAOException;
import com.project.bank.dao.util.PBKDFHashing;
import com.project.bank.entity.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

public class UserDAOImpl implements UserDAO {

    private static final String SQL_TABLE_COLUMN_USER_ID = "id_users";
    private static final String SQL_TABLE_COLUMN_FIRST_NAME = "first_name_users";
    private static final String SQL_TABLE_COLUMN_LAST_NAME = "last_name_users";
    private static final String SQL_TABLE_COLUMN_EMAIL = "email_users";
    private static final String SQL_TABLE_COLUMN_PASSWORD = "password_users";
    private static final String SQL_TABLE_COLUMN_DATE = "created_users";
    private static final String SELECT_EMAIL_AND_PASSWORD = "SELECT * FROM users "
            + "WHERE users.email_users=? ";
    private static final String INSERT_USER_INFO = "INSERT INTO users" +
            "(first_name_users,last_name_users,email_users,password_users,created_users) " +
            "VALUES(?,?,?,?,?)";
    private static final String SELECT_EMAIL_USERS = "SELECT email_users,password_users FROM users WHERE email_users=?";


    @Override
    public User signIn(String email, String password) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnectionPool connectionPool = null;
        User user = null;

        try {

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SELECT_EMAIL_AND_PASSWORD);
            ps.setString(1, email);
            rs = ps.executeQuery();


            if (!rs.next()) {
                return null;
            }
            rs.beforeFirst();
            while (rs.next()) {

                if (PBKDFHashing.validatePassword(password,rs.getString(SQL_TABLE_COLUMN_PASSWORD))) {

                    user = new User();
                    user.setId(rs.getInt(SQL_TABLE_COLUMN_USER_ID));
                    user.setFirstName(rs.getString(SQL_TABLE_COLUMN_FIRST_NAME));
                    user.setLastName(rs.getString(SQL_TABLE_COLUMN_LAST_NAME));
                    user.setEmail(rs.getString(SQL_TABLE_COLUMN_EMAIL));
                    user.setCreated(rs.getDate(SQL_TABLE_COLUMN_DATE));
                    return user;
                }
            }
        } catch (ConnectionPoolRuntimeException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new DAOException(e);
        } catch (InvalidKeySpecException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return user;
    }

    @Override
    public User registration(User user, String userPassword) throws DAOException {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnectionPool connectionPool = null;

        try {

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            ps = connection.prepareStatement(INSERT_USER_INFO, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, PBKDFHashing.generateStorngPasswordHash(userPassword));
            ps.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            rs.next();

            int userId = rs.getInt(1);
            ps.close();
            user.setId(userId);

        } catch (ConnectionPoolRuntimeException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new DAOException(e);
        } catch (InvalidKeySpecException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return user;
    }

    @Override
    public boolean checkPasswordUsed(String email, String userPassword) throws DAOException {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConnectionPool connectionPool = null;

        try {

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SELECT_EMAIL_USERS);

            ps.setString(1, email);
            rs = ps.executeQuery();

            if (!rs.next()) {
                return false;
            }

            while (rs.next()) {

                if (PBKDFHashing.validatePassword(userPassword,rs.getString(SQL_TABLE_COLUMN_PASSWORD))) {
                    return true;
                }
            }

        } catch (ConnectionPoolRuntimeException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new DAOException(e);
        } catch (InvalidKeySpecException e) {
            throw new DAOException(e);
        }  finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return false;
    }

    @Override
    public User getUserInfo(int id) throws DAOException {
        return null;
    }
}
