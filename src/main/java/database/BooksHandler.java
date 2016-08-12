package database;

import dataobjects.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BooksHandler extends DatabaseHandler {
    private Connection conn = null;
    private Statement stmt = null;
    public Logger logger = Logger.getLogger("BooksHandler");
    public final String DB_NAME = "ddnoin2chhlihm";

    private List convertRsToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        //List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        List< BookObject > bookList = new ArrayList< BookObject> ();
        while(rs.next()) {
            //HashMap<String, Object> row = new HashMap<String, Object>(columns);
            BookObject bookObject = new BookObject();

            for(int i = 1; i<=columns; ++i){
                //row.put(md.getColumnName(i), rs.getObject(i));
                String mid = md.getColumnName(i);
                if(rs.getObject(i) == null)
                    continue;
                else if(rs.getObject(i) != null){
                    bookObject.setItem(md.getColumnName(i), rs.getObject(i));
            }
            }
            //list.add(row);
            bookList.add(bookObject);
        }
        //return list;
        return bookList;
    }

    public List selectBook(String tableName, Object value, String columnName) throws SQLException {
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


    public void insertBook(String tableName, BookObject bookObject) throws SQLException {
        insertBook(tableName, bookObject);
    }

}
