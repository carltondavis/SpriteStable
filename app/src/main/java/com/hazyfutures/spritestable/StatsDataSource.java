package com.hazyfutures.spritestable;

/**
 * Created by cdavis on 1/17/2015.
 * Database handler for character statistics
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class StatsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private Database dbHelper;
    private String[] allStatColumns = {Database.COLUMN_ID,
            Database.COLUMN_STAT,
            Database.COLUMN_VALUE};
    private String[] allSpriteColumns = {Database.COLUMN_ID,
            Database.COLUMN_OVERWATCHSCORE,
            Database.COLUMN_RATING,
            Database.COLUMN_REGISTERED,
            Database.COLUMN_CONDITION,
            Database.COLUMN_SERVICES,
            Database.COLUMN_TYPE};

    public StatsDataSource(Context context) {
        dbHelper = new Database(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /*   public Stat createStat(String Stat, Integer Value) {
           ContentValues values = new ContentValues();
           values.put(Database.COLUMN_STAT, Stat);
           values.put(Database.COLUMN_VALUE, Value);
           long insertId = database.insert(Database.TABLE_STATS, null,
                   values);
           Cursor cursor = database.query(Database.TABLE_STATS,
                   allColumns, Database.COLUMN_ID + " = " + insertId, null,
                   null, null, null);
           cursor.moveToFirst();
           Stat newStat = cursorToStat(cursor);
           cursor.close();
           return newStat;
       }
       public void deleteStat(Stat stat) {
           long id = stat.getId();
           System.out.println("Stat deleted with id: " + id);
           database.delete(Database.TABLE_STATS, Database.COLUMN_ID
                   + " = " + id, null);
       }

   */
    public void updateStat(String attribute, Integer Value) {
        ContentValues value = new ContentValues();
        value.put(Database.COLUMN_VALUE, Value);
        int val = database.update(Database.TABLE_STATS, value, Database.COLUMN_STAT + " = '" + attribute + "'", null);
       // Log.i("SaveStat: ", attribute + ":" + Value + "Return:" + val);

    }

    public void deleteSprite(Sprite sprite){
        database.beginTransaction();
        try {
            int val = database.delete(Database.TABLE_SPRITES, Database.COLUMN_ID + "=?", new String[]{sprite.getId() + ""});
            if (val > 0) {
                database.setTransactionSuccessful();
            }
        } finally {
            database.endTransaction();
        }
    }

    public void updateSprite(Sprite sprite) {
        ContentValues value = new ContentValues();

        value.put(Database.COLUMN_RATING, sprite.getRating());
        value.put(Database.COLUMN_SERVICES, sprite.getServicesOwed());//Services
        value.put(Database.COLUMN_TYPE, sprite.getSpriteType());//SpriteType
        value.put(Database.COLUMN_REGISTERED,sprite.getRegistered());
        value.put(Database.COLUMN_OVERWATCHSCORE, sprite.getGODScore());
        value.put(Database.COLUMN_CONDITION, sprite.getCondition());
        database.beginTransaction();
        try {
            int val = database.update(Database.TABLE_SPRITES, value, Database.COLUMN_ID + "=?", new String[]{sprite.getId() + ""});
            if (val > 0) {
                //Log.i("SaveSprite: ", "V" + sprite.getRating() + "S" + sprite.getServicesOwed() + "T" + sprite.getSpriteType() + "R" + sprite.getRegistered() + "O" + sprite.getGODScore() + "C" + sprite.getCondition() + "I" + sprite.getId());
                database.setTransactionSuccessful();
            }else{
                if(database.insert(Database.TABLE_SPRITES, null,value)!=-1){
                  //  Log.i("SaveSprite: ", "V" + sprite.getRating() + "S" + sprite.getServicesOwed() + "T" + sprite.getSpriteType() + "R" + sprite.getRegistered() + "O" + sprite.getGODScore() + "C" + sprite.getCondition() + "I" + sprite.getId());
                    database.setTransactionSuccessful();
                }
            }
        } finally {
            database.endTransaction();
        }
    }

    public List<Stat> getAllStats() {
        List<Stat> stats = new ArrayList<>();

        Cursor cursor = database.query(Database.TABLE_STATS,
                allStatColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Stat stat = cursorToStat(cursor);
            stats.add(stat);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return stats;
    }

    private Stat cursorToStat(Cursor cursor) {
        Stat stat = new Stat();
        stat.setId(cursor.getLong(0));
        stat.setStat(cursor.getString(1));
        stat.setValue(cursor.getInt(2));
        return stat;
    }

    private Sprite cursorToSprite(Cursor cursor) {
        Sprite sprite = new Sprite();

        sprite.setRating(Integer.parseInt(cursor.getString(2)));
        sprite.setServicesOwed(Integer.parseInt(cursor.getString(5)));//Services
        sprite.setId(Integer.parseInt(cursor.getString(0)));//ID
        sprite.setSpriteType(Integer.parseInt(cursor.getString(6)));//SpriteType
        sprite.setRegistered(Integer.parseInt(cursor.getString(3)));
        sprite.setGODScore(Integer.parseInt(cursor.getString(1)));
        sprite.setCondition(Integer.parseInt(cursor.getString(4)));

       // Log.i("LoadSprite: ", "V" + sprite.getRating() + "S" + sprite.getServicesOwed() + "T" + sprite.getSpriteType() + "R" + sprite.getRegistered() + "O" + sprite.getGODScore() + "C" + sprite.getCondition() + "I" + sprite.getId());
        return sprite;
    }

    public List<Sprite> getAllSprites() {
        List<Sprite> sprites = new ArrayList<>();


        Cursor cursor = database.query(Database.TABLE_SPRITES, allSpriteColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Sprite sprite = cursorToSprite(cursor);
            sprites.add(sprite);
            sprites.set(sprites.size() - 1, sprite);
            //sprites.add(sprite);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return sprites;
    }

}

