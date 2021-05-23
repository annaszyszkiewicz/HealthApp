package edu.ib.healthapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object TableInfo : BaseColumns {
    const val TABLE_USER = "user"
    const val TABLE_COLUMN_NAME = "name"
    const val TABLE_COLUMN_SURNAME = "surname"
    const val TABLE_COLUMN_BIRTH = "birth"
    const val TABLE_COLUMN_HEIGHT = "height"

    const val TABLE_RESULT = "result"
    const val TABLE_COLUMN_USER = "user_id"
    const val TABLE_COLUMN_VALUE = "value"
    const val TABLE_COLUMN_DATE = "result_date"
    const val TABLE_COLUMN_TIME = "result_time"
}

object BasicCommand {
    const val CREATE_USER_TABLE: String =
        "CREATE TABLE IF NOT EXISTS ${TableInfo.TABLE_USER} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${TableInfo.TABLE_COLUMN_NAME} ," +
                "${TableInfo.TABLE_COLUMN_SURNAME} ," +
                "${TableInfo.TABLE_COLUMN_BIRTH} ," +
                "${TableInfo.TABLE_COLUMN_HEIGHT})"

    const val DELETE_USER_TABLE = "DROP TABLE IF EXISTS ${TableInfo.TABLE_USER}"

    const val CREATE_RESULT_TABLE: String =
        "CREATE TABLE IF NOT EXISTS ${TableInfo.TABLE_RESULT} (" +
                "${TableInfo.TABLE_COLUMN_USER} INTEGER, FOREIGN KEY (" +
                "${TableInfo.TABLE_COLUMN_USER}) REFERENCES ${TableInfo.TABLE_USER} (${BaseColumns._ID}) ," +
                "${TableInfo.TABLE_COLUMN_VALUE} ," +
                "${TableInfo.TABLE_COLUMN_DATE} ," +
                "${TableInfo.TABLE_COLUMN_TIME})"

//    const val DELETE_RESULT_TABLE = "DROP TABLE IF EXISTS ${TableInfo.TABLE_RESULT}"
}

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, TableInfo.TABLE_USER, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(BasicCommand.CREATE_USER_TABLE)
        db?.execSQL(BasicCommand.CREATE_RESULT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(BasicCommand.DELETE_USER_TABLE)
    }
}