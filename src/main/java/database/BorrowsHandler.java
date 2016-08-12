package database;

import dataobjects.BookObject;
import dataobjects.BorrowsObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Filip on 04.08.2016.
 */
public class BorrowsHandler extends DatabaseHandler {
    private Connection conn = null;
    private Statement stmt = null;
    public Logger logger = Logger.getLogger("BooksHandler");

    private List convertRsToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<BorrowsObject > borrowsList = new ArrayList< BorrowsObject>();
        while(rs.next()) {
            BorrowsObject borrowsObject = new BorrowsObject();

            for(int i = 1; i<=columns; ++i){

                String mid = md.getColumnName(i);
                if(rs.getObject(i) == null)
                    continue;
                else if(rs.getObject(i) != null){
                    borrowsObject.setItem(md.getColumnName(i), rs.getObject(i));
                }
            }
            borrowsList.add(borrowsObject);
        }
        return borrowsList;
    }

    public List selectBorrow(String tableName, Object value, String columnName) throws SQLException {
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
            values = convertRsToList(rs);
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

    public void insertBorrow(String tableName, BookObject bookObject) throws SQLException {
        insertBook(tableName, bookObject);
    }
}
