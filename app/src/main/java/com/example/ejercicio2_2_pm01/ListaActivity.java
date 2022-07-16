package com.example.ejercicio2_2_pm01;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ejercicio2_2_pm01.Modulo.Consultas;
import com.example.ejercicio2_2_pm01.Vinculo.UsuariosApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaActivity extends AppCompatActivity {

    ListView listacompleta;
    ArrayList<String> titulos = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    EditText txtidusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        obtenerListaPersonas();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titulos);
        listacompleta = (ListView) findViewById(R.id.fulllist);
        listacompleta.setAdapter(arrayAdapter);
    }
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
            public void onResponse(Call<List<Consultas>> call, Response<List<Consultas>> response)
            {
                for(Consultas consultas : response.body())
                {
                    Log.i(consultas.getTitle(), "onResponse");
                    titulos.add(consultas.getTitle());

                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Consultas>> call, Throwable t) {
                t.getMessage();
            }
        });
    }

}