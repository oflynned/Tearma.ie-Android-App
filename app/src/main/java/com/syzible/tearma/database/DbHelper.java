package com.syzible.tearma.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.syzible.tearma.objects.Definition;

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
                Database.Definitions.ID + " INTEGER PRIMARY KEY, " +
                Database.Definitions.TERM + " VARCHAR(320), " +
                Database.Definitions.TYPE + " TEXT, " +
                Database.Definitions.SEARCH_MUTATIONS + " VARCHAR(320), " +
                Database.Definitions.LANGUAGE + " TEXT, " +
                Database.Definitions.SIGNPOST + " VARCHAR(320), " +
                Database.Definitions.MUTATIONS + " VARCHAR(320), " +
                Database.Definitions.DOMAINS + " VARCHAR(320)"
                + ");";

        String createNounMutationsTable = "CREATE TABLE " + Database.NounMutations.TABLE_NAME + "(" +
                Database.NounMutations.ID + " INTEGER FOREIGN KEY, " +
                Database.NounMutations.DECLENSION + " INTEGER, " +
                Database.NounMutations.GENDER + " TEXT, " +
                Database.NounMutations.ROOT + " VARCHAR(320), " +
                Database.NounMutations.GEN_SING + " VARCHAR(320), " +
                Database.NounMutations.NOM_PLU + " VARCHAR(320), " +
                Database.NounMutations.GEN_PLU + " VARCHAR(320)"
                + ");";

        String createVerbMutationsTable = "CREATE TABLE " + Database.VerbMutations.TABLE_NAME + "(" +
                Database.VerbMutations.ID + " INTEGER FOREIGN KEY, " +
                Database.VerbMutations.ROOT + " VARCHAR(320), " +
                Database.VerbMutations.GERUND + " VARCHAR(320), " +
                Database.VerbMutations.PARTICIPLE + " VARCHAR(320)"
                + ");";

        String createDomainsTable = "CREATE TABLE " + Database.Domains.TABLE_NAME + "(" +
                Database.Domains.ID + " INTEGER FOREIGN KEY, " +
                Database.Domains.LANG + " VARCHAR(320), " +
                Database.Domains.DOMAIN + " VARCHAR(320)"
                + ");";


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


    public void addDefinition(Definition definition) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cvDefinitions = new ContentValues();
        ContentValues cvNoun = new ContentValues();
        ContentValues cvVerb = new ContentValues();
        ContentValues cvDomains = new ContentValues();

        switch (definition.getDetails().getSearchType()) {
            case "noun":
                break;
            case "verb":
                break;
            default:
                break;
        }

        db.close();
    }

    void printTable(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        int rowCount = cursor.getCount();
        int colCount = cursor.getColumnCount();

        if(rowCount > 0) {
            cursor.moveToFirst();
            System.out.println(tableName + ": " + rowCount + " rows, " + colCount + " cols");
            for(int r=0; r<rowCount;r++) {
                for (int c = 0; c < colCount; c++)
                    System.out.println(c + ". " + cursor.getString(c) + ";");
                System.out.println("---------------------");
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();
    }

    void printAllTables() {
        String tables[] = {
                Database.Definitions.TABLE_NAME,
                Database.NounMutations.TABLE_NAME,
                Database.VerbMutations.TABLE_NAME,
                Database.Domains.TABLE_NAME
        };

        for(String table : tables) {
            printTable(table);
        }
    }

    private void insertNoun(ContentValues cv, Definition definition) {

    }

    private void insertVerb(ContentValues cv, Definition definition) {

    }

    private void insertOther(ContentValues cv, Definition definition) {

    }

    public static void removeDefinition(int pos) {
    }

    public static JSONArray getRecords() {
        return null;
    }
}
