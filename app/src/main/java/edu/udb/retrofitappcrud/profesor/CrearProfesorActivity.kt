package edu.udb.retrofitappcrud.profesor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.udb.retrofitappcrud.MainActivity
import edu.udb.retrofitappcrud.R
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CrearProfesorActivity : AppCompatActivity() {
    private lateinit var nombreEditText: EditText
    private lateinit var apellidoEditText: EditText
    private lateinit var edadEditText: EditText
    private lateinit var crearButton: Button

    // Obtener las credenciales de autenticación
    var auth_username = ""
    var auth_password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_profesor)

        // Obtención de datos que envia actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            auth_username = datos.getString("auth_username").toString()
            auth_password = datos.getString("auth_password").toString()
        }

        nombreEditText = findViewById(R.id.editTextNombreProfesor)
        apellidoEditText = findViewById(R.id.editTextApellidoProfesor)
        edadEditText = findViewById(R.id.editTextEdadProfesor)
        crearButton = findViewById(R.id.btnGuardarProfesor)

        crearButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val apellido = apellidoEditText.text.toString()
            val edad = edadEditText.text.toString().toInt()

            val alumno = Profesor(0,nombre, apellido, edad)
            Log.e("API", "auth_username: $auth_username")
            Log.e("API", "auth_password: $auth_password")

            // Crea un cliente OkHttpClient con un interceptor que agrega las credenciales de autenticación
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", Credentials.basic(auth_username, auth_password))
                        .build()
                    chain.proceed(request)
                }
                .build()

            // Crea una instancia de Retrofit con el cliente OkHttpClient
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dsmmoviles.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            // Crea una instancia del servicio que utiliza la autenticación HTTP básica
            val api = retrofit.create(ProfesorApi::class.java)

            api.crearProfesor(alumno).enqueue(object : Callback<Profesor> {
                override fun onResponse(call: Call<Profesor>, response: Response<Profesor>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@CrearProfesorActivity, "Profesor creado exitosamente", Toast.LENGTH_SHORT).show()
                        val i = Intent(getBaseContext(), mainProfesor::class.java)
                        startActivity(i)
                    } else {
                        val error = response.errorBody()?.string()
                        Log.e("API", "Error crear Profesor: $error")
                        Toast.makeText(this@CrearProfesorActivity, "Error al crear el Profesor", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<Profesor>, t: Throwable) {
                    Toast.makeText(this@CrearProfesorActivity, "Error al crear el Profesor", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}