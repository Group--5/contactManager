package otto.contacts.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by study on 10/18/14.
 */
public class LiteHelper extends SQLiteOpenHelper{

    // This needs to be upgraded every time our database changes.
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "color_scheme";
    private static final String TABLE_BUTTON_HEX = "btn_hex";
    private static final String KEY_ID = "id";
    private static final String BUTTON_HEX = "btn_color";
    private static final String[] COLUMNS = {KEY_ID, TABLE_BUTTON_HEX};

    public LiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void saveButtonColor(String s)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BUTTON_HEX, s);
        db.insert(TABLE_BUTTON_HEX,null,values);
        db.close();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

//        private static final int DATABASE_VERSION = 6;
//        private static final String DATABASE_NAME = "color_scheme";
//        private static final String TABLE_BUTTON_HEX = "btn_hex";
//        private static final String KEY_ID = "id";


        String CREATE_CODE_TABLE = "CREATE TABLE btn_hex ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "btn_color TEXT, " +
                 ")";

        sqLiteDatabase.execSQL(CREATE_CODE_TABLE);

    }



//    public void deleteDtcErrorObject(DtcErrorObject obj) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CODES, //table name
//                KEY_ID + " = ?",  // selections
//                new String[]{String.valueOf(obj.getId())}); //selections args
//        db.close();
//        Log.d("deleteDTCerrorObject", obj.toString());
//    }
//
//    public void deleteSession(SessionObject obj) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_SESSION,
//                SESSION_KEY_ID + " = ?",
//                new String[]{String.valueOf(obj.getId())});
//        db.close();
//        Log.d("deleteSessionErrorObject", obj.toString());
//    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS codes");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS sessions");
        this.onCreate(sqLiteDatabase);
    }
}