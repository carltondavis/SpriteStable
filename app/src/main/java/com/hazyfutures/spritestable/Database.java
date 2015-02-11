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
    public static final String COLUMN_SPECIALIZATIONOF = "SpecializationID";
    private static final String SKILL_DATABASE_CREATE = "create table "
            + TABLE_SKILLS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SKILLNAME
            + " text not null, " + COLUMN_SKILLVALUE
            + " integer not null, "+ COLUMN_SPECIALIZATIONOF
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
    private static final int DATABASE_VERSION = 8;


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(STAT_DATABASE_CREATE);
        database.execSQL(SKILL_DATABASE_CREATE);
        database.execSQL(SPRITE_DATABASE_CREATE);
        ContentValues values = new ContentValues();
        ContentValues spritevalues = new ContentValues();

        values.put(Database.COLUMN_SKILLNAME, "Cybercombat");
        values.put(Database.COLUMN_SKILLVALUE, 0);
        values.put(Database.COLUMN_SPECIALIZATIONOF, -1);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Electronic Warfare");
        values.put(Database.COLUMN_SKILLVALUE, 0);
        values.put(Database.COLUMN_SPECIALIZATIONOF, -1);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Hacking");
        values.put(Database.COLUMN_SKILLVALUE, 0);
        values.put(Database.COLUMN_SPECIALIZATIONOF, -1);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Computer");
        values.put(Database.COLUMN_SKILLVALUE, 0);
        values.put(Database.COLUMN_SPECIALIZATIONOF, -1);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Hardware");
        values.put(Database.COLUMN_SKILLVALUE, 0);
        values.put(Database.COLUMN_SPECIALIZATIONOF, -1);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Software");
        values.put(Database.COLUMN_SKILLVALUE, 0);
        values.put(Database.COLUMN_SPECIALIZATIONOF, -1);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Compiling");
        values.put(Database.COLUMN_SKILLVALUE, 0);
        values.put(Database.COLUMN_SPECIALIZATIONOF, -1);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Decompiling");
        values.put(Database.COLUMN_SKILLVALUE, 0);
        values.put(Database.COLUMN_SPECIALIZATIONOF, -1);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Registering");
        values.put(Database.COLUMN_SKILLVALUE, 0);
        values.put(Database.COLUMN_SPECIALIZATIONOF, -1);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Courier");
        values.put(Database.COLUMN_SKILLVALUE, 1);
        values.put(Database.COLUMN_SPECIALIZATIONOF, 7);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Crack");
        values.put(Database.COLUMN_SKILLVALUE, 1);
        values.put(Database.COLUMN_SPECIALIZATIONOF, 7);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Data");
        values.put(Database.COLUMN_SKILLVALUE, 1);
        values.put(Database.COLUMN_SPECIALIZATIONOF, 7);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Fault");
        values.put(Database.COLUMN_SKILLVALUE, 1);
        values.put(Database.COLUMN_SPECIALIZATIONOF, 7);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Machine");
        values.put(Database.COLUMN_SKILLVALUE, 1);
        values.put(Database.COLUMN_SPECIALIZATIONOF, 7);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Courier");
        values.put(Database.COLUMN_SKILLVALUE, 1);
        values.put(Database.COLUMN_SPECIALIZATIONOF, 8);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Crack");
        values.put(Database.COLUMN_SKILLVALUE, 1);
        values.put(Database.COLUMN_SPECIALIZATIONOF, 8);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Data");
        values.put(Database.COLUMN_SKILLVALUE, 1);
        values.put(Database.COLUMN_SPECIALIZATIONOF, 8);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Fault");
        values.put(Database.COLUMN_SKILLVALUE, 1);
        values.put(Database.COLUMN_SPECIALIZATIONOF, 8);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Machine");
        values.put(Database.COLUMN_SKILLVALUE, 1);
        values.put(Database.COLUMN_SPECIALIZATIONOF, 8);
        database.insert(Database.TABLE_SKILLS, null,
                values);
        values.put(Database.COLUMN_SKILLNAME, "Courier");
        values.put(Database.COLUMN_SKILLVALUE, 1);
        values.put(Database.COLUMN_SPECIALIZATIONOF, 9);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Crack");
        values.put(Database.COLUMN_SKILLVALUE, 1);
        values.put(Database.COLUMN_SPECIALIZATIONOF, 9);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Data");
        values.put(Database.COLUMN_SKILLVALUE, 1);
        values.put(Database.COLUMN_SPECIALIZATIONOF, 9);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Fault");
        values.put(Database.COLUMN_SKILLVALUE, 1);
        values.put(Database.COLUMN_SPECIALIZATIONOF, 9);
        database.insert(Database.TABLE_SKILLS, null,
                values);

        values.put(Database.COLUMN_SKILLNAME, "Machine");
        values.put(Database.COLUMN_SKILLVALUE, 1);
        values.put(Database.COLUMN_SPECIALIZATIONOF, 9);
        database.insert(Database.TABLE_SKILLS, null,
                values);

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

