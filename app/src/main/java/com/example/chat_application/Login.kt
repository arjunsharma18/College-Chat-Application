package com.example.chat_application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class Login : AppCompatActivity() {


    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var login:Button
    private lateinit var signup:Button
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
         mAuth=FirebaseAuth.getInstance()
        email = findViewById(R.id.edt_email)
        password = findViewById(R.id.edt_password)
        login = findViewById(R.id.btn_login)
        signup = findViewById(R.id.btn_signup)

        signup.setOnClickListener {
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {
            val em = email.text.toString()
            val pass = password.text.toString()

            login(em,pass)
        }

    }
    private fun login(email:String,password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {

                    val intent=Intent(this,MainActivity::class.java)
                    Toast.makeText(this,"Login Successful !!",Toast.LENGTH_SHORT).show()
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"Error Occurred While Login",Toast.LENGTH_SHORT).show()

                }

                // ...
            }
    }
}