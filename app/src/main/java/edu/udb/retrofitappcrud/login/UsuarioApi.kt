package edu.udb.retrofitappcrud.login

import retrofit2.Call
import retrofit2.http.*

interface UsuarioApi {
    @POST("usuario.php")
    fun login(@Body user: Usuario): Call<Void>
}