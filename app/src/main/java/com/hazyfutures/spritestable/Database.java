package com.hazyfutures.spritestable;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by cdavis on 1/17/2015.
 * Database helper
 */

public class Database extends SQLiteOpenHelper {

    public static final String TABLE_STATS = "Stats";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_STAT = "Stat";
    public static final String COLUMN_VALUE = "Value";
    private static final String STAT_DATABASE_CREATE = "create table "
            + TABLE_STATS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_STAT
            + " text not null, " + COLUMN_VALUE
            + " integer not null"
            + ");";
    public static final String TABLE_SPRITES = "Sprites";
    public static final String COLUMN_RATING = "Rating";
    public static final String COLUMN_TYPE = "Type";
    public static final String COLUMN_SERVICES = "Services";
    public static final String COLUMN_REGISTERED = "Registered";
    public static final String COLUMN_OVERWATCHSCORE = "OverwatchScore";
    public static final String COLUMN_CONDITION = "Condition";
    // Database creation sql statement
    private static final String SPRITE_DATABASE_CREATE = "create table "
            + TABLE_SPRITES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_RATING
            + " integer not null, " + COLUMN_TYPE
            + " integer not null, " + COLUMN_SERVICES
            + " integer not null, " + COLUMN_REGISTERED
            + " integer not null, " + COLUMN_OVERWATCHSCORE
            + " integer not null, " + COLUMN_CONDITION
            + " integer not null"
            + ");";
    private static final String DATABASE_NAME = "SpriteStable.db";
    private static final int DATABASE_VERSION = 5;


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(STAT_DATABASE_CREATE);
        database.execSQL(SPRITE_DATABASE_CREATE);
        ContentValues values = new ContentValues();
        ContentValues spritevalues = new ContentValues();

        values.put(Database.COLUMN_STAT, "Body");
        values.put(Database.COLUMN_VALUE, 1);
        database.insert(Database.TABLE_STATS, null,
                values);
        values.put(Database.COLUMN_STAT, "Intuition");
        values.put(Database.COLUMN_VALUE, 1);
        database.insert(Database.TABLE_STATS, null,
                values);
        values.put(Database.COLUMN_STAT, "Charisma");
        values.put(Database.COLUMN_VALUE, 1);
        database.insert(Database.TABLE_STATS, null,
                values);
        values.put(Database.COLUMN_STAT, "Logic");
        values.put(Database.COLUMN_VALUE, 1);
        database.insert(Database.TABLE_STATS, null,
                values);
        values.put(Database.COLUMN_STAT, "Willpower");
        values.put(Database.COLUMN_VALUE, 1);
        database.insert(Database.TABLE_STATS, null,
                values);
        values.put(Database.COLUMN_STAT, "Compiling");
        values.put(Database.COLUMN_VALUE, 1);
        database.insert(Database.TABLE_STATS, null,
                values);
        values.put(Database.COLUMN_STAT, "Registering");
        values.put(Database.COLUMN_VALUE, 1);
        database.insert(Database.TABLE_STATS, null,
                values);
        values.put(Database.COLUMN_STAT, "Resonance");
        values.put(Database.COLUMN_VALUE, 1);
        database.insert(Database.TABLE_STATS, null,
                values);
        values.put(Database.COLUMN_STAT, "Karma");
        values.put(Database.COLUMN_VALUE, 1);
        database.insert(Database.TABLE_STATS, null,
                values);
        values.put(Database.COLUMN_STAT, "Stun");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);
        values.put(Database.COLUMN_STAT, "Physical");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        spritevalues.put(Database.COLUMN_RATING, 1);
        spritevalues.put(Database.COLUMN_TYPE, 1);
        spritevalues.put(Database.COLUMN_SERVICES, 0);
        spritevalues.put(Database.COLUMN_REGISTERED, 0);
        spritevalues.put(Database.COLUMN_OVERWATCHSCORE, 0);
        spritevalues.put(Database.COLUMN_CONDITION, 0);
        database.insert(Database.TABLE_SPRITES, null,
                spritevalues);
        Log.i("CreateSprite: ", "V" + spritevalues.get(Database.COLUMN_RATING) + "S" + spritevalues.get(Database.COLUMN_SERVICES) + "T" + spritevalues.get(Database.COLUMN_TYPE) + "R" + spritevalues.get(Database.COLUMN_REGISTERED) + "O" + spritevalues.get(Database.COLUMN_OVERWATCHSCORE) + "C" + spritevalues.get(Database.COLUMN_CONDITION) + "I" + spritevalues.get(Database.COLUMN_ID));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Database.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPRITES);
        onCreate(db);
    }

}
