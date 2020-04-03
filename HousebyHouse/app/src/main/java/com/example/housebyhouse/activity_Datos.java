package com.example.housebyhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class activity_Datos extends AppCompatActivity {
    TextView cantidadUnidades,cantidadDisponibles,cantidadArrendadas,mejorCliente,tipoComun,prom;
    Button btn;
    private int dia,mes,ano;
    AdapterDatos adapterDatos;
    OperacionesBd bd;

    String fechastring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__datos);
        Conectar();
        bd=new OperacionesBd(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        cantidadUnidades.setText(bd.NumeroDeViviendas()+"");
        cantidadDisponibles.setText(bd.NumeroDeViviendasSinArrendar()+"");
        cantidadArrendadas.setText(bd.NumeroDeViviendasArrendadas()+"");
        mejorCliente.setText(bd.MejorCliente());
        prom.setText(bd.promedioArriendos()+"");
        tipoComun.setText(bd.tipoComun());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=GregorianCalendar.getInstance();
                dia=calendar.get(Calendar.DAY_OF_MONTH);
                mes=calendar.get(Calendar.MONTH);
                ano=calendar.get(Calendar.YEAR);
                final DatePickerDialog dialog=new DatePickerDialog(activity_Datos.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        SimpleDateFormat dateFormat = new SimpleDateFormat(
                                "yyyy-MM-dd", Locale.getDefault());
                         fechastring=year+"-"+month+"-"+dayOfMonth;
                        try {
                            Date date1=dateFormat.parse(fechastring);
                            fechastring =dateFormat.format(date1);
                            dialogBuscarViviendas();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },ano,mes,dia);
                dialog.show();
            }
        });
    }
    private void dialogBuscarViviendas(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity_Datos.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_dia_especifico, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        RecyclerView listView=view.findViewById(R.id.lv);
        listView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        bd=new OperacionesBd(getApplicationContext());
        ArrayList<UnidadHabitacional> arrayList = bd.BuscarViviendas(fechastring);
        if(arrayList.size()!=0) {
            dialog.show();
            adapterDatos = new AdapterDatos(arrayList);
            adapterDatos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"AAAAAAAAAAAAA",Toast.LENGTH_LONG).show();
                }
            });
            listView.setAdapter(adapterDatos);
        }
        else Toast.makeText(getApplicationContext(),"No hay casas arrendadas",Toast.LENGTH_LONG).show();
    }


    private void Conectar() {
        cantidadArrendadas=findViewById(R.id.txtNodisp);
        cantidadDisponibles=findViewById(R.id.txtdisp);
        cantidadUnidades=findViewById(R.id.txtUregis);
        mejorCliente=findViewById(R.id.txtBestClient);
        tipoComun=findViewById(R.id.txtTipoComun);
        prom=findViewById(R.id.txtProm);
        btn=findViewById(R.id.btnDiaEspecifico);
    }


}
