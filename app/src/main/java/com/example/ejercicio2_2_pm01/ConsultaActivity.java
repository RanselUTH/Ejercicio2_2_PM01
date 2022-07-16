package com.example.ejercicio2_2_pm01;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ejercicio2_2_pm01.Vinculo.UsuariosApi;
import com.example.ejercicio2_2_pm01.Modulo.Consultas;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultaActivity extends AppCompatActivity {

    ListView listapersonas;
    ArrayList<String> titulos = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    EditText txtidusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        txtidusuario = (EditText) findViewById(R.id.txtidusuario);
        //obtenerListaPersonas();
        //ObtenerUsuario();

        txtidusuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ObtenerUsuario(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titulos);
        listapersonas = (ListView) findViewById(R.id.listusers);
        listapersonas.setAdapter(arrayAdapter);

        }

    // Lo primero
    private void obtenerListaPersonas()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsuariosApi usuariosApi = retrofit.create(UsuariosApi.class);

        Call<List<Consultas>> calllista = usuariosApi.getUsuarios();

        calllista.enqueue(new Callback<List<Consultas>>() {
            @Override
            public void onResponse(Call<List<Consultas>> call, Response<List<Consultas>> response) {
                for (Consultas usuarios : response.body()) {
                    Log.i(usuarios.getTitle(), "onResponse");
                    titulos.add(usuarios.getTitle());

                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Consultas>> call, Throwable t) {
                t.getMessage();
            }
        });
        }

    private void ObtenerUsuario(String texto)
    {
        //listapersonas = null;
        //titulos.remove(0);

        if (titulos.size()>0)
        {
            titulos.remove(0);
        }
        arrayAdapter.notifyDataSetChanged();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsuariosApi usuariosApi = retrofit.create(UsuariosApi.class);

        Call<Consultas> calllista = usuariosApi.getUsuario(texto);
        calllista.enqueue(new Callback<Consultas>() {
            @Override
            public void onResponse(Call<Consultas> call, Response<Consultas> response)
            {
                Log.i(response.body().getTitle(), "onResponse");
                titulos.add(response.body().getTitle());

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Consultas> call, Throwable t) {

            }
        });
    }
}