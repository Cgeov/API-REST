package edu.udb.retrofitappcrud.alumno

import edu.udb.retrofitappcrud.alumno.Alumno
import retrofit2.Call
import retrofit2.http.*

interface AlumnoApi {

    @GET("alumno.php")
    fun obtenerAlumnos(): Call<List<Alumno>>

    @GET("alumno.php/{id}")
    fun obtenerAlumnoPorId(@Path("id") id: Int): Call<Alumno>

    @POST("alumno.php")
    fun crearAlumno(@Body alumno: Alumno): Call<Alumno>

    @POST("alumno.php")
    fun actualizarAlumno(@Query("id") id: Int, @Body alumno: Alumno,  @Query("operacion") operacion: String): Call<Alumno>

    @POST("alumno.php")
    fun eliminarAlumno(@Query("id") id: Int, @Query("operacion") operacion: String): Call<Void>
}