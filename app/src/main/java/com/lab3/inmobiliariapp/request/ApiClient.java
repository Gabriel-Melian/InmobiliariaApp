package com.lab3.inmobiliariapp.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.lab3.inmobiliariapp.models.Contrato;
import com.lab3.inmobiliariapp.models.InmuebleModel;
import com.lab3.inmobiliariapp.models.Pago;
import com.lab3.inmobiliariapp.models.PropietarioModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClient {
    public final static  String URLBASE = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";

    public static InmoService getApiInmobiliaria(){
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                //ConvertersScalars convierten la respuesta del servidor (String, int, boolean) de texto plano a objetos Java
                //Aca es necesario porque el Login devuelve el Token en formato texto plano (String)
                .addConverterFactory(GsonConverterFactory.create(gson))
                //ConvertersGson convierte la respuesta del servidor (JSON) en objetos Java
                .build();

        return retrofit.create(InmoService.class);
    }

    public interface InmoService{

        @FormUrlEncoded
        @POST("api/Propietarios/login")
        Call<String> loginForm(@Field("Usuario") String usuario, @Field("Clave") String clave);

        @GET("api/Propietarios")
        Call<PropietarioModel> getPropietario(@Header("Authorization") String token);

        @PUT("api/Propietarios/actualizar")
        Call<PropietarioModel> actualizarProp(@Header("Authorization") String token, @Body PropietarioModel p);

        @GET("api/inmuebles")
        Call<List<InmuebleModel>> getInmuebles(@Header("Authorization") String token);
        @FormUrlEncoded
        @PUT("api/Propietarios/changePassword")
        Call<Void> changePassword(@Header("Authorization") String token,
                                  @Field("currentPassword") String currentPassword,
                                  @Field("newPassword") String newPassword);

        @PUT("api/Inmuebles/actualizar")
        Call<InmuebleModel> actualizarInmueble(@Header("Authorization") String token, @Body InmuebleModel inmueble);

        @Multipart
        @POST("api/Inmuebles/cargar")
        Call<InmuebleModel> cargarInmueble(
                @Header("Authorization") String token,
                @Part MultipartBody.Part imagen,
                @Part("inmueble") RequestBody inmueble
        );

        //Devuelve lista de contratos en base al id de un inmueble
        @GET("api/Contratos/inmueble/{id}")
        Call<List<Contrato>> obtenerContratosPorInmuebleId(
                @Header("Authorization") String token,
                @Path("id") int idInmueble
        );

        //Devuelve los inmuebles con contrato vigente
        @GET("api/Inmuebles/GetContratoVigente")
        Call<List<InmuebleModel>> obtenerInmueblesConContratoVigente(
                @Header("Authorization") String token
        );

        @GET("api/Pagos/contrato/{id}")
        Call<List<Pago>> obtenerPagosPorContrato(
                @Header("Authorization") String token,
                @Path("id") int idContrato
        );
    }

    public static void guardarToken(Context context, String token) {

        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        editor.putString("token", token);

        editor.apply();

    }

    public static String leerToken(Context context) {

        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);

        return sp.getString("token", null);

    }

}
