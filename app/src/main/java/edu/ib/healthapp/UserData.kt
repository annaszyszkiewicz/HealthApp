package edu.ib.healthapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object TableInfo: BaseColumns {
    const val TABLE_NAME = "Użytkownik"
    const val TABLE_COLUMN_NAME ="Imię"
    const val TABLE_COLUMN_SURNAME = "Nazwisko"
    const val TABLE_COLUMN_BIRTH = "Data urodzenia"
    const val TABLE_COLUMN_HEIGHT = "Wzrost [cm]"
}

object BasicCommand {
    const val SQL_CREATE_TABLE:String =
        "CREATE TABLE ${TableInfo.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${TableInfo.TABLE_COLUMN_NAME} ," +
                "${TableInfo.TABLE_COLUMN_SURNAME} ," +
                "${TableInfo.TABLE_COLUMN_BIRTH} ," +
                "${TableInfo.TABLE_COLUMN_HEIGHT} ,"

    const val SQL_DELATE_TABLE = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME}"
}

class DataBaseHelper(context: Context): SQLiteOpenHelper(context, TableInfo.TABLE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(BasicCommand.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(BasicCommand.SQL_DELATE_TABLE)
    }
}