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
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ViviendasActivity extends AppCompatActivity {
    ArrayList<UnidadHabitacional> listDatos;
    RecyclerView recyclerView;
    AdapterDatos adapterDatos;
    OperacionesBd bd;
    UnidadHabitacional unidadHabitacional;
    int dia,mes,ano;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viviendas);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        recyclerView=findViewById(R.id.reciclerViviendas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        bd=new OperacionesBd(getApplicationContext());
        adapter();
    }
    public void adapter(){
        listDatos=bd.listaViviendas();
        if(listDatos!=null) {
            adapterDatos = new AdapterDatos(listDatos);
            adapterDatos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listDatos.get(recyclerView.getChildAdapterPosition(v)).getEstaArrendada().equals("si"))
                        Toast.makeText(ViviendasActivity.this, "Ya se encuentra arrendada", Toast.LENGTH_SHORT).show();
                    else {
                        unidadHabitacional = listDatos.get(recyclerView.getChildAdapterPosition(v));
                        dialogArrendar();
                    }
                }
            });
            recyclerView.setAdapter(adapterDatos);
        }
        else
            Toast.makeText(getApplicationContext(),"No hay viviendas registradas",Toast.LENGTH_LONG).show();
    }
    private void dialogArrendar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ViviendasActivity.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_arrendar, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        final TextView tipoAEditar = view.findViewById(R.id.tipoAEditar),direccionAEditar = view.findViewById(R.id.direccionAEditar),
        precioAEditar = view.findViewById(R.id.precioAEditar), NombreAEditar = view.findViewById(R.id.NombreAEditar),
        TelefonoAEditar = view.findViewById(R.id.TelefonoAEditar), FechaAEditar = view.findViewById(R.id.FechaAEditar);
        Button btnArrendar = view.findViewById(R.id.btnArrendar),btnEscogerFecha=view.findViewById(R.id.btnEscogerFecha);
        tipoAEditar.setText(unidadHabitacional.getTipo());
        direccionAEditar.setText(unidadHabitacional.getDireccion());
        precioAEditar.setText(unidadHabitacional.getPrecio()+" $");
        NombreAEditar.setText(unidadHabitacional.getNombrePropietario());
        TelefonoAEditar.setText(unidadHabitacional.getTelefono());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        FechaAEditar.setText(dateFormat.format(date));
        btnEscogerFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar= GregorianCalendar.getInstance();
                dia=calendar.get(Calendar.DAY_OF_MONTH);
                mes=calendar.get(Calendar.MONTH);
                ano=calendar.get(Calendar.YEAR);
                final DatePickerDialog DPdialog=new DatePickerDialog(ViviendasActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        SimpleDateFormat dateFormat = new SimpleDateFormat(
                                "yyyy-MM-dd", Locale.getDefault());
                        String fechaString=year+"-"+month+"-"+dayOfMonth;
                        try {
                            Date date1=dateFormat.parse(fechaString);
                            fechaString =dateFormat.format(date1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        FechaAEditar.setText(fechaString);
                    }
                },ano,mes,dia);
                DPdialog.show();
            }
        });
        btnArrendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bd.arrendar(unidadHabitacional.getId(),FechaAEditar.getText().toString());
                Toast.makeText(getApplicationContext(),"Vivienda arrendada",Toast.LENGTH_LONG).show();
                adapter();
                dialog.dismiss();
            }
        });
    }
}
