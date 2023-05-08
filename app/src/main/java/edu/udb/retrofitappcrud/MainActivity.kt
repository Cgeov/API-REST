package edu.udb.retrofitappcrud

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import edu.udb.retrofitappcrud.login.mainUsuario

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        Handler().postDelayed({
            val intent = Intent(this@MainActivity, mainUsuario::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}