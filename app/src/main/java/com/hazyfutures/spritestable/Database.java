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
    private static final int DATABASE_VERSION = 9;


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
        values.put(Database.COLUMN_STAT, "Agility");
        values.put(Database.COLUMN_VALUE, 1);
        database.insert(Database.TABLE_STATS, null,
                values);
        values.put(Database.COLUMN_STAT, "Strength");
        values.put(Database.COLUMN_VALUE, 1);
        database.insert(Database.TABLE_STATS, null,
                values);
        values.put(Database.COLUMN_STAT, "Essence");
        values.put(Database.COLUMN_VALUE, 6);
        database.insert(Database.TABLE_STATS, null,
                values);
        values.put(Database.COLUMN_STAT, "Magic");
        values.put(Database.COLUMN_VALUE, -1);
        database.insert(Database.TABLE_STATS, null,
                values);
        values.put(Database.COLUMN_STAT, "Reaction");
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

        values.put(Database.COLUMN_STAT, "HoursThisSession");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "SleeplessHours");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "ConsecutiveRest");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "HoursSinceKarmaRefresh");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "CodeSlinger");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "FocusedConcentration");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "Insomnia");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "HighPainTolerance");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "HomeGround");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "NaturalHardening");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "QuickHealer");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "Toughness");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "WillToLive");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "BadLuck");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "CodeBlock");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "LossOfConfidence");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "LowPainTolerance");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "SensitiveSystem");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "SimsenseVertigo");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "SlowHealer");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "Perceptive");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "SpikeResistance");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "ToughAsNailsPhysical");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "ToughAsNailsStun");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "Asthma");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "AsthmaFatigueDamage");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "BiPolar");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "BiPolarCurrent");
        values.put(Database.COLUMN_VALUE, 6);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "Blind");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "ComputerIlliterate");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "Deaf");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "DimmerBulb");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "Illiterate");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "Oblivious");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

        values.put(Database.COLUMN_STAT, "PieIesuDomine");
        values.put(Database.COLUMN_VALUE, 0);
        database.insert(Database.TABLE_STATS, null,
                values);

//TODO:Insert Qualities default values

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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKILLS);
        onCreate(db);
    }

}

