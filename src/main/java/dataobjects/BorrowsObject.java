package dataobjects;

import java.util.Date;

public class BorrowsObject {
    int id;
    int id_book;
    String borrowed_to;
    Date borrowed_from_date;
    String guarantor;
    String note;

    public void setId(int id) {
        this.id = id;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }

    public void setBorrowed_to(String borrowed_to) {
        this.borrowed_to = borrowed_to;
    }

    public void setBorrowed_from_date(Date borrowed_from_date) {
        this.borrowed_from_date = borrowed_from_date;
    }

    public void setGuarantor(String guarantor) {
        this.guarantor = guarantor;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId_book() {
        return id_book;
    }

    public int getId() {
        return id;
    }

    public String getBorrowed_to() {
        return borrowed_to;
    }

    public Date getBorrowed_from_date() {
        return borrowed_from_date;
    }

    public String getGuarantor() {
        return guarantor;
    }

    public String getNote() {
        return note;
    }

    public void setItem(String columnName, Object item){
        if(columnName.equals(BorrowsColumnEnum.id.name()))
            setId((Integer)item);
        else if(columnName.equals(BorrowsColumnEnum.id_book.name()))
            setId_book((Integer)item);
        else if(columnName.equals(BorrowsColumnEnum.borrowed_to.name()))
            setBorrowed_to(item.toString());
        else if(columnName.equals(BorrowsColumnEnum.borrowed_from_date.name()))
            setBorrowed_from_date((Date) item);
        else if(columnName.equals(BorrowsColumnEnum.guarantor.name()))
            setGuarantor(item.toString());
        else if(columnName.equals(BorrowsColumnEnum.note.name()))
            setNote(item.toString());
    }
}
