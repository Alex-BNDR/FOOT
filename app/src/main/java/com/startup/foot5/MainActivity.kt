package com.startup.foot5

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button;
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.rengwuxian.materialedittext.MaterialEditText
import com.startup.foot5.Models.User
import com.startup.foot5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var binding: ActivityMainBinding

    private lateinit var root : RelativeLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        root = findViewById(R.id.root_element)
        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()

        var btnRegister = findViewById<Button>(R.id.btnRegister)
        var btnSignIn = findViewById<Button>(R.id.btnSignIn)

        var users = db.getReference("Users")

        binding.btnRegister.setOnClickListener(View.OnClickListener {
            showRegisterWindow()
        })

        binding.btnSignIn.setOnClickListener(View.OnClickListener {
            showSignInWindow()
        })

    }

    private fun showSignInWindow() {
        var dialog= AlertDialog.Builder(this)
        dialog.setTitle("Ввійти")
        dialog.setMessage("Введіть всі дані для входу")

        var inflater = LayoutInflater.from(this)
        var sign_in_window = inflater.inflate(R.layout.sign_in_window, null)
        dialog.setView(sign_in_window)

        var email:MaterialEditText = sign_in_window.findViewById(R.id.emailField)
        var pass:MaterialEditText = sign_in_window.findViewById(R.id.passField)

        dialog.setPositiveButton("Прийняти") { dialogInterface: DialogInterface, i: Int ->
            if(TextUtils.isEmpty(email.text.toString())) Snackbar.make(root, "Введіть вашу пошту", Snackbar.LENGTH_SHORT).show() ; return@setPositiveButton

            if(TextUtils.isEmpty(pass.text.toString()) && pass.text.toString().length < 5) Snackbar.make(root, "Введіть ваш пароль", Snackbar.LENGTH_SHORT).show() ; return@setPositiveButton

            Toast.makeText(applicationContext, "Ви успішно ввійшли", Toast.LENGTH_SHORT).show()}

        dialog.setNegativeButton("Відмінити"){dialogInterface : DialogInterface, i:Int ->
            Toast.makeText(applicationContext, "Ви відмінили ввід даних", Toast.LENGTH_SHORT).show()}

        dialog.setCancelable(true)
        dialog.show()
    }


    private fun showRegisterWindow() {
        var dialog= AlertDialog.Builder(this)
        dialog.setTitle("Зареєструватись")
        dialog.setMessage("Введіть всі дані для реєстрації")

        var inflater = LayoutInflater.from(this)
        var register_window = inflater.inflate(R.layout.register_window, null)
        dialog.setView(register_window)

        var email:MaterialEditText = register_window.findViewById(R.id.emailField)
        var pass:MaterialEditText = register_window.findViewById(R.id.passField)
        var name:MaterialEditText = register_window.findViewById(R.id.nameField)
        var phone:MaterialEditText = register_window.findViewById(R.id.phoneField)

        dialog.setPositiveButton("Прийняти") { dialogInterface: DialogInterface, i: Int ->

            if (TextUtils.isEmpty(email.text.toString())) Snackbar.make(
                root,
                "Введіть вашу пошту",
                Snackbar.LENGTH_SHORT
            ).show(); return@setPositiveButton

            if (TextUtils.isEmpty(pass.text.toString()) && pass.text.toString().length < 5) Snackbar.make(
                root,
                "Введіть ваш пароль",
                Snackbar.LENGTH_SHORT
            ).show(); return@setPositiveButton

            if (TextUtils.isEmpty(name.text.toString())) Snackbar.make(
                root,
                "Введіть ваше ім'я",
                Snackbar.LENGTH_SHORT
            ).show(); return@setPositiveButton

            if (TextUtils.isEmpty(phone.text.toString())) Snackbar.make(
                root,
                "Введіть ваш телефон",
                Snackbar.LENGTH_SHORT
            ).show(); return@setPositiveButton
        }
//            auth.createUserWithEmailAndPassword(email.text.toString(), pass.text.toString()).addOnSuccessListener(OnSuccessListener<AuthResult>() {
//                @Override
//                public void onSuccess(AuthResult authResult) {
//
//                    Toast.makeText(Register.this, "Success", Toast.LENGTH_LONG).show();
//                }
//
//
//
//            auth.createUserWithEmailAndPassword(email.text.toString(), pass.text.toString())
//                .addOnSuccessListener(OnSuccessListener<AuthResult>(AuthResult)) { override fun onSuccess(authResult: AuthResult) {
//                val user = User(
//                    name = name.text.toString(),
//                    email = email.text.toString(),
//                    pass = pass.text.toString(),
//                    phone = phone.text.toString()
//                )
//                email.
//            }  }
//
//
//            Toast.makeText(applicationContext, "Ви успішно зареєструвались", Toast.LENGTH_SHORT).show()}
//
//        dialog.setNegativeButton("Відмінити"){dialogInterface : DialogInterface, i:Int ->
//            Toast.makeText(applicationContext, "Ви відмінили ввід даних", Toast.LENGTH_SHORT).show()}

        dialog.setCancelable(true)
        dialog.show()




    }
}