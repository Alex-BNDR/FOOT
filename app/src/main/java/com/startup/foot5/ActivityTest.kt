package com.startup.foot5

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rengwuxian.materialedittext.MaterialEditText
import com.startup.foot5.Models.UserTest
import com.startup.foot5.R.layout
import com.startup.foot5.databinding.ActivityMainBinding

class ActivityTest : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var binding: ActivityMainBinding

    private lateinit var root: RelativeLayout

    var users: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        FirebaseApp.initializeApp((this as Context))
        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
        users = db!!.getReference("Users")

        binding = ActivityMainBinding.inflate(layoutInflater)

        var btnSignIn = findViewById<Button>(R.id.btnSignIn)
        btnSignIn.setOnClickListener { showSignInWindow() }

        var btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener { showRegisterWindow() }


    }

    private fun showRegisterWindow() {

        val dialog = AlertDialog.Builder(
            (this as Context)
        )
        dialog.setTitle("Зареєструватись")
        dialog.setMessage("Введіть всі дані для реєстрації")
        val inflater = LayoutInflater.from(this)
        val register_window = inflater.inflate(R.layout.register_window, null)
        dialog.setView(register_window)

        val email = register_window.findViewById<MaterialEditText>(R.id.emailField)
        val pass = register_window.findViewById<MaterialEditText>(R.id.passField)
        val name = register_window.findViewById<MaterialEditText>(R.id.nameField)
        val phone = register_window.findViewById<MaterialEditText>(R.id.phoneField)

        dialog.setPositiveButton(
            "Прийняти",
            DialogInterface.OnClickListener { dialogInterface, which ->
                if (TextUtils.isEmpty(email.text.toString())) {
                    Snackbar.make(root!!, "Введіть вашу пошту", Snackbar.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                if (TextUtils.isEmpty(pass.text.toString())) {
                    Snackbar.make(root!!, "Введіть ваш пароль", Snackbar.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                if (pass.text.toString().length < 5) {
                    Snackbar.make(
                        root!!,
                        "Пароль має бути більше 5 символів",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return@OnClickListener
                }
                if (TextUtils.isEmpty(name.text.toString())) {
                    Snackbar.make(root!!, "Введіть ваше ім'я", Snackbar.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                if (TextUtils.isEmpty(phone.text.toString())) {
                    Snackbar.make(root!!, "Введіть ваш телефон", Snackbar.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                fun onClick() {
                    auth!!.createUserWithEmailAndPassword(
                        email.text.toString(),
                        pass.text.toString()
                    )
                        .addOnCompleteListener {
                            val user = UserTest()
                            user.email = email.text.toString()
                            user.name = name.text.toString()
                            user.phone = phone.text.toString()
                            user.pass = pass.text.toString()
                            users!!.child(user.email!!).setValue(user).addOnSuccessListener {
                                Snackbar.make(
                                    root!!,
                                    "Користувач доданий!",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }

                        }
                }

            })

        dialog.setNegativeButton("Відмінити") { dialogInterface, which -> dialogInterface.dismiss() }
        dialog.show()
    }

    private fun showSignInWindow() {
        val dialog = AlertDialog.Builder(
            (this as Context)
        )
        dialog.setTitle("Ввійти" as CharSequence)
        dialog.setMessage("Введіть всі дані для входу" as CharSequence)
        val inflater = LayoutInflater.from(this as Context)
        val sign_in_window = inflater.inflate(R.layout.sign_in_window, null as ViewGroup?)
        dialog.setView(sign_in_window)

        val email = sign_in_window.findViewById<MaterialEditText>(R.id.emailField)
        val pass = sign_in_window.findViewById<MaterialEditText>(R.id.passField)

        dialog.setPositiveButton(
            "Прийняти",
            DialogInterface.OnClickListener { dialogInterface, which ->
                if (TextUtils.isEmpty(email.text.toString())) {
                    Snackbar.make(root!!, "Введіть вашу пошту", Snackbar.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                if (TextUtils.isEmpty(pass.text.toString())) {
                    Snackbar.make(root!!, "Введіть ваш пароль", Snackbar.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                if (pass.text.toString().length < 5) {
                    Snackbar.make(
                        root!!,
                        "Пароль має бути більше 5 символів",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return@OnClickListener
                }

                auth!!.createUserWithEmailAndPassword(email.text.toString(), pass.text.toString())
                    .addOnSuccessListener {
                        val user = UserTest()
                        user.email = email.text.toString()
                        user.pass = pass.text.toString()
                        users!!.child(user.email!!).setValue(user).addOnSuccessListener {
                            Snackbar.make(
                                root!!,
                                "Користувач ввійшов!",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }

            })
        dialog.setNegativeButton("Відмінити") { dialogInterface, which -> dialogInterface.dismiss() }
        dialog.show()
    }
}
