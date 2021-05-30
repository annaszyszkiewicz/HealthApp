package edu.ib.healthapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object TableInfo : BaseColumns {
    const val DATABASE = "health_app"

    const val TABLE_USER = "user"
    const val TABLE_USER_ID = BaseColumns._ID
    const val TABLE_USER_COLUMN_NAME = "name"
    const val TABLE_USER_COLUMN_SURNAME = "surname"
    const val TABLE_USER_COLUMN_BIRTH = "birth"
    const val TABLE_USER_COLUMN_HEIGHT = "height"

    const val TABLE_RESULT = "result"
    const val TABLE_RESULT_ID = BaseColumns._ID
    const val TABLE_RESULT_COLUMN_USER = "userid"
    const val TABLE_RESULT_COLUMN_TYPE = "measure"
    const val TABLE_RESULT_COLUMN_VALUE = "value"
    const val TABLE_RESULT_COLUMN_DATE = "resultdate"
    const val TABLE_RESULT_COLUMN_TIME = "resulttime"
}

object BasicCommand {
    const val CREATE_USER_TABLE: String =
        "CREATE TABLE IF NOT EXISTS ${TableInfo.TABLE_USER} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${TableInfo.TABLE_USER_COLUMN_NAME} TEXT, " +
                "${TableInfo.TABLE_USER_COLUMN_SURNAME} TEXT, " +
                "${TableInfo.TABLE_USER_COLUMN_BIRTH} TEXT, " +
                "${TableInfo.TABLE_USER_COLUMN_HEIGHT} NUMERIC)"

    const val DELETE_USER_TABLE = "DROP TABLE IF EXISTS ${TableInfo.TABLE_USER}"
    const val DELETE_RESULT_TABLE = "DROP TABLE IF EXISTS ${TableInfo.TABLE_RESULT}"

    const val CREATE_RESULT_TABLE: String =
        "CREATE TABLE IF NOT EXISTS ${TableInfo.TABLE_RESULT} (" +
		"${TableInfo.TABLE_RESULT_ID} INTEGER PRIMARY KEY, " +
                "${TableInfo.TABLE_RESULT_COLUMN_USER} INTEGER, " +
                "${TableInfo.TABLE_RESULT_COLUMN_TYPE} TEXT, "+
                "${TableInfo.TABLE_RESULT_COLUMN_VALUE} TEXT, " +
                "${TableInfo.TABLE_RESULT_COLUMN_DATE} TEXT, " +
                "${TableInfo.TABLE_RESULT_COLUMN_TIME} TEXT," +
                " FOREIGN KEY (${TableInfo.TABLE_RESULT_COLUMN_USER}) REFERENCES ${TableInfo.TABLE_USER}(${BaseColumns._ID}))"

//    const val DELETE_RESULT_TABLE = "DROP TABLE IF EXISTS ${TableInfo.TABLE_RESULT}"
}

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, TableInfo.DATABASE, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(BasicCommand.CREATE_USER_TABLE)
        db?.execSQL(BasicCommand.CREATE_RESULT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        //db?.execSQL(BasicCommand.DELETE_RESULT_TABLE)
        //db?.execSQL(BasicCommand.CREATE_RESULT_TABLE)
    }


}