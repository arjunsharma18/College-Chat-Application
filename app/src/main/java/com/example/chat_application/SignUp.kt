package com.example.chat_application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUp : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDBRef:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()
        mAuth=FirebaseAuth.getInstance()
        name = findViewById(R.id.edt_name)
        email = findViewById(R.id.edt_email)
        password = findViewById(R.id.edt_password)
        signup = findViewById(R.id.btn_signup)

        signup.setOnClickListener {
            val nm = name.text.toString()
            val em = email.text.toString()
            val pass = password.text.toString()
            signUpp(nm,em,pass)

        }

    }

    private fun signUpp(name:String,email:String,password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this,MainActivity::class.java)
                    Toast.makeText(this,"SignUp Successful !!!",Toast.LENGTH_SHORT).show()
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    if(password.length<8){
                        Toast.makeText(this,"Your Password Should be atleast 8 characters long",Toast.LENGTH_LONG).show()
                    }
                    else {
                        Toast.makeText(
                            this,
                            "Some Error has Occurred while SignUp",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                // ...
            }
    }
    private fun addUserToDatabase(name:String,email: String,uid:String){
mDBRef =FirebaseDatabase.getInstance().getReference()
        mDBRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}