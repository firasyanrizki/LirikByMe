package com.example.asus.LirikByMe;

import android.provider.BaseColumns;

public class EntryContract {
    // Database Version
    public static final int DATABASE_VERSION = 1;
    // Database Name
    public static final String DATABASE_NAME = "com.example.asus.lirikbyme.db";

    public class Entry implements BaseColumns {
        // Table name
        public static final String TABLE_ENTRY = "entry";
        public static final String TABLE_GENRE = "lyric_genre";
        // Table Columns names
        public static final String KEY_ID = "id";
        public static final String KEY_TITLE = "title";
        public static final String KEY_LINK = "link";
        public static final String KEY_GENRE = "genre";
        public static final String KEY_TYPE = "type";
        public static final String KEY_LANGUAGE = "language";
        public static final String KEY_LYRIC = "lyric";
    }
}
