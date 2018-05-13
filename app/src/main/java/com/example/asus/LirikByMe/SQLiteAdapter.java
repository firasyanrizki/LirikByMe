package com.example.asus.LirikByMe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteAdapter {
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public SQLiteAdapter(Context context) {
        this.context = context;
    }
    //    Table Entry
    private String CREATE_TABLE_ENTRY = "CREATE TABLE " + EntryContract.Entry.TABLE_ENTRY + '('
            + EntryContract.Entry.KEY_TITLE + " TEXT NOT NULL PRIMARY KEY, "
            + EntryContract.Entry.KEY_LINK + " TEXT NOT NULL, "
            + EntryContract.Entry.KEY_TYPE + " TEXT NOT NULL, "
            + EntryContract.Entry.KEY_LANGUAGE + " TEXT NOT NULL, "
            + EntryContract.Entry.KEY_LYRIC + " TEXT NOT NULL);" ;
//    Table Genre

    private String CREATE_TABLE_GENRE = "CREATE TABLE " + EntryContract.Entry.TABLE_GENRE + '('
            + EntryContract.Entry.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + EntryContract.Entry.KEY_TITLE + " TEXT, "
            + EntryContract.Entry.KEY_GENRE + " TEXT);";

    public SQLiteAdapter openToRead() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, EntryContract.DATABASE_NAME, null, EntryContract.DATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }

    public SQLiteAdapter openToWrite() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, EntryContract.DATABASE_NAME, null, EntryContract.DATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        return this;
    }
    public long insertEntry(String title, String link, String language, String type, String lyric){
        ContentValues contentValues = new ContentValues();
        contentValues.put(EntryContract.Entry.KEY_TITLE, title);
        contentValues.put(EntryContract.Entry.KEY_LINK, link);
        contentValues.put(EntryContract.Entry.KEY_LANGUAGE, language);
        contentValues.put(EntryContract.Entry.KEY_TYPE, type);
        contentValues.put(EntryContract.Entry.KEY_LYRIC, lyric);
        return sqLiteDatabase.insert(EntryContract.Entry.TABLE_ENTRY, null, contentValues);
    }
    public long insertGenre(String title, String genre){
        ContentValues contentValues = new ContentValues();
        contentValues.put(EntryContract.Entry.KEY_TITLE, title);
        contentValues.put(EntryContract.Entry.KEY_GENRE, genre);
        return sqLiteDatabase.insert(EntryContract.Entry.TABLE_GENRE, null, contentValues);
    }
    public int deleteAll(){
        sqLiteDatabase.delete(EntryContract.Entry.TABLE_GENRE, null, null);
        return sqLiteDatabase.delete(EntryContract.Entry.TABLE_ENTRY, null, null);
    }
    public Cursor selectWhereTitle(String title){
        String[] columns = new String[]{EntryContract.Entry.KEY_TITLE,
                EntryContract.Entry.KEY_LINK, EntryContract.Entry.KEY_LYRIC};
        String[] where = new String[]{title};
        Cursor cursor = sqLiteDatabase.query(EntryContract.Entry.TABLE_ENTRY, columns,
                EntryContract.Entry.KEY_TITLE + " = ?", where, null, null, null);
        return cursor;
    }
    public long selectCountWhereGenre(String genre){
        String[] where = new String[]{genre};
        long sum = DatabaseUtils.queryNumEntries(sqLiteDatabase, EntryContract.Entry.TABLE_GENRE,
                EntryContract.Entry.KEY_GENRE + " = ?", where);
        return sum;
    }
    public Cursor queueAll(){
        String[] columns = new String[]{EntryContract.Entry.KEY_TITLE};
        Cursor cursor = sqLiteDatabase.query(EntryContract.Entry.TABLE_ENTRY, columns,
                null, null, null, null, null);
        return cursor;
    }
    public void close(){
        sqLiteHelper.close();
    }

    public class SQLiteHelper extends SQLiteOpenHelper {

        public SQLiteHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENTRY);
        db.execSQL(CREATE_TABLE_GENRE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + EntryContract.Entry.TABLE_ENTRY);
        db.execSQL("DROP TABLE IF EXISTS " + EntryContract.Entry.TABLE_GENRE);
// Creating tables again
        onCreate(db);
        }
    }
}
