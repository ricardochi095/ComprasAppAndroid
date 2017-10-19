package bemtorres.myapplication;

/**
 * Created by benja on 13/10/2017.
 */
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class AgregarMiembro extends Activity implements OnClickListener {
    EditText et, etCant, etPrecio, etDesc;
    TextView txtTotal, txtTotalDesc, txtAhorro;
    LinearLayout llDesc;
    Button btnAgregar, read_bt, btnCalcular;
    boolean estado = false;
    int cant =0, desc=0;
    double precio=0.0, total =0.0, totalDes=0.0;
    SQLControlador dbconeccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_miembro);

        Switch simpleSwitch = (Switch) findViewById(R.id.switch1);
        simpleSwitch.setTextOn("Si");
        simpleSwitch.setTextOff("No");
        simpleSwitch.setTextColor(Color.RED);
        llDesc = (LinearLayout) findViewById(R.id.LL_descuento);

        et = (EditText) findViewById(R.id.et_miembro_id);
        etCant = (EditText) findViewById(R.id.et_Cant);
        etPrecio = (EditText) findViewById(R.id.et_Precio);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        etDesc = (EditText) findViewById(R.id.et_Desc);
        txtAhorro = (TextView) findViewById(R.id.txtAhorras);
        txtTotalDesc = (TextView) findViewById(R.id.txtTotalDesc);

        btnAgregar = (Button) findViewById(R.id.btnAgregarId);
        btnCalcular = (Button)findViewById(R.id.btnCalcular);

        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    llDesc.setVisibility(View.VISIBLE);
                    estado = true;
                    txtAhorro.setVisibility(View.VISIBLE);
            }
                else{
                    llDesc.setVisibility(View.GONE);
                    estado = false;
                    txtAhorro.setVisibility(View.GONE);
                }
            }

        });
        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDeDatos();
        btnAgregar.setOnClickListener(this);
        btnCalcular.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnAgregarId:
                String name = et.getText().toString();
                cant =0;desc=0;precio=0;total =0;totalDes=0;
                Calcular();
                //Agregar a la base de datos
                if (dbconeccion.insertarDatos(name,cant,precio,total,desc,totalDes)){
                    // Toast.makeText(getApplicationContext(), "Agregado a la BD", Toast.LENGTH_LONG).show();
                }
                else{
                Toast.makeText(getApplicationContext(), "Error Insertar Base de datos", Toast.LENGTH_LONG).show();
                }
                Intent main = new Intent(AgregarMiembro.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
                break;
            case R.id.btnCalcular:
                String nombre = et.getText().toString();
                Calcular();
                break;
        }
    }

    public void Calcular(){
        cant =0;desc=0;precio=0.0;total =0.0;totalDes=0.0;
        try{
            if(!etCant.getText().toString().equals("")){
                cant = Integer.parseInt(etCant.getText().toString());
            }
            if(!etPrecio.getText().toString().equals("")){
                precio = Double.valueOf(etPrecio.getText().toString()); //obtiene precio
                total = cant * precio; //Calcula Total de la compra
                if (estado){
                    if (!etDesc.getText().toString().equals("")){
                        desc = Integer.parseInt(etDesc.getText().toString());
                        txtAhorro.setText("Ahorras $"+(total * (desc/100.0)));
                        totalDes = total - (total*(desc/100.0));
                    }
                    else{
                       // Toast.makeText(getApplicationContext(), "Ingrese Parametros descuento", Toast.LENGTH_LONG).show();
                    }
                }
            }
            else{
               // Toast.makeText(getApplicationContext(), "Ingrese Parametros General", Toast.LENGTH_LONG).show();
            }

            txtTotal.setText("Total $"+total);
            txtTotalDesc.setText("Total $"+totalDes);
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error al ingresar parametros", Toast.LENGTH_LONG).show();
        }
    }
}