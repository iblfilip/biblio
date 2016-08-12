package database;

import dataobjects.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BooksHandler extends DatabaseHandler {
    public Logger logger = Logger.getLogger("BooksHandler");

    public List convertRsToList(ResultSet rs) throws SQLException {
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

    public void insert(String tableName, BookObject bookObject) throws SQLException {
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
            logger.log(Level.SEVERE, "Exeption during executing INSERT statement", e);
        }
        finally {
            if(insertInto != null)
                insertInto.close();
        }
        conn.setAutoCommit(true);
    }

}
