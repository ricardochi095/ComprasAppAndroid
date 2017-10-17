package bemtorres.myapplication;

/**
 * Created by benja on 13/10/2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLControlador {

    private DBhelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public SQLControlador(Context c) {
        ourcontext = c;
    }

    public SQLControlador abrirBaseDeDatos() throws SQLException {
        dbhelper = new DBhelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        dbhelper.close();
    }

    public boolean insertarDatos(String name, int cantidad, double precio, double total, int desc, double totalDesc) {

            ContentValues cv = new ContentValues();
            cv.put(DBhelper.MIEMBRO_NOMBRE, name);
            cv.put(DBhelper.PRODUCTO_CANT, cantidad);
            cv.put(DBhelper.PRODUCTO_PRECIO, precio);
            cv.put(DBhelper.PRODUCTO_TOTAL, total);
            cv.put(DBhelper.PRODUCTO_DESC, desc);
            cv.put(DBhelper.PRODUCTO_TOTAL_DESC, totalDesc);
            database.insert(DBhelper.TABLE_MEMBER, null, cv);
            return true;

    }

    public Cursor leerDatos() {
        String[] todasLasColumnas = new String[] {
                DBhelper.MIEMBRO_ID,
                DBhelper.MIEMBRO_NOMBRE,
                DBhelper.PRODUCTO_CANT,
                DBhelper.PRODUCTO_PRECIO,
                DBhelper.PRODUCTO_TOTAL,
                DBhelper.PRODUCTO_DESC,
                DBhelper.PRODUCTO_TOTAL_DESC
        };
        Cursor c = database.query(DBhelper.TABLE_MEMBER, todasLasColumnas, null,
                null, null, null,null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public int actualizarDatos(long memberID, String memberName, int cantidad, double precio, double total, int descuento, double totalDesc) {
        ContentValues cvActualizar = new ContentValues();
        cvActualizar.put(DBhelper.MIEMBRO_NOMBRE, memberName);
        cvActualizar.put(DBhelper.PRODUCTO_CANT, cantidad);
        cvActualizar.put(DBhelper.PRODUCTO_PRECIO, precio);
        cvActualizar.put(DBhelper.PRODUCTO_TOTAL, total);
        cvActualizar.put(DBhelper.PRODUCTO_DESC, descuento);
        cvActualizar.put(DBhelper.PRODUCTO_TOTAL_DESC, totalDesc);

        int i = database.update(DBhelper.TABLE_MEMBER, cvActualizar,
                DBhelper.MIEMBRO_ID + " = " + memberID, null);
        return i;
    }

    public void deleteData(long memberID) {
        database.delete(DBhelper.TABLE_MEMBER, DBhelper.MIEMBRO_ID + "="
                + memberID, null);
    }

    public double calcularTotal(){
        double resultado = 0;
       // Cursor c= database.query("");
        return 0.0;
    }
}