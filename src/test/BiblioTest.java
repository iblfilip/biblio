import dataobjects.BookObject;
import dataobjects.BorrowsColumnEnum;
import dataobjects.BorrowsObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import processor.Processor;
import dataobjects.BookColumnEnum;
import database.GetPropertyValues;

import java.io.IOException;
import java.util.List;

/**
 * Created by Filip on 03.08.2016.
 */
public class BiblioTest {
    public static String TITLE = "saturnin";
    public static String UPDATE_NEW_VALUE = "zdarbuh";
    public static int DELETE_ID = 4;

    public static String BORROWER = "laca";
    public static String GUARANTOR = "fili";
    Processor processor = new Processor();

    @Test
    public void SelectBookTest() {
        List<BookObject> x = processor.selectBook(6, BookColumnEnum.id);
        BookObject bookOb = x.get(0);
        Assert.assertEquals(bookOb.getGenre(), "zdarbuh", "selected value is not existing");
    }

    @Test
    public void UpdateBookTest() {
        processor.updateBook(2020, BookColumnEnum.year_of_publish, 5, BookColumnEnum.id);
        List<BookObject> x = processor.selectBook(2020, BookColumnEnum.year_of_publish);
        BookObject bookOb = x.get(0);
        Assert.assertEquals(bookOb.getYear_of_publish(), (Integer)2020);
    }
    @Test
    public void deleteBookTest() {
        processor.deleteBook(DELETE_ID, BookColumnEnum.id);
    }


    @Test
    public void updateBorrowsTest() {
        processor.updateBorrows("laca", BorrowsColumnEnum.borrowed_to, 1, BorrowsColumnEnum.id);
        List<BorrowsObject> x = processor.selectBorrows("laca", BorrowsColumnEnum.borrowed_to);
        BorrowsObject borob = x.get(0);
        Assert.assertEquals(borob.getGuarantor(), GUARANTOR);
    }
    @Test
    public void selectBorrowsTest() {
        List <BorrowsObject> borrowsList = processor.selectBorrows(BORROWER, BorrowsColumnEnum.borrowed_to);
        BorrowsObject borrowsObject = borrowsList.get(0);
        Assert.assertEquals(borrowsObject.getGuarantor(), GUARANTOR, "select databaseprocessor is not correct" );
    }
    @Test
    public void deleteBorrowsTest() throws IOException {
        GetPropertyValues getPropertyValues = new GetPropertyValues();
        getPropertyValues.getPropValues();
    }

}