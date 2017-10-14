package bemtorres.myapplication;

/**
 * Created by benja on 13/10/2017.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    // Información de la tabla
    public static final String TABLE_MEMBER = "productos";
    public static final String MIEMBRO_ID = "_id";
    public static final String MIEMBRO_NOMBRE = "nombre";
    public static final String PRODUCTO_CANT = "0";
    public static final String PRODUCTO_PRECIO = "0";
    public static final String PRODUCTO_TOTAL= "0";
    public static final String PRODUCTO_DESC = "0";
    public static final String PRODUCTO_TOTAL_DESC = "0";


    // información del a base de datos
    static final String DB_NAME = "DBPRODUCTO";
    static final int DB_VERSION = 1;

    // Información de la base de datos
    private static final String CREATE_TABLE = "create table "
            + TABLE_MEMBER + "(" + MIEMBRO_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MIEMBRO_NOMBRE + " TEXT NOT NULL,"
            + PRODUCTO_CANT+ " TEXT ,"
            + PRODUCTO_PRECIO+" TEXT ,"
            + PRODUCTO_TOTAL + " TEXT ,"
            + PRODUCTO_DESC + " TEXT ,"
            + PRODUCTO_TOTAL_DESC+" TEXT );";

    public DBhelper(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER);
        onCreate(db);
    }
}
