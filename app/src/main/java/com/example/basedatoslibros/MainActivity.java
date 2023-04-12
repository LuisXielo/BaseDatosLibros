package com.example.basedatoslibros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String NombreBaseDatos = "administracion";
    private EditText txt_codigo, txt_nombre, txt_autor, txt_editorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_codigo = (EditText) findViewById(R.id.txt_codigo);
        txt_nombre = (EditText) findViewById(R.id.txt_nombre);
        txt_autor = (EditText) findViewById(R.id.txt_autor);
        txt_editorial = (EditText) findViewById(R.id.txt_editorial);
    }
    // metodo gaurdar
    public void Guardar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, NombreBaseDatos, null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = txt_codigo.getText().toString();
        String nombre = txt_nombre.getText().toString();
        String autor = txt_autor.getText().toString();
        String editorial = txt_editorial.getText().toString();

        if(!codigo.isEmpty() && !nombre.isEmpty() && !autor.isEmpty() && !editorial.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("autor", autor);
            registro.put("editorial", editorial);

            BaseDeDatos.insert("libreria", null, registro);

            BaseDeDatos.close();

            txt_codigo.setText("");
            txt_nombre.setText("");
            txt_autor.setText("");
            txt_editorial.setText("");

            Toast.makeText(this, "Se ha guardado correctamente", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "introduce todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    // metodo buscar
    public void Buscar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, NombreBaseDatos, null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = txt_codigo.getText().toString();

        if(!codigo.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select nombre, autor, editorial from libreria where codigo =" + codigo, null);

            // al devolver un array es [0,1] proque siempre inicia en 0 el array
            if (fila.moveToFirst()){
                txt_nombre.setText(fila.getString(0));
                txt_autor.setText(fila.getString(1));
                txt_editorial.setText(fila.getString(2));
                BaseDeDatos.close();

            }else {
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }
        }else{
            Toast.makeText(this, "Debes introducir el codigo", Toast.LENGTH_LONG).show();
        }
    }
}