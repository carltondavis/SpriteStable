package com.dasixes.spritestable;

/**
 * Created by cdavis on 1/17/2015.
 * Database handler for character statistics
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class StatsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private Database dbHelper;
    private String[] allStatColumns = {Database.COLUMN_ID,
            Database.COLUMN_STAT,
            Database.COLUMN_VALUE};
    private String[] allSkillColumns = {Database.COLUMN_ID,
            Database.COLUMN_SKILLNAME,
            Database.COLUMN_SKILLVALUE};
    private String[] allSpecializationColumns = {Database.COLUMN_ID,
            Database.COLUMN_SPECIALIZATIONNAME,
            Database.COLUMN_LINKEDSKILL,
            Database.COLUMN_EXISTS};

    private String[] allMatrixActionColumns = {Database.COLUMN_ID,
            Database.COLUMN_ACTIONNAME,
            Database.COLUMN_LINKEDSKILL,
            Database.COLUMN_LINKEDATTRIBUTE,
            Database.COLUMN_MARKSREQUIRED,
            Database.COLUMN_OPPOSEDATTRIBUTE,
            Database.COLUMN_OPPOSEDSKILL,
            Database.COLUMN_LIMITTYPE,
            Database.COLUMN_ACTIONTYPE};

    private String[] allQualityColumns = {Database.COLUMN_ID,
            Database.COLUMN_QUALITYNAME,
            Database.COLUMN_QUALITYVALUE};
    private String[] allSpriteColumns = {Database.COLUMN_ID,
            Database.COLUMN_OVERWATCHSCORE,
            Database.COLUMN_RATING,
            Database.COLUMN_REGISTERED,
            Database.COLUMN_CONDITION,
            Database.COLUMN_SERVICES,
            Database.COLUMN_TYPE};
    private String[] allComplexFormColumns = {Database.COLUMN_ID,
            Database.COLUMN_CF_NAME,
            Database.COLUMN_CF_TARGET,
            Database.COLUMN_CF_DURATION,
            Database.COLUMN_CF_FADING};

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

    public void updateQuality(String Quality, Integer Value) {
        ContentValues value = new ContentValues();
        value.put(Database.COLUMN_QUALITYVALUE, Value);
        int val = database.update(Database.TABLE_QUALITIES, value, Database.COLUMN_QUALITYNAME + " = '" + Quality + "'", null);
        // Log.i("SaveStat: ", attribute + ":" + Value + "Return:" + val);

    }

    public void updateSkill(String SkillName, Integer Value) {
        ContentValues value = new ContentValues();
        value.put(Database.COLUMN_SKILLVALUE, Value);
        int val = database.update(Database.TABLE_SKILLS, value, Database.COLUMN_SKILLNAME + " = '" + SkillName + "'", null);
        // Log.i("SaveStat: ", attribute + ":" + Value + "Return:" + val);
    }
    public void updateSpecialization(String SpecializationName, String SkillName, Integer exists) {
        String selectQuery = "SELECT " + Database.COLUMN_ID +" FROM "+ Database.TABLE_SKILLS +" WHERE "+ Database.COLUMN_SKILLNAME +"=?";
        Cursor c = database.rawQuery(selectQuery, new String[] { SkillName });
        if (c.moveToFirst()) {
            String skillID =c.getString(c.getColumnIndex(Database.COLUMN_ID));
            ContentValues value = new ContentValues();
            value.put(Database.COLUMN_EXISTS, exists);
            database.beginTransaction();
            try {
                long  val =  database.update(Database.TABLE_SPECIALIZATIONS, value, Database.COLUMN_LINKEDSKILL + " = '" + skillID + "' and " + Database.COLUMN_SPECIALIZATIONNAME + " = '" + SpecializationName + "'", null);
                if (val > 0) {
              //      Log.i("Specialization", "Update Spec: " + SpecializationName);
                    database.setTransactionSuccessful();
                }else{
                    Log.i("Specialization", "FAILED UPDATE/INSERT");
                }
            } finally {
                database.endTransaction();
            }
        }
        c.close();
        // Log.i("SaveSpec: ", SkillName + "/" + SpecializationName + ":" + exists + "Return:" + val);
    }

    public void deleteSprite(Sprite sprite){
        database.beginTransaction();
        try {
            int val = database.delete(Database.TABLE_SPRITES, Database.COLUMN_ID + "=?", new String[]{sprite.getId() + ""});
            if (val > 0) {
//                Log.i("Sprite", "Delete ID:" + sprite.getId());
                database.setTransactionSuccessful();
            }
        } finally {
            database.endTransaction();
        }
    }
    public long insertComplexForm(ComplexForm cf){
        ContentValues value = new ContentValues();
        long  val=0;

        value.put(Database.COLUMN_CF_NAME, cf.getName());
        value.put(Database.COLUMN_CF_TARGET, cf.getTarget());//Services
        value.put(Database.COLUMN_CF_DURATION, cf.getDuration());//SpriteType
        value.put(Database.COLUMN_CF_FADING,cf.getFading());
        database.beginTransaction();
        try {
            val = database.insert(Database.TABLE_COMPLEXFORMS, null,value);
            if(val>0){
                cf.setId(val);
                //        Log.i("Sprite", "Insert ID: " + val);
                database.setTransactionSuccessful();
            }else{
                //        Log.i("Sprite", "FAILED UPDATE/INSERT");
            }

        } finally {
            database.endTransaction();
        }
        return val;

    }

    public long insertSprite(Sprite sprite){
        ContentValues value = new ContentValues();
        long  val=0;

        value.put(Database.COLUMN_RATING, sprite.getRating());
        value.put(Database.COLUMN_SERVICES, sprite.getServicesOwed());//Services
        value.put(Database.COLUMN_TYPE, sprite.getSpriteType());//SpriteType
        value.put(Database.COLUMN_REGISTERED,sprite.getRegistered());
        value.put(Database.COLUMN_OVERWATCHSCORE, sprite.getGODScore());
        value.put(Database.COLUMN_CONDITION, sprite.getCondition());
        database.beginTransaction();
        try {
            val = database.insert(Database.TABLE_SPRITES, null,value);
                if(val>0){
                    sprite.setId(val);
            //        Log.i("Sprite", "Insert ID: " + val);
                    database.setTransactionSuccessful();
                }else{
            //        Log.i("Sprite", "FAILED UPDATE/INSERT");
                }

        } finally {
            database.endTransaction();
        }
        return val;

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
            long  val = database.update(Database.TABLE_SPRITES, value, Database.COLUMN_ID + "=?", new String[]{sprite.getId() + ""});
            if (val > 0) {
                //Log.i("Sprite", "Update ID: " + sprite.getId());
                database.setTransactionSuccessful();
            }else{
                Log.i("Sprite", "FAILED UPDATE/INSERT");
            }
        } finally {
            database.endTransaction();
        }
    }

    public List<Stats> getAllStats() {
        List<Stats> stats = new ArrayList<>();

        Cursor cursor = database.query(Database.TABLE_STATS,
                allStatColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Stats stat = cursorToStat(cursor);
            stats.add(stat);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return stats;
    }
    public List<Qualities> getAllQualities() {
        List<Qualities> qualities = new ArrayList<>();

        Cursor cursor = database.query(Database.TABLE_QUALITIES,
                allQualityColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Qualities quality = cursorToQuality(cursor);
            qualities.add(quality);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return qualities;
    }
    private Stats cursorToStat(Cursor cursor) {
        Stats stat = new Stats();
        stat.setId(cursor.getLong(0));
        stat.setStatName(cursor.getString(1));
        stat.setStatValue(cursor.getInt(2));
        return stat;
    }

    private Qualities cursorToQuality(Cursor cursor) {
        Qualities quality = new Qualities();
        quality.setId(cursor.getLong(0));
        quality.setQuality(cursor.getString(1));
        quality.setValue(cursor.getInt(2));
        return quality;
    }

    private Skills cursorToSkill(Cursor cursor) {
        Skills skill = new Skills();
        skill.setId(cursor.getLong(0));
        skill.setSkillName(cursor.getString(1));
        skill.setSkillValue(cursor.getInt(2));
        return skill;
    }

    private Specializations cursorToSpecialization(Cursor cursor) {
        Specializations specialization = new Specializations();
        specialization.setId(cursor.getLong(0));
        specialization.setSpecializationName(cursor.getString(1));
        specialization.setLinkedSkill(cursor.getInt(2));
        specialization.setExists(cursor.getInt(3) == 1);
        return specialization;
    }

    private MatrixActions cursorToMatrixAction(Cursor cursor) {
        MatrixActions matrixAction = new MatrixActions();
        matrixAction.setId(cursor.getLong(0));
        matrixAction.setActionName(cursor.getString(1));
        matrixAction.setLinkedSkill(cursor.getString(2));
        matrixAction.setLinkedAttribute(cursor.getString(3));
        matrixAction.setMarksRequired(cursor.getInt(4));
        matrixAction.setOpposedAttribute(cursor.getString(5));
        matrixAction.setOpposedSkill(cursor.getString(6));
        matrixAction.setLimitType(cursor.getInt(7));
        matrixAction.setActionType(cursor.getInt(8));
        return matrixAction;
    }



    public List<Skills> getAllSkills() {
        List<Skills> skills = new ArrayList<>();

        Cursor cursor = database.query(Database.TABLE_SKILLS,
                allSkillColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Skills skill = cursorToSkill(cursor);
            skills.add(skill);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return skills;
    }

    public List<Specializations> getAllSpecializations() {
        List<Specializations> specializations = new ArrayList<>();

        Cursor cursor = database.query(Database.TABLE_SPECIALIZATIONS,
                allSpecializationColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Specializations specialization = cursorToSpecialization(cursor);
            specializations.add(specialization);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return specializations;
    }

    public List<MatrixActions> getAllMatrixActions() {
        List<MatrixActions> matrixActions = new ArrayList<>();

        Cursor cursor = database.query(Database.TABLE_MATRIXACTIONS,
                allMatrixActionColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MatrixActions matrixAction = cursorToMatrixAction(cursor);
            matrixActions.add(matrixAction);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return matrixActions;
    }

    public List<ComplexForm> getAllComplexForms() {
        List<ComplexForm> complexForms = new ArrayList<>();

        Cursor cursor = database.query(Database.TABLE_COMPLEXFORMS,
                allComplexFormColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ComplexForm complexForm = cursorToComplexForm(cursor);
            complexForms.add(complexForm);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return complexForms;
    }
    private ComplexForm cursorToComplexForm(Cursor cursor) {
        ComplexForm cf= new ComplexForm();

        cf.setId(Integer.parseInt(cursor.getString(0)));//ID
        cf.setName(cursor.getString(1));
        cf.setTarget(cursor.getString(2));//Services
        cf.setDuration(cursor.getString(3));//SpriteType
        cf.setFading(cursor.getString(4));

        //Log.i("Sprite", "Loaded ID: " + sprite.getId());
        return cf;
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

        //Log.i("Sprite", "Loaded ID: " + sprite.getId());
        return sprite;
    }

    public List<Sprite> getAllSprites() {
        List<Sprite> sprites = new ArrayList<>();


        Cursor cursor = database.query(Database.TABLE_SPRITES, allSpriteColumns, null, null, null, null, null);

        cursor.moveToFirst();
        int i=0;
        while (!cursor.isAfterLast()) {
            i++;
            Sprite sprite = cursorToSprite(cursor);
            sprites.add(sprite);
            //sprites.set(sprites.size() - 1, sprite);
            //sprites.add(sprite);


            cursor.moveToNext();
        }
        //Log.i("Sprite", "Loaded " + i + " sprites");
        // make sure to close the cursor
        cursor.close();
        return sprites;
    }
    public void ResetDB(){
        dbHelper.ResetDB();
    }

}

