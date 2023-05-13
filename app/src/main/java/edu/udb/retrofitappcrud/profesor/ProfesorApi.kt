package edu.udb.retrofitappcrud.profesor

import retrofit2.Call
import retrofit2.http.*

interface ProfesorApi {
    @GET("profesor.php")
    fun obtenerProfesores(): Call<List<Profesor>>

    @GET("profesor.php/{id}")
    fun obtenerProfesorPorId(@Path("id") id: Int): Call<Profesor>

    @POST("profesor.php")
    fun crearProfesor(@Body profesor: Profesor): Call<Profesor>

    @POST("profesor.php")
    fun actualizarProfesor(@Query("id") id: Int, @Body profesor: Profesor,@Query("operacion") operacion: String): Call<Profesor>

    @POST("profesor.php")
    fun eliminarProfesor(@Query("id") id: Int, @Query("operacion") operacion: String): Call<Void>
}