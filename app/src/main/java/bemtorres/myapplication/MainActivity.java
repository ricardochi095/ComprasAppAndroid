package bemtorres.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    Button btnAgregarMiembro;
    ListView lista;
    SQLControlador dbconeccion;
    TextView tv_miemID, tv_miemNombre, tv_prodCant, tv_prodPrecio, tv_prodTotal, tv_prodDesc, tv_prod_totalDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDeDatos();
        btnAgregarMiembro = (Button) findViewById(R.id.btnAgregarMiembro);
        lista = (ListView) findViewById(R.id.listViewMiembros);

        //acción del boton agregar miembro
        btnAgregarMiembro.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iagregar = new Intent(MainActivity.this, AgregarMiembro.class);
                startActivity(iagregar);
            }
        });

        // Tomar los datos desde la base de datos para poner en el curso y después en el adapter
        Cursor cursor = dbconeccion.leerDatos();

        String[] from = new String[] {
                DBhelper.MIEMBRO_ID,
                DBhelper.MIEMBRO_NOMBRE,
                DBhelper.PRODUCTO_CANT,
                DBhelper.PRODUCTO_PRECIO,
                DBhelper.PRODUCTO_TOTAL,
                DBhelper.PRODUCTO_DESC,
                DBhelper.PRODUCTO_TOTAL_DESC
        };
        int[] to = new int[] {
                R.id.miembro_id,
                R.id.miembro_nombre,
                R.id.prod_cant,
                R.id.prod_precio,
                R.id.prod_total,
                R.id.prod_desc,
                R.id.prod_total_desc

        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                MainActivity.this, R.layout.formato_fila, cursor, from, to);

        adapter.notifyDataSetChanged();
        lista.setAdapter(adapter);

        // acción cuando hacemos click en item para poder modificarlo o eliminarlo
        lista.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                tv_miemID = (TextView) view.findViewById(R.id.miembro_id);
                tv_miemNombre = (TextView) view.findViewById(R.id.miembro_nombre);
                tv_prodCant = (TextView) view.findViewById(R.id.prod_cant);
                tv_prodPrecio = (TextView) view.findViewById(R.id.prod_precio);
                tv_prodTotal = (TextView) view.findViewById(R.id.prod_total);
                tv_prodDesc = (TextView) view.findViewById(R.id.prod_desc);
                tv_prod_totalDesc = (TextView) view.findViewById(R.id.prod_total_desc);

                String aux_miembroId = tv_miemID.getText().toString();
                String aux_miembroNombre = tv_miemNombre.getText().toString();
                String aux_productoCant = tv_prodCant.getText().toString();
                String aux_productoPrecio = tv_prodCant.getText().toString();
                String aux_productoTotal = tv_prodTotal.getText().toString();
                String aux_productoDesc = tv_prodDesc.getText().toString();
                String aux_productoTotalDesc = tv_prod_totalDesc.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), ModificarMiembro.class);
                modify_intent.putExtra("miembroId", aux_miembroId);
                modify_intent.putExtra("miembroNombre", aux_miembroNombre);
                modify_intent.putExtra("prod_cant", aux_productoCant);
                modify_intent.putExtra("prod_precio", aux_productoPrecio);
                modify_intent.putExtra("prod_total", aux_productoTotal);
                modify_intent.putExtra("prod_desc", aux_productoDesc);
                modify_intent.putExtra("prod_total_desc", aux_productoTotalDesc);
                startActivity(modify_intent);
            }
        });
    }  //termina el onCreate
} //termina clase