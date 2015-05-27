package com.proyecto.servidor;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Principal extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad);
        insertar_usuario_post("http://192.168.1.106:80/FCircle0.2/ControlAndroid");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String insertar_usuario_post(String urlPeticion) {
        String resultado="";
        try {
            URL url = new URL(urlPeticion);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setDoOutput(true);
            conexion.setRequestMethod("POST");
            MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.STRICT);

            multipartEntity.addPart("nombre", new StringBody("jorge"));
            multipartEntity.addPart("apellidos", new StringBody("muñoz carrasco"));
            multipartEntity.addPart("nombre_usuario", new StringBody("giorgio"));
            multipartEntity.addPart("clave", new StringBody("1234"));
            multipartEntity.addPart("foto", new StringBody(""));
            multipartEntity.addPart("localizacion", new StringBody(""));

            conexion.setRequestProperty("Content-Type", multipartEntity.getContentType().getValue());
            OutputStream out = conexion.getOutputStream();

            try {
                multipartEntity.writeTo(out);
            } catch (Exception e){
                Log.v("primero", e.toString());
                return e.toString();
            }finally {
                out.close();
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            String decodedString;

            while ((decodedString = in.readLine()) != null) {
                resultado+=decodedString;
            }
            in.close();
        } catch (MalformedURLException ex) {
            Log.v("segundo",ex.toString());
            return null;
        } catch (IOException ex) {
            Log.v("tercero",ex.toString());
            return null;
        }
        return resultado;
    }
}
