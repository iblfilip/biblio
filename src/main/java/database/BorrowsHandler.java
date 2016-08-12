package database;

import dataobjects.BookObject;
import dataobjects.BorrowsColumnEnum;
import dataobjects.BorrowsObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BorrowsHandler extends DatabaseHandler {
    public Logger logger = Logger.getLogger("BooksHandler");

    public List convertRsToList(ResultSet rs) throws SQLException {
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

    public void insert(String tableName, BorrowsObject borrowsObject) throws SQLException {
        Connection conn = connect();
        PreparedStatement insertInto = null;
        String sql = "insert into " + tableName + " ("  + BorrowsColumnEnum.id_book + ", " + BorrowsColumnEnum.borrowed_to + ", "+ BorrowsColumnEnum.borrowed_from_date + ", " + BorrowsColumnEnum.guarantor + ", " + BorrowsColumnEnum.note + ") VALUES (?,?,?,?,?)";
        try{
            conn.setAutoCommit(false);
            insertInto = conn.prepareStatement(sql);
            if (borrowsObject.getId_book()==null)
                insertInto.setNull(1, Types.INTEGER);
            else insertInto.setInt(1,borrowsObject.getId_book());
            insertInto.setString(2, borrowsObject.getBorrowed_to());
            insertInto.setDate(3, borrowsObject.getBorrowed_from_date());
            insertInto.setString(4, borrowsObject.getGuarantor());
            insertInto.setString(5, borrowsObject.getNote());
            insertInto.executeUpdate();
            conn.commit();
            logger.log(Level.INFO, "INSERT INTO query executed {0}", insertInto.toString());
        }
        catch(SQLException e) {
            logger.log(Level.SEVERE, "Exeption during executing INSERT statement", e);
        }
        finally {
            if(insertInto != null)
                insertInto.close();
        }
        conn.setAutoCommit(true);
    }
}
