package com.example.amitgyawali.bookupp.Database;

/**
 * Created by kushal on 3/28/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.amitgyawali.bookupp.Tools.BookInformation;

public class Database {
    private static SQLiteDatabase db = null;

    private Database(){}

    public static void insertBook(BookInformation book, Context context){
        if(db == null){
            initializeDB(context);
        }

        db.insertWithOnConflict(BookScannerContract.Books.TABLE_NAME, "null", packBookContentValues(book), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static void deleteBookById(int bookId, Context context){
        if(db == null){
            initializeDB(context);
        }
        if(bookId < 0){
            return;
        }
        String[] whereArgs = new String[]{String.valueOf(bookId)};
        db.delete(BookScannerContract.Books.TABLE_NAME, BookScannerContract.Books._ID + "=?", whereArgs);
    }

    public static Cursor getAllBooks(Context context){
        if(db == null){
            initializeDB(context);
        }
        return db.query(BookScannerContract.Books.TABLE_NAME, null, null, null, null, null, BookScannerContract.Books.COLUMN_NAME_TITLE + " ASC");
    }

    private static void initializeDB(Context context){
        BookScannerDbHelper helper = new BookScannerDbHelper(context);
        db = helper.getWritableDatabase();
    }

    private static ContentValues packBookContentValues(BookInformation book){
        ContentValues values = new ContentValues();
        values.put(BookScannerContract.Books.COLUMN_NAME_ISBN, book.isbn);
        values.put(BookScannerContract.Books.COLUMN_NAME_TITLE, book.title);
        values.put(BookScannerContract.Books.COLUMN_NAME_AVERAGE_RATING, book.averageRating);
        values.put(BookScannerContract.Books.COLUMN_NAME_DESCRIPTION, book.description);
        values.put(BookScannerContract.Books.COLUMN_NAME_PAGE_COUNT, book.pageCount);
        values.put(BookScannerContract.Books.COLUMN_NAME_RATINGS_COUNT, book.ratingsCount);
        values.put(BookScannerContract.Books.COLUMN_NAME_DATE_SCANNED, book.timeLastUpdated);
        values.put(BookScannerContract.Books.COLUMN_NAME_IMAGE_URL, book.imageURL);
        values.put(BookScannerContract.Books.COLUMN_NAME_LOCATION, book.location);
        return values;
    }
}

