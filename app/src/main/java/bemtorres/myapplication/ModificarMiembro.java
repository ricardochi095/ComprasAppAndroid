package bemtorres.myapplication;

/**
 * Created by benja on 13/10/2017.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ModificarMiembro extends Activity implements OnClickListener {

    EditText et;
    Button btnActualizar, btnEliminar;

    long member_id;

    SQLControlador dbcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_miembro);

        dbcon = new SQLControlador(this);
        dbcon.abrirBaseDeDatos();

        et = (EditText) findViewById(R.id.et_miembro_id);
        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);

        Intent i = getIntent();
        String memberID = i.getStringExtra("miembroId");
        String memberName = i.getStringExtra("miembroNombre");

        member_id = Long.parseLong(memberID);

        et.setText(memberName);

        btnActualizar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnActualizar:
                String memName_upd = et.getText().toString();
                dbcon.actualizarDatos(member_id, memName_upd);
                this.returnHome();
                break;

            case R.id.btnEliminar:
                dbcon.deleteData(member_id);
                this.returnHome();
                break;
        }
    }

    public void returnHome() {

        Intent home_intent = new Intent(getApplicationContext(),
                MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(home_intent);
    }
}
