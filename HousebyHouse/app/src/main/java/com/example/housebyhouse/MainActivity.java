package com.example.housebyhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    CardView cvViviendas, cvAddViviendas, cvAddDueno, cvAdmin;
    OperacionesBd bd;
    String idActual;
    ArrayAdapter<String> adapter;
    AdapterDatos adapterDatos;
    RecyclerView recyclerView;
    ArrayList<String> list;
    UnidadHabitacional unidadHabitacional;
    ArrayList<UnidadHabitacional> listDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Conectar();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);


        list=new ArrayList<>();
        list.add("Casa");
        list.add("Apartamento");
        list.add("Finca");
        bd = new OperacionesBd(this);
        cvViviendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViviendasActivity.class));
            }
        });
        cvAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),activity_Datos.class));
            }
        });
        cvAddViviendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogIngresoDueno();
            }
        });
        cvAddDueno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDueno();
            }
        });
    }



    private void Conectar() {
        cvAddDueno=findViewById(R.id.cvAddDueno);
        cvAddViviendas=findViewById(R.id.cvRegistrarVivienda);
        cvAdmin=findViewById(R.id.cvAdmin);
        cvViviendas=findViewById(R.id.cvViviendas);
    }

    private void DialogDueno(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater =getLayoutInflater();
        final View view=layoutInflater.inflate(R.layout.layout_dialog_dueno,null);
        builder.setView(view);
        final AlertDialog dialog =builder.create();
        dialog.show();
        final EditText nombre,id,tel,password;
        Button btnAnadir;
        nombre=view.findViewById(R.id.txtName);
        id=view.findViewById(R.id.txtId);
        tel=view.findViewById(R.id.txtTelefono);
        password=view.findViewById(R.id.txtPassword);
        btnAnadir=view.findViewById(R.id.btnAddDueno);
        btnAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!vacio()) {
                    if (bd.Registrar(id.getText().toString(), nombre.getText().toString(), tel.getText().toString(),
                            password.getText().toString()))
                        Toast.makeText(MainActivity.this, "Registrado", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "Usuario ya registrado", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }

            }
            public boolean vacio(){
                if(nombre.getText().toString().equals("")||id.getText().toString().equals("")||
                tel.getText().toString().equals("")||password.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Llene todos los campos",Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });

    }

    private void DialogIngresoDueno() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_ingreso_dueno, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        final EditText Id, password;
        Button btnIngresar;
        Id = view.findViewById(R.id.txt2Id);
        password = view.findViewById(R.id.txt2Password);
        btnIngresar = view.findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!vacio()) {
                    if (bd.Ingresar(Id.getText().toString(), password.getText().toString())) {
                        idActual = Id.getText().toString();
                        MenuManejo();
                    } else
                        Toast.makeText(MainActivity.this, "Datos erróneos", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
            public boolean vacio(){
                if(Id.getText().toString().equals("")||password.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Llene todos los campos",Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            final AlertDialog.Builder dialogoConfirmacion =new AlertDialog.Builder(MainActivity.this);
            dialogoConfirmacion.setMessage("¿Seguro que desea salir de hábitat horizontal?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            dialogoConfirmacion.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void MenuManejo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_menu_manejo, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        Button btnViviendasDelete = view.findViewById(R.id.btnDeleteVivienda);
        Button btnViviendasUpdate= view.findViewById(R.id.btnDesarrendar);
        Button btnAddVivienda = view.findViewById(R.id.btnAddVivienda);
        btnAddVivienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarViviendaDialog();
            }
        });
        btnViviendasDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EliminarVivienda();
            }
        });
        btnViviendasUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActualizarVivienda();
            }
        });
    }

    public void ActualizarVivienda(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_desarrendar, null);
        builder.setView(view);
        final AlertDialog dialogA = builder.create();
        dialogA.show();
        final RecyclerView rvViviendasUpdate=view.findViewById(R.id.rvViviendasUpdate);
        rvViviendasUpdate.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        listDatos=bd.MisViviendasArrendadas(idActual);
        if(listDatos!=null) {
            adapterDatos = new AdapterDatos(listDatos);
            adapterDatos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    //listDatos.get(rvViviendasDelete.getChildAdapterPosition(v));
                    AlertDialog.Builder dialogoConfirmacion =new AlertDialog.Builder(MainActivity.this);
                    dialogoConfirmacion.setMessage("¿Seguro que desea desarrendar esta vivienda?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    bd.desarrendar(listDatos.get(rvViviendasUpdate.getChildAdapterPosition(v)).getId());
                                    Toast.makeText(MainActivity.this,"Vivienda desarrendada",Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    dialogA.dismiss();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    dialogoConfirmacion.show();
                }
            });
            rvViviendasUpdate.setAdapter(adapterDatos);
        }
        else
            Toast.makeText(getApplicationContext(),"No hay viviendas registradas",Toast.LENGTH_LONG).show();
    }

    public void EliminarVivienda(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_delete_vivienda, null);
        builder.setView(view);
        final AlertDialog dialogE = builder.create();
        dialogE.show();
        final RecyclerView rvViviendasDelete=view.findViewById(R.id.rvViviendasDelete);
        rvViviendasDelete.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        listDatos=bd.MisViviendas(idActual);
        if(listDatos!=null) {
            adapterDatos = new AdapterDatos(listDatos);
            adapterDatos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder dialogoConfirmacion =new AlertDialog.Builder(MainActivity.this);
                    dialogoConfirmacion.setMessage("¿Seguro que desea Eliminar esta vivienda?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    bd.eliminarVivienda(listDatos.get(rvViviendasDelete.getChildAdapterPosition(v)).getId(),
                                            Integer.parseInt(listDatos.get(rvViviendasDelete.getChildAdapterPosition(v)).getNombrePropietario()));
                                    Toast.makeText(MainActivity.this,"Vivienda eliminada",Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    dialogE.dismiss();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    dialogoConfirmacion.show();
                }
            });
            rvViviendasDelete.setAdapter(adapterDatos);
        }
        else
            Toast.makeText(getApplicationContext(),"No hay viviendas registradas",Toast.LENGTH_LONG).show();
    }

    public void agregarViviendaDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_ingresar_vivienda, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        final Spinner spinner;
        final EditText precio,direccion;
        Button btn;
        spinner=view.findViewById(R.id.spinner);
        precio=view.findViewById(R.id.txtPrecio);
        direccion=view.findViewById(R.id.txtDireccion);
        btn=view.findViewById(R.id.addVivienda);
        adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,list);
        spinner.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!vacio()) {
                    if (bd.RegistrarVivienda(spinner.getSelectedItem().toString(),
                            Long.parseLong(precio.getText().toString()), direccion.getText().toString(),
                            idActual))
                        Toast.makeText(MainActivity.this, "Registrada", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this, "Ya existe esa direccion", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
                else
                    Toast.makeText(MainActivity.this,"llene todos los campos",Toast.LENGTH_LONG).show();
            }
            public boolean vacio(){
                if(precio.getText().toString().equals("")||direccion.getText().toString().equals("")
                ||spinner.getSelectedItem().toString().equals(""))return true;
                else return false;
            }
        });
    }

}
