package com.syzible.tearma.Deprecated.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.syzible.tearma.Deprecated.Objects.Definition;
import com.syzible.tearma.Deprecated.Objects.Domain;
import com.syzible.tearma.Deprecated.Objects.SearchLang;

import org.json.JSONArray;

/**
 * Created by ed on 01/12/2016
 */

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, Database.DB_NAME, null, Database.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createDefinitionsTable = "CREATE TABLE " + Database.Definitions.TABLE_NAME + "(" +
                Database.Definitions.ID + " INTEGER NOT NULL PRIMARY KEY, " +
                Database.Definitions.SEARCH_TERM + " VARCHAR(320), " +
                Database.Definitions.SEARCH_TYPE + " TEXT, " +
                Database.Definitions.LANGUAGE + " TEXT, " +
                Database.Definitions.SIGNPOST + " VARCHAR(320)" +
                ");";

        String createNounMutationsTable = "CREATE TABLE " + Database.NounMutations.TABLE_NAME + "(" +
                Database.NounMutations.ID + " INTEGER UNIQUE NOT NULL, " +
                Database.NounMutations.DECLENSION + " INTEGER, " +
                Database.NounMutations.GENDER + " TEXT, " +
                Database.NounMutations.ROOT + " VARCHAR(320), " +
                Database.NounMutations.GEN_SING + " VARCHAR(320), " +
                Database.NounMutations.NOM_PLU + " VARCHAR(320), " +
                Database.NounMutations.GEN_PLU + " VARCHAR(320), " +
                "FOREIGN KEY (" + Database.NounMutations.ID + ") REFERENCES " +
                Database.Definitions.TABLE_NAME + "(" + Database.Definitions.ID + ")" +
                ");";


        String createNounSearchMutationsTable = "CREATE TABLE " + Database.NounSearchMutations.TABLE_NAME + "(" +
                Database.NounSearchMutations.ID + " INTEGER UNIQUE NOT NULL, " +
                Database.NounSearchMutations.DECLENSION + " INTEGER, " +
                Database.NounSearchMutations.GENDER + " TEXT, " +
                Database.NounSearchMutations.ROOT + " VARCHAR(320), " +
                Database.NounSearchMutations.GEN_SING + " VARCHAR(320), " +
                Database.NounSearchMutations.NOM_PLU + " VARCHAR(320), " +
                Database.NounSearchMutations.GEN_PLU + " VARCHAR(320), " +
                "FOREIGN KEY (" + Database.NounSearchMutations.ID + ") REFERENCES " +
                Database.Definitions.TABLE_NAME + "(" + Database.Definitions.ID + ")" +
                ");";

        String createVerbMutationsTable = "CREATE TABLE " + Database.VerbMutations.TABLE_NAME + "(" +
                Database.VerbMutations.ID + " INTEGER UNIQUE NOT NULL, " +
                Database.VerbMutations.ROOT + " VARCHAR(320), " +
                Database.VerbMutations.GERUND + " VARCHAR(320), " +
                Database.VerbMutations.PARTICIPLE + " VARCHAR(320), " +
                "FOREIGN KEY (" + Database.VerbMutations.ID + ") REFERENCES " +
                Database.Definitions.TABLE_NAME + "( " + Database.Definitions.ID + ")" +
                ");";

        String createVerbSearchMutationsTable = "CREATE TABLE " + Database.VerbSearchMutations.TABLE_NAME + "(" +
                Database.VerbSearchMutations.ID + " INTEGER UNIQUE NOT NULL, " +
                Database.VerbSearchMutations.ROOT + " VARCHAR(320), " +
                Database.VerbSearchMutations.GERUND + " VARCHAR(320), " +
                Database.VerbSearchMutations.PARTICIPLE + " VARCHAR(320), " +
                "FOREIGN KEY (" + Database.VerbSearchMutations.ID + ") REFERENCES " +
                Database.Definitions.TABLE_NAME + "( " + Database.Definitions.ID + ")" +
                ");";

        String createDomainsTable = "CREATE TABLE " + Database.Domains.TABLE_NAME + "(" +
                Database.Domains.ID + " INTEGER UNIQUE NOT NULL, " +
                Database.Domains.EN_DOMAIN + " VARCHAR(320), " +
                Database.Domains.GA_DOMAIN + " VARCHAR(320), " +
                "FOREIGN KEY (" + Database.Domains.ID + ") REFERENCES " +
                Database.Definitions.TABLE_NAME + "( " + Database.Definitions.ID + ")" +
                ");";


        sqLiteDatabase.execSQL(createDefinitionsTable);
        sqLiteDatabase.execSQL(createNounMutationsTable);
        sqLiteDatabase.execSQL(createVerbMutationsTable);
        sqLiteDatabase.execSQL(createDomainsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // bad implementation - will change to a real upgrade later once tables are constrained
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Database.Definitions.TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Database.Domains.TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Database.NounMutations.TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Database.VerbMutations.TABLE_NAME + ";");

        onCreate(sqLiteDatabase);
    }

    public DbHelper printTable(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        int rowCount = cursor.getCount();
        int colCount = cursor.getColumnCount();

        if (rowCount > 0) {
            cursor.moveToFirst();
            System.out.println(tableName + ": " + rowCount + " rows, " + colCount + " cols");
            for (int r = 0; r < rowCount; r++) {
                for (int c = 0; c < colCount; c++)
                    System.out.println(c + ". " + cursor.getString(c) + ";");
                System.out.println("---------------------");
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return this;
    }

    public DbHelper purgeDB(Context context) {
        context.deleteDatabase(Database.DB_NAME + ".db");
        return this;
    }

    public DbHelper purge() {
        SQLiteDatabase db = this.getReadableDatabase();
        System.out.println("Purging tables");

        db.execSQL("DROP TABLE IF EXISTS " + Database.Definitions.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + Database.Domains.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + Database.NounMutations.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + Database.VerbMutations.TABLE_NAME + ";");

        onCreate(db);

        return this;
    }

    public DbHelper printAllTables() {
        String tables[] = {
                Database.Definitions.TABLE_NAME,
                Database.NounMutations.TABLE_NAME,
                Database.VerbMutations.TABLE_NAME,
                Database.Domains.TABLE_NAME
        };

        for (String table : tables) {
            printTable(table);
        }

        return this;
    }

    public DbHelper addDefinition(Definition definition, SearchLang searchLang) {
        ContentValues cvDefinitions = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();
        cvDefinitions.put(Database.Definitions.SEARCH_TERM, definition.getDetails().getSearchTerm());
        cvDefinitions.put(Database.Definitions.SEARCH_TYPE, definition.getDetails().getSearchType());
        cvDefinitions.put(Database.Definitions.LANGUAGE, searchLang.getSearchLang());
        cvDefinitions.put(Database.Definitions.SIGNPOST, definition.getDetails().getSignpost());
        db.insert(Database.Definitions.TABLE_NAME, null, cvDefinitions);
        db.close();

        SQLiteDatabase write_db = this.getWritableDatabase();
        SQLiteDatabase read_db = this.getReadableDatabase();

        String query = "SELECT MAX(" + Database.Definitions.ID + ") AS max_id FROM " + Database.Definitions.TABLE_NAME + ";";
        Cursor cursor = read_db.rawQuery(query, null);
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex("max_id"));

        printTable(Database.Definitions.TABLE_NAME);
        System.out.println("Max id: " + id);
        cursor.close();

        switch (definition.getDetails().getSearchType()) {
            case "noun":
                storeNoun(definition, id);
                break;
            case "verb":
                storeVerb(definition, id);
                break;
            default:
                storeOther(definition, id);
                break;
        }

        //storeDomains(definition);

        read_db.close();
        write_db.close();

        return this;
    }

    private void storeNoun(Definition definition, int id) {
        SQLiteDatabase write_db = this.getWritableDatabase();
        ContentValues nounCv = new ContentValues();

        write_db.close();
    }

    private void storeVerb(Definition definition, int id) {

    }

    private void storeOther(Definition definition, int id) {

    }

    public DbHelper storeDomains(Definition definition) {
        SQLiteDatabase writeDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        int id = 0;

        for (Domain domain : definition.getDomains().getDomains()) {
            cv.clear();
            cv.put(Database.Domains.ID, id);
            cv.put(Database.Domains.EN_DOMAIN, domain.getEnDomain());
            cv.put(Database.Domains.GA_DOMAIN, domain.getGaDomain());
            writeDb.insert(Database.Domains.TABLE_NAME, null, cv);
        }

        writeDb.close();

        return this;
    }

    public DbHelper removeDefinition(int id) {
        return this;
    }

    public static JSONArray getRecords() {
        return null;
    }
}
