package com.example.sn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/* Donald Hughes
 * Persistence - Assessment 3 * */
public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String PLAYER_TABLE = "PLAYER_TABLE";
    public static final String COLUMN_PLAYER_NAME = "PLAYER_NAME";
    public static final String COLUMN_PLAYER_SCORE = "PLAYER_SCORE";
    public static final String COLUMN_ACTIVE_PLAYER = "ACTIVE_PLAYER";
    public static final String COLUMN_SCORE_DATE = "SCORE_DATE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "player2.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PLAYER_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PLAYER_NAME + " TEXT, " + COLUMN_PLAYER_SCORE + " INT, " + COLUMN_SCORE_DATE + " TEXT, " + COLUMN_ACTIVE_PLAYER + " BOOL)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(PlayerModel playerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PLAYER_NAME, playerModel.getName());
        cv.put(COLUMN_PLAYER_SCORE, playerModel.getScore());
        cv.put(COLUMN_SCORE_DATE, playerModel.getDate());
        cv.put(COLUMN_ACTIVE_PLAYER, playerModel.isActive());

        long insert = db.insert(PLAYER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

        public List<PlayerModel> getPlayers() {

            List<PlayerModel> returnList = new ArrayList<>();
            String queryString = "SELECT * FROM " + PLAYER_TABLE + " ORDER BY id DESC LIMIT 5";

            SQLiteDatabase db = this.getReadableDatabase();

            final Cursor cursor = db.rawQuery(queryString, null);

            if(cursor.moveToFirst()){
                do {
                    int playerID = cursor.getInt(0);
                    String playerName = cursor.getString(1);
                    long playerScore = cursor.getInt(2);
                    String scoreDate = cursor.getString(3);
                    boolean playerActive = cursor.getInt(4) == 1 ? true: false;


                    PlayerModel newPlayer = new PlayerModel(playerID, playerName, playerScore, scoreDate, playerActive);
                    returnList.add(newPlayer);

                } while (cursor.moveToNext());

            } else {
                //failure. do not add anything
            }
            cursor.close();
            db.close();
            return returnList;
        };
};
