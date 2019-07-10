package app.manager;

import app.Fields;

import app.been.ViewCard;
import app.been.ViewUsers;
import app.entities.Card;
import app.entities.User;
import app.exception.DBException;
import app.exception.Messages;
import org.apache.log4j.Logger;

import java.security.SecureRandom;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public final class DBManager {

    private static final int RANDOM_N = 100000000;
    private static final int MAGIC_T =  888888888;

    private static final Logger LOG = Logger.getLogger(DBManager.class);
    private static DBManager instance;

    public static synchronized DBManager getInstance() throws DBException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() throws DBException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            // ST4DB - the name of data source
            ds = (DataSource) envContext.lookup("jdbc/db");
        } catch (NamingException ex) {
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
        }
    }

    private DataSource ds;

    public Connection getConnection() throws DBException {
        Connection con = null;
        try {
            con = ds.getConnection();
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
        }
        return con;
    }


    // //////////////////////////////////////////////////////////
    // SQL queries
    // //////////////////////////////////////////////////////////

    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM user WHERE login=?";

    private static final String SQL_INSERT_USER = "INSERT INTO user  (login,password,phone,id_status,id_type,first_name,last_name)VALUES (?,?,?,?,?,?,?)";

    private static final String SQL_FIND_CARD_BY_LOGIN = "SELECT * FROM credit_card WHERE id_user=? AND number=?";

    private static final String SQL_INSERT_ACCOUNT = "INSERT INTO account (number,balance,id_type,id_status,name)VALUES (?,?,?,?,?)";

    private static final String SQL_INSERT_CARD = "INSERT INTO credit_card (number,date,id_user,id_account,code)VALUES (?,?,?,?,?)";

    private static final String SQL_GET_USER_CARDS = "SELECT account.id_account, credit_card.number, credit_card.date, account.balance," +
            " account.name, account_status.title FROM (account INNER JOIN credit_card   ON credit_card.id_account =" +
            " account.id_account) INNER JOIN account_status  ON account.id_status = account_status.id_status " +
            "where credit_card.id_user = ?";

    private static final String SQL_GET_ALL_USERS_CARDS = "SELECT user.login, account.id_account, credit_card.number, credit_card.date, account.balance," +
            "             account.name, account_status.title, account.request  FROM ((account INNER JOIN credit_card  ON credit_card.id_account =" +
            "            account.id_account) INNER JOIN account_status  ON account.id_status = account_status.id_status)" +
            "            INNER JOIN user ON   credit_card.id_user = user.id_user;";

    private static final String SQL_GET_ALL_USER = "SELECT user.id_user, user.login, user.phone, user.first_name, user.last_name, " +
            "user_status.title FROM user INNER JOIN user_status  on user.id_status = user_status.id_status";

    private static final String SQL_UNBLOCK_CARD = "UPDATE account SET id_status = '2', request = '' WHERE id_account = ?";

    private static final String SQL_BLOCK_CARD = "UPDATE account SET id_status = '1' WHERE id_account = ?";

    private static final String SQL_UNBLOCK_USER = "UPDATE user SET id_status = '2' WHERE id_user = ?";

    private static final String SQL_BLOCK_USER = "UPDATE user SET id_status = '1' WHERE id_user = ?";

    private static final String SQL_REQUEST_UNBLOCK_CARD = "UPDATE account SET request = 'Unblock this card' WHERE id_account = ?";

    //User section
    public User findUserByLogin(String login) throws DBException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            con.setAutoCommit(false);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }

            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return user;
    }

    public User insertNewUser(User user) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            con.setAutoCommit(false);

            pstmt.setString(1, user.getLogin());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getPhone());
            pstmt.setInt(4, 2);
            pstmt.setInt(5, 2);
            pstmt.setString(6, user.getFirstName());
            pstmt.setString(7, user.getFirstName());
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                // not "id"
                if (rs.next()) {
                    rs.getLong(1);
                }
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CREATE_USER, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return user;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId_user(rs.getLong(Fields.USER_ID));
        user.setLogin(rs.getString(Fields.USER_LOGIN));
        user.setPassword(rs.getString(Fields.USER_PASSWORD));
        user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
        user.setLastName(rs.getString(Fields.USER_LAST_NAME));
        user.setPhone(rs.getString(Fields.USER_PHONE));
        user.setId_type(rs.getInt(Fields.TYPE_USER_ID));
        user.setId_status(rs.getInt(Fields.STATUS_USER_ID));
        return user;
    }

    public List<ViewUsers> getAllUser() throws DBException {
        List<ViewUsers> viewUsers = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
                pstmt = con.prepareStatement(SQL_GET_ALL_USER);
                con.setAutoCommit(false);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    viewUsers.add(extractUsersBean(rs));
                }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return viewUsers;
    }

    private ViewUsers extractUsersBean(ResultSet rs)
            throws SQLException {
        ViewUsers bean = new ViewUsers();
        bean.setId_user(rs.getString(Fields.USER_ID));
        bean.setLogin(rs.getString(Fields.USER_LOGIN));
        bean.setPhone(rs.getString(Fields.USER_PHONE));
        bean.setFirst_name(rs.getString(Fields.USER_FIRST_NAME));
        bean.setLast_name(rs.getString(Fields.USER_LAST_NAME));
        bean.setStatus(rs.getString(Fields.STATUS_USER));
        return bean;
    }

    public boolean blockUser(String status,int id_user) throws DBException {
        boolean block;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            if("blocked".equals(status)){
                pstmt = con.prepareStatement(SQL_UNBLOCK_USER);
                con.setAutoCommit(false);
                pstmt.setInt(1, id_user);
            }else if("unblocked".equals(status)){
                pstmt = con.prepareStatement(SQL_BLOCK_USER);
                con.setAutoCommit(false);
                pstmt.setInt(1, id_user);
            }
            pstmt.executeUpdate();
            con.commit();
            block = true;
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_BLOCK_USER, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return block;
    }

    //Card section
    public boolean findCardByLogin(User user,String number) throws DBException {
        boolean cardExist = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_CARD_BY_LOGIN);
            con.setAutoCommit(false);
            pstmt.setLong(1, user.getId_user());
            pstmt.setString(2, number);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cardExist = true;
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CARD_BY_USER, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return cardExist;
    }

    public Card insertNewCard(Card card) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_CARD, Statement.RETURN_GENERATED_KEYS);
            con.setAutoCommit(false);
            pstmt.setInt(1, card.getNumber());
            pstmt.setString(2, card.getDate());
            pstmt.setLong(3, card.getId_user());
            pstmt.setLong(4, card.getId_account());
            pstmt.setInt(5, card.getCode());
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                // not "id"
                if (rs.next()) {
                    rs.getLong(1);
                }
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CREATE_CARD, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return card;
    }

    public List<ViewCard> getUserCards(User user) throws DBException {
        List<ViewCard> viewCard = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            if(user.getId_type()==2) {
                pstmt = con.prepareStatement(SQL_GET_USER_CARDS);
                con.setAutoCommit(false);
                pstmt.setLong(1, user.getId_user());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    viewCard.add(extractUserCardsBean(rs));
                }
            }else{
                pstmt = con.prepareStatement(SQL_GET_ALL_USERS_CARDS);
                con.setAutoCommit(false);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    viewCard.add(extractAdminUsersCardsBean(rs));
                }
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_CARDS, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return viewCard;
    }

    private ViewCard extractUserCardsBean(ResultSet rs)
            throws SQLException {
        ViewCard bean = new ViewCard();
        bean.setId_account(rs.getInt(Fields.USER_BEAN_CARD_ID_ACCOUNT));
        bean.setNumber(rs.getInt(Fields.USER_BEAN_CARD_NUMBER));
        bean.setBalance(rs.getInt(Fields.USER_BEAN_CARD_BALANCE));
        bean.setDate(rs.getString(Fields.USER_BEAN_CARD_DATE));
        bean.setName(rs.getString(Fields.USER_BEAN_CARD_NAME));
        bean.setStatus(rs.getString(Fields.USER_BEAN_CARD_STATUS));
        return bean;
    }
    private ViewCard extractAdminUsersCardsBean(ResultSet rs)
            throws SQLException {
        ViewCard bean = new ViewCard();
        bean.setLogin(rs.getString(Fields.USER_BEAN_CARD_LOGIN));
        bean.setId_account(rs.getInt(Fields.USER_BEAN_CARD_ID_ACCOUNT));
        bean.setNumber(rs.getInt(Fields.USER_BEAN_CARD_NUMBER));
        bean.setBalance(rs.getInt(Fields.USER_BEAN_CARD_BALANCE));
        bean.setDate(rs.getString(Fields.USER_BEAN_CARD_DATE));
        bean.setName(rs.getString(Fields.USER_BEAN_CARD_NAME));
        bean.setStatus(rs.getString(Fields.USER_BEAN_CARD_STATUS));
        bean.setRequest(rs.getString(Fields.USER_BEAN_CARD_REQUEST));
        return bean;
    }

    public boolean blockCard(int id_account,String status,int type) throws DBException {
        boolean block;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            if("blocked".equals(status)){
                pstmt = con.prepareStatement(SQL_UNBLOCK_CARD);
                con.setAutoCommit(false);
                pstmt.setInt(1, id_account);
            }else if("unblocked".equals(status)){
                pstmt = con.prepareStatement(SQL_BLOCK_CARD);
                con.setAutoCommit(false);
                pstmt.setInt(1, id_account);
            }
            pstmt.executeUpdate();
            con.commit();
             block = true;
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_BLOCK_CARD, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return block;
    }

    public boolean requestUnblockCard(int id_account) throws DBException {
        boolean requestSend;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_REQUEST_UNBLOCK_CARD);
            con.setAutoCommit(false);
            pstmt.setInt(1, id_account);
            pstmt.executeUpdate();
            con.commit();
            requestSend = true;
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_REQUSET_CANNOT_SEND, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return requestSend;
    }

    //account section
    public int insertNewAccount(String type) throws DBException {
        int id = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SecureRandom rnd = new SecureRandom();
        int balance =  rnd.nextInt(10000);
        int number = rnd.nextInt((RANDOM_N)) + MAGIC_T;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
            con.setAutoCommit(false);
            pstmt.setInt(1, number);
            pstmt.setInt(2,balance);
            pstmt.setInt(3, 2);
            pstmt.setInt(4, 2);
            pstmt.setString(5, type);
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                // not "id"
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CREATE_ACCOUNT, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return id;
    }


    private void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }


    //Closes a statement object.

    private void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }


    //Closes a result set object.

    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
            }
        }
    }


    // Closes resources.

    private void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }


    //Rollbacks a connection.

    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                LOG.error("Cannot rollback transaction", ex);
            }
        }
    }
}