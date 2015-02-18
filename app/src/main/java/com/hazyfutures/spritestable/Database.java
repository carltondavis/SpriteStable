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
    public static final String COLUMN_ID = "_id";

    public static final String TABLE_STATS = "Stats";
    public static final String COLUMN_STAT = "Stat";
    public static final String COLUMN_VALUE = "Value";
    private static final String STAT_DATABASE_CREATE = "create table "
            + TABLE_STATS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_STAT
            + " text not null, " + COLUMN_VALUE
            + " integer not null"
            + ");";
    public static final String TABLE_QUALITIES = "Qualities";
    public static final String COLUMN_QUALITYNAME = "QualityName";
    public static final String COLUMN_QUALITYVALUE = "QualityValue";
    private static final String QUALITY_DATABASE_CREATE = "create table "
            + TABLE_QUALITIES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_QUALITYNAME
            + " text not null, " + COLUMN_QUALITYVALUE
            + " integer not null"
            + ");";
    public static final String TABLE_SKILLS = "Skills";
    public static final String COLUMN_SKILLNAME = "SkillName";
    public static final String COLUMN_SKILLVALUE = "SkillValue";
    private static final String SKILL_DATABASE_CREATE = "create table "
            + TABLE_SKILLS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SKILLNAME
            + " text not null, " + COLUMN_SKILLVALUE
            + " integer not null"
            + ");";
    public static final String TABLE_SPECIALIZATIONS = "Specializations";
    public static final String COLUMN_SPECIALIZATIONNAME = "SpecializationName";
    public static final String COLUMN_LINKEDSKILL = "LinkedSkill";
    public static final String COLUMN_EXISTS = "HasSpecialization";
    private static final String SPECIALIZATION_DATABASE_CREATE = "create table "
            + TABLE_SPECIALIZATIONS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SPECIALIZATIONNAME
            + " text not null, " + COLUMN_LINKEDSKILL
            + " integer not null, " + COLUMN_EXISTS
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
    private static final int DATABASE_VERSION = 11;


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    public long AddSkill(SQLiteDatabase database, String SkillName){
        return  AddSkill(database, SkillName, 0);
    }
    public long AddSkill(SQLiteDatabase database, String SkillName, Integer value){
        ContentValues values = new ContentValues();
        values.put(Database.COLUMN_SKILLNAME, SkillName);
        values.put(Database.COLUMN_SKILLVALUE, value);
        return database.insert(Database.TABLE_SKILLS, null,
                values);
    }

    public long AddQuality(SQLiteDatabase database, String QualityName){
        return  AddQuality(database, QualityName, 0);
    }
    public long AddQuality(SQLiteDatabase database, String QualityName, Integer value){
        ContentValues values = new ContentValues();
        values.put(Database.COLUMN_QUALITYNAME, QualityName);
        values.put(Database.COLUMN_QUALITYVALUE, value);
        return database.insert(Database.TABLE_QUALITIES, null,
                values);
    }

    public long AddStat(SQLiteDatabase database, String StatName){
        return  AddStat(database, StatName, 1);
    }
    public long AddStat(SQLiteDatabase database, String StatName, Integer value){
        ContentValues values = new ContentValues();
        values.put(Database.COLUMN_STAT, StatName);
        values.put(Database.COLUMN_VALUE, value);
        return database.insert(Database.TABLE_STATS, null,
                values);
    }
    public void AddSpecialization(SQLiteDatabase database, Long LinkedSkill, String SpecializationName){
        ContentValues values = new ContentValues();
        values.put(Database.COLUMN_SPECIALIZATIONNAME, SpecializationName);
        values.put(Database.COLUMN_LINKEDSKILL, LinkedSkill);
        values.put(Database.COLUMN_EXISTS, 0);
        database.insert(Database.TABLE_SPECIALIZATIONS, null,
                values);
    }
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(STAT_DATABASE_CREATE);
        database.execSQL(QUALITY_DATABASE_CREATE);
        database.execSQL(SKILL_DATABASE_CREATE);
        database.execSQL(SPECIALIZATION_DATABASE_CREATE);
        database.execSQL(SPRITE_DATABASE_CREATE);
        ContentValues values = new ContentValues();
        ContentValues spritevalues = new ContentValues();
        long LinkedSkill=0;
        LinkedSkill = AddSkill(database, "Cybercombat");
        AddSpecialization(database, LinkedSkill, "Devices");
        AddSpecialization(database, LinkedSkill, "Grids");
        AddSpecialization(database, LinkedSkill, "IC");
        AddSpecialization(database, LinkedSkill,  "Personas");
        AddSpecialization(database, LinkedSkill,  "Sprites");
        LinkedSkill = AddSkill(database, "Electronic Warfare");
        AddSpecialization(database, LinkedSkill, "Communications");
        AddSpecialization(database, LinkedSkill, "Encryption");
        AddSpecialization(database, LinkedSkill, "Jamming");
        AddSpecialization(database, LinkedSkill,  "Sensor Operations");
        LinkedSkill = AddSkill(database, "Hacking");
        AddSpecialization(database, LinkedSkill, "Devices");
        AddSpecialization(database, LinkedSkill, "Files");
        AddSpecialization(database, LinkedSkill, "Hosts");
        AddSpecialization(database, LinkedSkill,  "Personas");
        LinkedSkill = AddSkill(database, "Computer");
        LinkedSkill = AddSkill(database,  "Hardware");
        LinkedSkill = AddSkill(database, "Software");
        AddSpecialization(database, LinkedSkill, "Data Bombs");
        AddSpecialization(database, LinkedSkill,  "Cleaner");
        AddSpecialization(database, LinkedSkill, "Editor");
        AddSpecialization(database, LinkedSkill, "Static Veil");
        AddSpecialization(database, LinkedSkill, "Pulse Storm");
        AddSpecialization(database, LinkedSkill, "Puppeteer");
        AddSpecialization(database, LinkedSkill, "Resonance Channel");
        AddSpecialization(database, LinkedSkill, "Resonance Spike");
        AddSpecialization(database, LinkedSkill, "Resonance Veil");
        AddSpecialization(database, LinkedSkill, "Static Bomb");
        AddSpecialization(database, LinkedSkill, "Stitches");
        AddSpecialization(database, LinkedSkill, "Transcendent Grid");
        AddSpecialization(database, LinkedSkill,  "Tattletale");
        AddSpecialization(database, LinkedSkill, "Diffusion of Attack");
        AddSpecialization(database, LinkedSkill, "Diffusion of Sleaze");
        AddSpecialization(database, LinkedSkill, "Diffusion of Data Processing");
        AddSpecialization(database, LinkedSkill, "Diffusion of Firewall");
        AddSpecialization(database, LinkedSkill, "Infusion of Attack");
        AddSpecialization(database, LinkedSkill, "Infusion of Sleaze");
        AddSpecialization(database, LinkedSkill, "Infusion of Data Processing");
        AddSpecialization(database, LinkedSkill, "Infusion of Firewall");
        LinkedSkill = AddSkill(database,  "Compiling");
        AddSpecialization(database, LinkedSkill, "Courier");
        AddSpecialization(database, LinkedSkill, "Crack");
        AddSpecialization(database, LinkedSkill, "Data");
        AddSpecialization(database, LinkedSkill,  "Fault");
        AddSpecialization(database, LinkedSkill,  "Machine");
        LinkedSkill = AddSkill(database, "Decompiling");
        AddSpecialization(database, LinkedSkill, "Courier");
        AddSpecialization(database, LinkedSkill, "Crack");
        AddSpecialization(database, LinkedSkill, "Data");
        AddSpecialization(database, LinkedSkill,  "Fault");
        AddSpecialization(database, LinkedSkill,  "Machine");
        LinkedSkill = AddSkill(database,  "Registering");
        AddSpecialization(database, LinkedSkill, "Courier");
        AddSpecialization(database, LinkedSkill, "Crack");
        AddSpecialization(database, LinkedSkill, "Data");
        AddSpecialization(database, LinkedSkill,  "Fault");
        AddSpecialization(database, LinkedSkill,  "Machine");

        AddStat(database, "Body");
        AddStat(database, "Intuition");
        AddStat(database, "Charisma");
        AddStat(database, "Logic");
        AddStat(database, "Willpower");
        AddStat(database, "Agility");
        AddStat(database, "Strength");
        AddStat(database, "Essence", 6);
        AddStat(database, "Magic", -1);
        AddStat(database,  "Reaction");
        AddStat(database,  "Resonance");
        AddStat(database,  "Karma");
        AddStat(database, "Stun",0);
        AddStat(database, "Physical",0);
        AddStat(database, "HoursThisSession",0);
        AddStat(database,  "SleeplessHours",0);
        AddStat(database, "ConsecutiveRest",0);
        AddStat(database, "HoursSinceKarmaRefresh",0);

        AddQuality(database,  "Codeslinger");
        AddQuality(database,  "Focused Concentration");
        AddQuality(database,  "Insomnia");
        AddQuality(database,  "High Pain Tolerance");
        AddQuality(database,  "Home Ground");
        AddQuality(database,  "Natural Hardening");
        AddQuality(database,  "Quick Healer");
        AddQuality(database,  "Toughness");
        AddQuality(database,  "Will to Live");
        AddQuality(database,  "Bad Luck");
        AddQuality(database,  "Codeblock");
        AddQuality(database,  "Loss of Confidence");
        AddQuality(database,  "Low Pain Tolerance");
        AddQuality(database,  "Sensitive System");
        AddQuality(database,  "Simsense Vertigo");
        AddQuality(database,  "Slow Healer");
        AddQuality(database,  "Perceptive");
        AddQuality(database,  "Spike Resistance");
        AddQuality(database,   "Tough as Nails Physical");
        AddQuality(database,  "Tough as Nails Stun");
        AddQuality(database,   "Asthma");
        AddQuality(database,  "AsthmaFatigueDamage");
        AddQuality(database,  "BiPolar");
        AddQuality(database,   "BiPolarCurrent");
        AddQuality(database,  "Blind");
        AddQuality(database,  "Computer Illiterate");
        AddQuality(database,   "Deaf");
        AddQuality(database,   "Dimmer Bulb");
        AddQuality(database,   "Illiterate");
        AddQuality(database,   "Oblivious");
        AddQuality(database,   "Pie Iesu Domine");
        spritevalues.put(Database.COLUMN_RATING, 1);
        spritevalues.put(Database.COLUMN_TYPE, 1);
        spritevalues.put(Database.COLUMN_SERVICES, 0);
        spritevalues.put(Database.COLUMN_REGISTERED, 0);
        spritevalues.put(Database.COLUMN_OVERWATCHSCORE, 0);
        spritevalues.put(Database.COLUMN_CONDITION, 0);
        database.insert(Database.TABLE_SPRITES, null,
                spritevalues);
      //  Log.i("CreateSprite: ", "V" + spritevalues.get(Database.COLUMN_RATING) + "S" + spritevalues.get(Database.COLUMN_SERVICES) + "T" + spritevalues.get(Database.COLUMN_TYPE) + "R" + spritevalues.get(Database.COLUMN_REGISTERED) + "O" + spritevalues.get(Database.COLUMN_OVERWATCHSCORE) + "C" + spritevalues.get(Database.COLUMN_CONDITION) + "I" + spritevalues.get(Database.COLUMN_ID));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Database.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUALITIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPRITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKILLS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPECIALIZATIONS);
        onCreate(db);
    }
    public void ResetDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUALITIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPRITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKILLS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPECIALIZATIONS);
        onCreate(db);
    };

}

