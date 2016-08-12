package database;

import dataobjects.BookColumnEnum;
import dataobjects.BookObject;
import java.io.IOException;
import java.sql.*;
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

    public void updateBooks(String tableName, Object changedValue, String changedColumn, Object searchedValue, String searchedColumn) throws SQLException {
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

    public void deleteBook(String tableName, Object title, String columnName) throws SQLException {
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

    public void insertBook(String tableName, BookObject bookObject) throws SQLException {
        Connection conn = connect();
        PreparedStatement insertInto = null;
        String sql = "INSERT INTO " + tableName + " (" + BookColumnEnum.title.name()+ "," + BookColumnEnum.author_name.name() +","+ BookColumnEnum.year_of_publish.name() +","+ BookColumnEnum.place_of_publish.name() +","+ BookColumnEnum.isbn.name() +","+ BookColumnEnum.genre.name() +","+ BookColumnEnum.rating.name() +","+ BookColumnEnum.publisher.name() +","+ BookColumnEnum.notes.name() +","+ BookColumnEnum.quantity.name() + ") VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            conn.setAutoCommit(false);
            insertInto = conn.prepareStatement(sql);
            insertInto.setString(1,bookObject.getTitle());
            insertInto.setString(2,bookObject.getAuthor_name());

            if(bookObject.getYear_of_publish()== null)
                insertInto.setNull(3, Types.INTEGER);
            else insertInto.setInt(3,bookObject.getYear_of_publish());

            insertInto.setString(4,bookObject.getPlace_of_publish());

            if(bookObject.getIsbn()== null)
                insertInto.setNull(5, Types.INTEGER);
            else insertInto.setInt(5,bookObject.getIsbn());

            insertInto.setString(6,bookObject.getGenre());
            insertInto.setString(7,bookObject.getRating());
            insertInto.setString(8,bookObject.getPublisher());
            insertInto.setString(9,bookObject.getNotes());

            if(bookObject.getQuantity()== null)
                insertInto.setNull(10, Types.INTEGER);
            else insertInto.setInt(10,bookObject.getQuantity());

            insertInto.executeUpdate();
            conn.commit();
            logger.log(Level.INFO, "INSERT INTO query executed {0}", insertInto.toString());
        }
        catch(SQLException e) {
            logger.log(Level.SEVERE, "Exeption during executing DELETE statement", e);
        }
        finally {
            if(insertInto != null)
                insertInto.close();
        }
        conn.setAutoCommit(true);
    }

}
