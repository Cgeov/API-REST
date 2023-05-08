package edu.udb.retrofitappcrud.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.udb.retrofitappcrud.R
import edu.udb.retrofitappcrud.alumno.mainAlumno

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

class mainUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Obtener las credenciales de autenticación
        val auth_username = "admin"
        val auth_password = "admin123"

        val btn = findViewById<TextView>(R.id.btnLogear);
        val txtUser = findViewById<EditText>(R.id.txtUsername)
        val txtPass = findViewById<EditText>(R.id.txtPassword)

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", Credentials.basic(auth_username, auth_password))
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dsmmoviles.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val userService = retrofit.create(UsuarioApi::class.java)

        btn.setOnClickListener {

            val user = Usuario(txtUser.text.toString(),txtPass.text.toString())
            val call = userService.login(user)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@mainUsuario,
                            "Bienvenido :)",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@mainUsuario, mainAlumno::class.java)
                        startActivity(intent)
                    } else {
                        Log.e("errror_---------->" , response.toString())
                        Toast.makeText(
                            this@mainUsuario,
                            "Usuario No valido",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("API", "Error al obtener el usuario: ${t.message}")
                    Toast.makeText(
                        this@mainUsuario,
                        "Error al encontrar Usuario",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}