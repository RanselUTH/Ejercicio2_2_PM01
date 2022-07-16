package com.example.ejercicio2_2_pm01.Vinculo;


import com.example.ejercicio2_2_pm01.Modulo.Consultas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuariosApi
{
    String Ruta0 = "/posts";
    String Ruta1= "/posts/{valor}";

    @GET(Ruta0)
    Call<List<Consultas>> getUsuarios();

    @GET(Ruta1)
    Call<Consultas> getUsuario(@Path("valor") String valor);
}
