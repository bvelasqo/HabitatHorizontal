package com.example.housebyhouse;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

public class OperacionesBd {
    Context context;
    DbHelper helper;

    public OperacionesBd(Context context){
        this.context=context;
        helper=new DbHelper(context,"BD",null,1);
    }

    //Operaciones tabla UnidadHabitacional

    public int NumeroDeViviendas(){
        SQLiteDatabase db=helper.getWritableDatabase();
        String SQL="Select * from UnidadHabitacional";
        Cursor c=db.rawQuery(SQL,null);
        int viviendas=0;
        if(c.moveToFirst())
            viviendas=c.getCount();
        db.close();
        return viviendas;
    }

    public ArrayList<UnidadHabitacional> BuscarViviendas(String fecha) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String SQL = "Select * from UnidadHabitacional WHERE FechaRecepcion = '" + fecha + "'";
        Cursor c=db.rawQuery(SQL,null);
        ArrayList<UnidadHabitacional> list=new ArrayList<>();
        if(c.moveToFirst()){
            do{
                int id=c.getInt(0),idP=c.getInt(6),precio=c.getInt(2);
                String tipo=c.getString(1), direccion=c.getString(3),
                        EstaArrendada=c.getString(5),fechas="",nombrePropietario=NombreP(idP),
                        Telefono=Tel(idP);
                if(c.getString(4)!=null)fechas=c.getString(4);
                list.add(new UnidadHabitacional(id,tipo,precio,direccion,fechas,EstaArrendada,
                        nombrePropietario,Telefono));
            }while(c.moveToNext());
        }
        db.close();
        return list;
    }

    public void arrendar(int id,String fecha){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("EstaArrendada","si");
        values.put("FechaRecepcion",fecha);
        db.update("UnidadHabitacional",values,"IdUnidad="+id,null);
        db.close();
    }

    public ArrayList<UnidadHabitacional> listaViviendas(){
        SQLiteDatabase db=helper.getWritableDatabase();
        String SQL="Select * from UnidadHabitacional";
        Cursor c=db.rawQuery(SQL,null);
        ArrayList<UnidadHabitacional> list=new ArrayList<>();
        if(c.moveToFirst()){
            do{
                int id=c.getInt(0),idP=c.getInt(6);
                long precio=c.getInt(2);
                String tipo=c.getString(1), direccion=c.getString(3),
                EstaArrendada=c.getString(5),fecha="",nombrePropietario=NombreP(idP),
                Telefono=Tel(idP);
                if(c.getString(4)!=null)fecha=c.getString(4);
                list.add(new UnidadHabitacional(id,tipo,precio,direccion,fecha,EstaArrendada,
                        nombrePropietario,Telefono));
            }while(c.moveToNext());
        }
        db.close();
        return list;
    }

    public int NumeroDeViviendasSinArrendar(){
        SQLiteDatabase db=helper.getWritableDatabase();
        String SQL="Select * from UnidadHabitacional where EstaArrendada = 'no'";
        Cursor c=db.rawQuery(SQL,null);
        int viviendas=0;
        if(c.moveToFirst())
            viviendas=c.getCount();
        db.close();
        return viviendas;
    }

    public int NumeroDeViviendasArrendadas(){
        SQLiteDatabase db=helper.getWritableDatabase();
        String SQL="Select * from UnidadHabitacional where EstaArrendada = 'si'";
        Cursor c=db.rawQuery(SQL,null);
        int viviendas=0;
        if(c.moveToFirst())
            viviendas=c.getCount();
        db.close();
        return viviendas;
    }

    public boolean RegistrarVivienda(String tipo,long precio, String direccion,String idPropietario) {
        if(!validarDireccion(direccion)){
            SQLiteDatabase db = helper.getWritableDatabase();

                ContentValues cv = new ContentValues();
                cv.put("Tipo",tipo);
                cv.put("Precio", precio);
                cv.put("Direccion", direccion);
                cv.put("EstaArrendada","no");
                cv.put("IdPropietario",idPropietario);
                db.insert("UnidadHabitacional", null, cv);
                actualizarViviendas(idPropietario);
                db.close();

                return true;

        }
        return false;
    }

    public long promedioArriendos(){
        SQLiteDatabase db=helper.getWritableDatabase();
        String SQL="Select AVG(Precio) from UnidadHabitacional where EstaArrendada = 'si'";
        Cursor c=db.rawQuery(SQL,null);
        if(c.getCount()==0)return 0;
        c.moveToFirst();
        long avg=c.getInt(0);
        db.close();
        return  avg;
    }

    public String tipoComun(){
        SQLiteDatabase db=helper.getWritableDatabase();
        String SQL="SELECT COUNT(IdUnidad), Tipo FROM UnidadHabitacional GROUP BY Tipo ORDER BY COUNT(IdUnidad) DESC";
        Cursor c=db.rawQuery(SQL,null);
        String retorno="";
        if(c.moveToFirst())
            retorno= c.getString(1);
        db.close();
        return retorno;
    }

    private boolean validarDireccion(String direccion){
        SQLiteDatabase db=helper.getWritableDatabase();
        String SQL="Select * from UnidadHabitacional where Direccion='"+direccion+"'";
        Cursor c=db.rawQuery(SQL,null);
        boolean retorno=false;
        if(c.moveToFirst())
            retorno=c.moveToFirst();
        db.close();
        return retorno;
    }

    //Operaciones tabla propietario

    private String NombreP(int id){
        SQLiteDatabase db=helper.getWritableDatabase();
        String SQL="Select Nombre from Propietario where IdPropietario="+id;
        Cursor c=db.rawQuery(SQL,null);
        String nombre="";
        if(c.moveToFirst())
             nombre=c.getString(0);
        db.close();
        return nombre;
    }

    private String Tel(int id){
        SQLiteDatabase db=helper.getWritableDatabase();
        String SQL="Select Telefono from Propietario where IdPropietario="+id;
        Cursor c=db.rawQuery(SQL,null);
        String tel="";
        if(c.moveToFirst())
             tel=c.getString(0);
        db.close();
        return tel;
    }

    private boolean BuscarId(String id){
        SQLiteDatabase db=helper.getWritableDatabase();
        String SQL="Select * from Propietario where IdPropietario="+id+"";
        Cursor c=db.rawQuery(SQL,null);
        boolean retorno=false;
        if(c.moveToFirst())
            retorno=c.moveToFirst();
        db.close();
        return retorno;
    }

    public boolean Ingresar(String id, String password){
        SQLiteDatabase db=helper.getWritableDatabase();
        String SQL="Select IdPropietario from Propietario where IdPropietario = "+id+" AND Password = '"+password+"'";
        Cursor c=db.rawQuery(SQL,null);
        boolean retorno=false;
        if(c.moveToFirst())
            retorno=c.moveToFirst();
        db.close();
        return retorno;
    }

    public boolean Registrar(String id, String nombre,String telefono,String password){
        if(!BuscarId(id)) {
            SQLiteDatabase db = helper.getWritableDatabase();
            try {
                ContentValues cv = new ContentValues();
                cv.put("IdPropietario", id);
                cv.put("Nombre",nombre);
                cv.put("Telefono", telefono);
                cv.put("Password", password);
                cv.put("Viviendas",0);
                db.insert("Propietario", null, cv);
                db.close();
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    private void actualizarViviendas(String id){
            int v = viviendasPropietario(id) + 1;
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Viviendas",v);
            db.update("Propietario",values,"IdPropietario="+id,null);
            db.close();
    }

    private int viviendasPropietario(String id){
        SQLiteDatabase db=helper.getWritableDatabase();
        String SQL="Select Viviendas from Propietario where IdPropietario="+id+"";
        Cursor c=db.rawQuery(SQL,null);
        int retorno=0;
        if(c.moveToFirst())
             retorno=c.getInt(0);
        db.close();
        return retorno;
    }

    public String MejorCliente(){
        SQLiteDatabase db=helper.getWritableDatabase();
        String SQL="SELECT IdPropietario,Nombre,Viviendas FROM Propietario ORDER BY Viviendas DESC";
        Cursor c=db.rawQuery(SQL,null);
        String retorno="";
        if(c.moveToFirst())
             retorno = c.getString(0)+" - "+c.getString(1)+" - Viviendas: "+c.getString(2);
        db.close();
        return retorno;
    }



}
