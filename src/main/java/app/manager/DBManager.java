package app.manager;

import app.Fields;

import app.been.*;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public final class DBManager {

    private static final int RANDOM_N = 100000000;
    private static final int MAGIC_T = 888888888;

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

    private static final String SQL_INSERT_NOVAR_PAYMENT_SERVICE = "INSERT INTO payment (id_status,date,value,personal_account)VALUES (?,?,?,?)";

    private static final String SQL_INSERT_NOVAR_PAYMENT = "INSERT INTO payment (id_status,date,value)VALUES (?,?,?)";

    private static final String SQL_INSERT_NOVAR_FROM = "INSERT INTO payment_account (id_payment,id_account,id_payment_type)VALUES (?,?,?)";


    private static final String SQL_GET_USER_CARDS = "SELECT account.id_account, credit_card.number, credit_card.date, account.balance," +
            " account.name, account_status.title FROM (account INNER JOIN credit_card   ON credit_card.id_account =" +
            " account.id_account) INNER JOIN account_status  ON account.id_status = account_status.id_status " +
            "where credit_card.id_user = ?";

    private static final String SQL_GET_ALL_USERS_CARDS = "SELECT user.login, account.id_account, credit_card.number, credit_card.date, account.balance," +
            "             account.name, account_status.title, account.request  FROM ((account INNER JOIN credit_card  ON credit_card.id_account =" +
            "            account.id_account) INNER JOIN account_status  ON account.id_status = account_status.id_status)" +
            "            INNER JOIN user ON   credit_card.id_user = user.id_user;";

    private static final String SQL_GET_USER_PAYMENTS_TRANS = "SELECT credit_card.number, payment.value, payment.date from (credit_card inner JOIN" +
            "             payment_account on payment_account.id_account = credit_card.id_account) inner join payment on payment_account.id_payment = payment.id_payment" +
            "             Where payment_account.id_payment_type = '2' and payment_account.id_payment = ?";

    private static final String SQL_GET_USER_PAYMENTS_SERV = "SELECT payment.personal_account, payment.value, payment.date, services.title from ((payment inner join  " +
            "            payment_account on payment_account.id_payment = payment.id_payment)  inner JOIN account on account.id_account = payment_account.id_account ) " +
            "inner join  services on services.id_account = account.id_account where" +
            "             payment_account.id_payment_type = '2' and payment_account.id_payment = ? and account.id_type = 1";

    private static final String SQL_GET_USER_PAYMENTS_FROM = "SELECT credit_card.number FROM credit_card inner JOIN payment_account on payment_account.id_account = credit_card.id_account" +
            "             Where payment_account.id_payment_type = '1' and credit_card.id_user = ? and payment_account.id_payment = ?";



    private static final String SQL_GET_USER_ID_PAYMENTS = "SELECT payment_account.id_payment From payment_account inner JOIN account on payment_account.id_account = account.id_account" +
            " inner join credit_card on credit_card.id_account = account.id_account  Where credit_card.id_user = ? and payment_account.id_payment_type = '1'";



    private static final String SQL_GET_ALL_USER = "SELECT user.id_user, user.login, user.phone, user.first_name, user.last_name, " +
            "user_status.title FROM user INNER JOIN user_status  on user.id_status = user_status.id_status";

    private static final String SQL_UNBLOCK_CARD = "UPDATE account SET id_status = '2', request = '' WHERE id_account = ?";

    private static final String SQL_BLOCK_CARD = "UPDATE account SET id_status = '1' WHERE id_account = ?";

    private static final String SQL_UNBLOCK_USER = "UPDATE user SET id_status = '2' WHERE id_user = ?";

    private static final String SQL_BLOCK_USER = "UPDATE user SET id_status = '1' WHERE id_user = ?";

    private static final String SQL_REQUEST_UNBLOCK_CARD = "UPDATE account SET request = 'Unblock this card' WHERE id_account = ?";

    private static final String SQL_GET_USER_CARD = "SELECT credit_card.number, credit_card.id_account FROM credit_card WHERE credit_card.id_user = ?";

    private static final String SQL_GET_SERVICE = "SELECT services.title, services.id_account FROM services WHERE services.service = ?";

    private static final String SQL_GET_RECIPIENT_CARD = "SELECT credit_card.number, credit_card.id_account FROM credit_card WHERE credit_card.number = ?";

    private static final String SQL_GET_BALANCE = "SELECT account.balance FROM account WHERE id_account = ?";

    private static final String SQL_PERFORM_PAYMENT = "UPDATE account SET account.balance = ? WHERE id_account = ?";

    private static final String SQL_UPDATE_PAYMENT_STATUS = "UPDATE payment SET payment.id_status = '2' WHERE id_payment = ?";

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

    public boolean blockUser(String status, int id_user) throws DBException {
        boolean block;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            if ("blocked".equals(status)) {
                pstmt = con.prepareStatement(SQL_UNBLOCK_USER);
                con.setAutoCommit(false);
                pstmt.setInt(1, id_user);
            } else if ("unblocked".equals(status)) {
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
    public boolean findCardByLogin(User user, String number) throws DBException {
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
            if (user.getId_type() == 2) {
                pstmt = con.prepareStatement(SQL_GET_USER_CARDS);
                con.setAutoCommit(false);
                pstmt.setLong(1, user.getId_user());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    viewCard.add(extractUserCardsBean(rs));
                }
            } else {
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

    public List<ViewCard> getCards(User user) throws DBException {
        List<ViewCard> viewCard = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_GET_USER_CARD);
            con.setAutoCommit(false);
            pstmt.setLong(1, user.getId_user());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                viewCard.add(extractUsersCardsBean(rs));
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

    public ViewCard getRecipientCard(int number) throws DBException {
        ViewCard viewCard = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_GET_RECIPIENT_CARD);
            con.setAutoCommit(false);
            pstmt.setLong(1, number);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                viewCard = extractUsersCardsBean(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_BLOCK_RECIPIENT_CARD, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return viewCard;
    }

    public int getBalance(int number) throws DBException {
        int balance = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_GET_BALANCE);
            con.setAutoCommit(false);
            pstmt.setLong(1, number);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                balance = rs.getInt(1);
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_BLOCK_RECIPIENT_CARD, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return balance;
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

    private ViewCard extractUsersCardsBean(ResultSet rs)
            throws SQLException {
        ViewCard bean = new ViewCard();
        bean.setNumber(rs.getInt(Fields.USER_BEAN_CARD_NUMBER));
        bean.setId_account(rs.getInt(Fields.USER_BEAN_CARD_ID_ACCOUNT));
        return bean;
    }

    private ViewService extractServiceBean(ResultSet rs)
            throws SQLException {
        ViewService bean = new ViewService();
        bean.setTitle(rs.getString(Fields.SERVICE_BEAN_TITLE));
        bean.setId_account(rs.getInt(Fields.SERVICE_BEAN_ID_ACCOUNT));
        return bean;
    }

    public boolean blockCard(int id_account, String status, int type) throws DBException {
        boolean block;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            if ("blocked".equals(status)) {
                pstmt = con.prepareStatement(SQL_UNBLOCK_CARD);
                con.setAutoCommit(false);
                pstmt.setInt(1, id_account);
            } else if ("unblocked".equals(status)) {
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

    //service section
    public List<ViewService> getService(String service) throws DBException {
        List<ViewService> viewService = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_GET_SERVICE);
            con.setAutoCommit(false);
            pstmt.setString(1, service);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                viewService.add(extractServiceBean(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_SERVICES, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return viewService;
    }

    //account section
    public int insertNewAccount(String type) throws DBException {
        int id = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SecureRandom rnd = new SecureRandom();
        int balance = rnd.nextInt(10000);
        int number = rnd.nextInt((RANDOM_N)) + MAGIC_T;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
            con.setAutoCommit(false);
            pstmt.setInt(1, number);
            pstmt.setInt(2, balance);
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

    //payment section

    public int insertPayment(int howMuch) throws DBException {
        int id = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            con = getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_NOVAR_PAYMENT, Statement.RETURN_GENERATED_KEYS);
            con.setAutoCommit(false);
            pstmt.setInt(1, 1);
            pstmt.setString(2, dateFormat.format(date));
            pstmt.setInt(3, howMuch);
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

    public int insertPaymentService(int howMuch, int personal_account) throws DBException {
        int id = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            con = getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_NOVAR_PAYMENT_SERVICE, Statement.RETURN_GENERATED_KEYS);
            con.setAutoCommit(false);
            pstmt.setInt(1, 1);
            pstmt.setString(2, dateFormat.format(date));
            pstmt.setInt(3, howMuch);
            pstmt.setInt(4, personal_account);
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

    public boolean insertFromTo(int who, int id_account, int id_payment) throws DBException {
        boolean flag;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_INSERT_NOVAR_FROM);
            con.setAutoCommit(false);
            pstmt.setInt(1, id_payment);
            pstmt.setInt(2, id_account);
            pstmt.setInt(3, who);
            pstmt.executeUpdate();
            con.commit();
            flag = true;
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CREATE_PAYMENT_FORM_TO, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return flag;
    }

    public boolean performPayment(int id_account, int balance) throws DBException {
        boolean flag;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_PERFORM_PAYMENT);
            con.setAutoCommit(false);
            pstmt.setInt(1, balance);
            pstmt.setInt(2, id_account);
            pstmt.executeUpdate();
            con.commit();
            flag = true;
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_PERFORM_BALANCE, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return flag;
    }

    public boolean updateStatusPayment(int id_payment) throws DBException {
        boolean flag;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_UPDATE_PAYMENT_STATUS);
            con.setAutoCommit(false);
            pstmt.setInt(1, id_payment);
            pstmt.executeUpdate();
            con.commit();
            flag = true;
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_UPDATE_PAYMENT_STATUS, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return flag;
    }

    public ViewPayment getUserNumberFrom (int id_payment,User user,List<ViewPayment> viewPayments) throws DBException {
        ViewPayment been = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
                pstmt = con.prepareStatement(SQL_GET_USER_PAYMENTS_FROM);
                con.setAutoCommit(false);
                pstmt.setLong(1, user.getId_user());
                pstmt.setInt(2, id_payment);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                   been = extractListFromBeen(rs);
                }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_PAYMENT_FROM_LIST, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return been;
    }

    private ViewPayment extractListFromBeen(ResultSet rs)
            throws SQLException {
        ViewPayment bean = new ViewPayment();
        bean.setNumberFrom(rs.getInt(Fields.USER_BEAN_CARD_NUMBER_FROM));
        return bean;
    }

    public boolean getUserPayment (int id_payment,List<ViewPayment> viewPayments,ViewPayment payment) throws DBException {
        boolean flag;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_GET_USER_PAYMENTS_TRANS);
            con.setAutoCommit(false);
            pstmt.setLong(1, id_payment);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                viewPayments.add(extractListPaymentBeen(rs,payment));
            }
            pstmt = con.prepareStatement(SQL_GET_USER_PAYMENTS_SERV);
            con.setAutoCommit(false);
            pstmt.setLong(1, id_payment);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                viewPayments.add(extractListPayment2Been(rs,payment));
            }
            con.commit();
            flag = true;
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_PAYMENT, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return flag;
    }

    public List<ListPayment> getUserIdPayment (User user) throws DBException {
        List<ListPayment> listPayment = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_GET_USER_ID_PAYMENTS);
            con.setAutoCommit(false);
            pstmt.setLong(1, user.getId_user());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                listPayment.add(extractListIdPaymentBeen(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_PAYMENT_FROM_LIST, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return listPayment;
    }

    private ListPayment extractListIdPaymentBeen(ResultSet rs)
            throws SQLException {
        ListPayment bean = new ListPayment();
        bean.setId_payment(rs.getInt(Fields.USER_BEAN_CARD_ID_PAYMENT));
        return bean;
    }
    private ViewPayment extractListPayment2Been(ResultSet rs,ViewPayment payment)
            throws SQLException {
        payment.setNumber(rs.getInt(Fields.USER_BEAN_CARD_PERSONAL_ACCOUNT));
        payment.setValue(rs.getInt(Fields.USER_BEAN_PAYMENT_VALUE));
        payment.setDate(rs.getString(Fields.USER_BEAN_PAYMENT_DATE));
        payment.setService(rs.getString(Fields.USER_BEAN_CARD_SERVICE));
        return payment;
    }
    private ViewPayment extractListPaymentBeen(ResultSet rs, ViewPayment payment)
            throws SQLException {
        payment.setNumber(rs.getInt(Fields.USER_BEAN_CARD_NUMBER));
        payment.setValue(rs.getInt(Fields.USER_BEAN_PAYMENT_VALUE));
        payment.setDate(rs.getString(Fields.USER_BEAN_PAYMENT_DATE));
        payment.setService("transfer between cards");
        return payment;
    }
    //close section
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