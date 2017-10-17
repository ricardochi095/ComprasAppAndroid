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

public class ModificarMiembro extends Activity implements OnClickListener {


    Button btnActualizar, btnEliminar, btnCalcular;
    EditText et, etCant, etPrecio, etDesc;
    TextView txtTotal, txtTotalDesc;
    boolean estado = false;
    int cant =0, desc=0;
    double precio=0.0, total =0.0, totalDes=0.0;
    LinearLayout llDesc;
    long member_id;

    SQLControlador dbcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_miembro);

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
        txtTotalDesc = (TextView) findViewById(R.id.txtTotalDesc);


        dbcon = new SQLControlador(this);
        dbcon.abrirBaseDeDatos();

        //Arreglar esto
        if (!etCant.getText().toString().equals("0")){
            simpleSwitch.setChecked(true);
            llDesc.setVisibility(View.VISIBLE);
        }

        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    llDesc.setVisibility(View.VISIBLE);
                    estado = true;
                }
                else{
                    llDesc.setVisibility(View.GONE);
                    estado = false;
                }
            }

        });

        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);

        Intent i = getIntent();
        String memberID = i.getStringExtra("miembroId");
        String memberName = i.getStringExtra("miembroNombre");
        String prodCant = i.getStringExtra("prod_cant");
        String prodPrecio = i.getStringExtra("prod_precio");
        String prodTotal = i.getStringExtra("prod_total");
        String prodDesc = i.getStringExtra("prod_desc");
        String prodTotalDesc = i.getStringExtra("prod_total_desc");

        member_id = Long.parseLong(memberID);

        et.setText(memberName);
        if(!prodCant.equals("") && !prodCant.equals("0") && !prodCant.equals("0.0")){etCant.setText(prodCant);}
        if(!prodPrecio.equals("") && !prodPrecio.equals("0") && !prodPrecio.equals("0.0")){etPrecio.setText(prodPrecio);}
        if(!prodTotal.equals("") && !prodTotal.equals("0") && !prodTotal.equals("0.0")){txtTotal.setText("Total $"+prodTotal);}
        if(!prodDesc.equals("") && !prodDesc.equals("0") && !prodDesc.equals("0.0")){
            etDesc.setText(prodDesc);
            txtTotalDesc.setText("Total $" +prodTotalDesc);
            simpleSwitch.setChecked(true);
            llDesc.setVisibility(View.VISIBLE);
            estado = true;
        }
        else{
            simpleSwitch.setChecked(false);
            llDesc.setVisibility(View.GONE);
            estado = false;
        }


        btnActualizar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
        btnCalcular.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnActualizar:
                String memName_upd = et.getText().toString();
                cant =0;desc=0;precio=0.0;total =0.0;totalDes=0.0;
                try{
                    //!etCant.getText().toString().equals("") quite esto
                    if(!etPrecio.getText().toString().equals("")){
                        cant = Integer.parseInt(etCant.getText().toString());
                        precio = Double.valueOf(etPrecio.getText().toString());
                        total = cant * precio;
                        if (estado){
                            if (!etDesc.getText().toString().equals("")){
                                desc = Integer.parseInt(etDesc.getText().toString());
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
                    Toast.makeText(getApplicationContext(), "Error al ingresar parametros", Toast.LENGTH_LONG).show();                }


                dbcon.actualizarDatos(member_id, memName_upd,cant,precio,total,desc,totalDes );
                this.returnHome();
                break;

            case R.id.btnEliminar:
                dbcon.deleteData(member_id);
                this.returnHome();
                break;
            case R.id.btnCalcular:
                String nombre = et.getText().toString();
                cant =0;desc=0;precio=0.0;total =0.0;totalDes=0.0;
                try{
                   // !etCant.getText().toString().equals("") &&
                    if(!etPrecio.getText().toString().equals("")){
                        cant = Integer.parseInt(etCant.getText().toString());
                        precio = Double.valueOf(etPrecio.getText().toString());
                        total = cant * precio;
                        if (estado){
                            if (!etDesc.getText().toString().equals("")){
                                desc = Integer.parseInt(etDesc.getText().toString());
                                totalDes = total - (total*(desc/100.0));
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Ingrese Parametros descuento", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Ingrese Parametros General", Toast.LENGTH_LONG).show();
                    }

                    txtTotal.setText("Total $"+total);
                    txtTotalDesc.setText("Total $"+totalDes);
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Error al ingresar parametros", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void returnHome() {

        Intent home_intent = new Intent(getApplicationContext(),
                MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
