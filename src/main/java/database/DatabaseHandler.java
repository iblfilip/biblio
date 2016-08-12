package database;

import dataobjects.BookColumnEnum;
import dataobjects.BookObject;
import dataobjects.TableNameEnum;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseHandler {
    private Connection conn = null;
    private Statement stmt = null;
    public Logger logger = Logger.getLogger("BooksHandler");
    Properties prop = new Properties();

    public Connection connect() {
        try{
            GetPropertyValues getPropertyValues = new GetPropertyValues();
            prop = getPropertyValues.getPropValues();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error during getting database properties", e);
        }
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager
                    .getConnection("jdbc:postgresql://" + prop.getProperty("host")+ ":" + prop.getProperty("port")+ "/" + prop.getProperty("database")+ "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory",
                            prop.getProperty("user"), prop.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            logger.log(Level.SEVERE, "Error during connecting the database", e);
            System.exit(0);
        }
        logger.log(Level.INFO,">>database opened successfully");
        return conn;
    }

    public void update(String tableName, Object changedValue, String changedColumn, Object searchedValue, String searchedColumn) throws SQLException {
        Connection conn = connect();
        PreparedStatement updateTable = null;
        stmt = conn.createStatement();
        String sql = "UPDATE " + tableName + " SET " + changedColumn + "=? WHERE " + searchedColumn + " =?;";

        try {
            conn.setAutoCommit(false);
            updateTable = conn.prepareStatement(sql);
            updateTable.setObject(1, changedValue);
            updateTable.setObject(2, searchedValue);
            updateTable.executeUpdate();
            conn.commit();
            logger.log(Level.INFO, ">>update statement executed {0}", updateTable.toString());
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Error during executing UPDATE statement ", e);
        }
        finally {
            if(updateTable != null)
                updateTable.close();
        }
        conn.setAutoCommit(true);
    }

    public void delete(String tableName, Object title, String columnName) throws SQLException {
        Connection conn = connect();
        PreparedStatement deleteTable = null;
        String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
        try{
            conn.setAutoCommit(false);
            deleteTable = conn.prepareStatement(sql);
            deleteTable.setObject(1, title);
            deleteTable.executeUpdate();
            conn.commit();
            logger.log(Level.INFO, "DELETE query executed {0}", deleteTable.toString());
        }
        catch(SQLException e) {
            logger.log(Level.SEVERE, "Exeption during executing DELETE statement", e);
        }
        finally {
            if(deleteTable != null)
                deleteTable.close();
        }
        conn.setAutoCommit(true);
    }

    public List select(String tableName, Object value, String columnName) throws SQLException {
        Connection conn = connect();
        PreparedStatement select = null;
        List values= null;
        stmt = conn.createStatement();
        String sql = "SELECT * FROM " + tableName + " WHERE " + columnName + "=?";
        try{
            conn.setAutoCommit(false);
            select = conn.prepareStatement(sql);
            select.setObject(1, value);
            ResultSet rs = select.executeQuery();
            if(tableName.equals(TableNameEnum.borrows.name())) {
                BorrowsHandler borrowsHandler = new BorrowsHandler();
                values = borrowsHandler.convertRsToList(rs);
            }
            else if(tableName.equals(TableNameEnum.book.name())) {
                BooksHandler booksHandler = new BooksHandler();
                values = booksHandler.convertRsToList(rs);
            }
            logger.log(Level.INFO, "SELECT query executed {0}", select.toString());
        }
        catch(SQLException e) {
            logger.log(Level.SEVERE, "Exeption during executing SELECT statement", e);
        }
        finally {
            if(select != null)
                select.close();
        }
        conn.setAutoCommit(true);
        return values;
    }



}
