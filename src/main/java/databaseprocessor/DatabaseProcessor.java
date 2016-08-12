package databaseprocessor;


import database.BooksHandler;
import database.BorrowsHandler;
import database.DatabaseHandler;
import dataobjects.*;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProcessor {

    static String tableName;

    public DatabaseProcessor(TableNameEnum tableNameEnum) {
        tableName = tableNameEnum.name();
    }

    public Logger logger = Logger.getLogger("DatabaseProcessorLogger");
    public DatabaseHandler handler = new DatabaseHandler();
    public BorrowsHandler borrowsHandler = new BorrowsHandler();
    public BooksHandler booksHandler = new BooksHandler();

    public List selectItem(Object value, String column) {
        List selected = null;
        if(tableName.equals(TableNameEnum.book.name())) {
            try {
                selected = booksHandler.selectBook(tableName, value, column);
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Exception during execution of SQL ", e);
            }
        }
        else if(tableName.equals(TableNameEnum.borrows.name())) {
            try {
                selected = borrowsHandler.selectBorrow(tableName, value, column);
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Exception during execution of SQL ", e);
            }
        }
        if(selected.size()==0) {
            logger.log(Level.INFO, "No results returned from SELECT statement");
        }
        return selected;
    }

    public void updateItem(Object changedValue, String changedColumn, Object searchedValue, String searchedColumn) {
        try {
            handler.updateBooks(tableName, changedValue, changedColumn, searchedValue, searchedColumn);
        }
        catch(SQLException e) {
            logger.log(Level.SEVERE, "Exeption during execution of SQL ", e);
        }
    }

    public void deleteItem(Object value, String column) {
        try{
            handler.deleteBook(tableName, value, column);
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Exeption during execution of SQL ", e);
        }
    }
    public void insertItem(BookObject bookObject) {
        try{
            handler.insertBook(tableName, bookObject);
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Exeption during execution of SQL ", e);
        }
    }
}
