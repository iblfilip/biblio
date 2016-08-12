package processor;

import databaseprocessor.DatabaseProcessor;
import database.BooksHandler;
import database.BorrowsHandler;
import dataobjects.*;

import java.sql.SQLException;
import java.util.List;


public class Processor {

    BooksHandler handler = new BooksHandler();
    //BookFactory bookFactory = new BookFactory();
    //BorrowsHandler borrowsHandler = new BorrowsHandler();

    public void bla() throws SQLException {
        BooksHandler handler = new BooksHandler();
        //BookFactory bookFactory = new BookFactory();
        //Book book1 = BookFactory.getBook("title");
        //List x = book1.selectBook("author_name", "pan prstenu", handler);
        //book1.deleteBook("capka", handler);

        //Book book2 = BookFactory.getBook("author_name");
        //List x = book2.selectBook("jirotka", handler);

        //Book book3 = BookFactory.getBook(BookColumnEnum.title.name());
        //List<BookObject> x = book3.selectBook("ahoj", handler);
        //System.out.println(x.toString());
        //BookObject bookOb = x.get(0);
       // int idecko = bookOb.getId();

        //handler.insertBook(bookOb);
    }

    public List selectBook(Object value, BookColumnEnum column) {
        DatabaseProcessor databaseProcessor = new DatabaseProcessor(TableNameEnum.book);
        List <BookObject> booksList = databaseProcessor.selectItem(value, column.name());
        return booksList;
    }
    public void updateBook(Object changedValue, BookColumnEnum changedColumn, Object searchedValue, BookColumnEnum searchedColumn) {
        DatabaseProcessor databaseProcessor = new DatabaseProcessor(TableNameEnum.book);
        databaseProcessor.updateItem(changedValue, changedColumn.name(), searchedValue, searchedColumn.name());
    }
    public void deleteBook(Object value, BookColumnEnum column) {
        DatabaseProcessor databaseProcessor = new DatabaseProcessor(TableNameEnum.book);
        databaseProcessor.deleteItem(value, column.name());
    }
    public List selectBorrows(String value, BorrowsColumnEnum column) {
        DatabaseProcessor databaseProcessor = new DatabaseProcessor(TableNameEnum.borrows);
        List <BorrowsObject> borrowsList = databaseProcessor.selectItem(value, column.name());
        return borrowsList;
    }
    public void updateBorrows(Object changedValue, BorrowsColumnEnum changedColumn, Object searchedValue, BorrowsColumnEnum searchedColumn) {
        DatabaseProcessor databaseProcessor = new DatabaseProcessor(TableNameEnum.borrows);
        databaseProcessor.updateItem(changedValue, changedColumn.name(), searchedValue, searchedColumn.name());
    }
    public void deleteBorrows(Object value, BorrowsColumnEnum column) {
        DatabaseProcessor databaseProcessor = new DatabaseProcessor(TableNameEnum.borrows);
        databaseProcessor.deleteItem(value, column.name());
    }
}
