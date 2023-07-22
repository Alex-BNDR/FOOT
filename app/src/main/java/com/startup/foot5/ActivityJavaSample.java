package com.startup.foot5;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.startup.foot5.Models.UserJavaSample;
import com.startup.foot5.R.id;
import com.startup.foot5.R.layout;
import com.startup.foot5.after_reg_auth.NewActivity;
import com.startup.foot5.after_reg_auth.NewActivityJava;
import com.startup.foot5.databinding.ActivityMainBinding;

import org.jetbrains.annotations.Nullable;

//import kotlin.Metadata;
//
//@Metadata(
//        mv = {1, 8, 0},
//        k = 1,
//        d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\b\u0010\u000f\u001a\u00020\fH\u0002J\b\u0010\u0010\u001a\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0011"},
//        d2 = {"Lcom/startup/foot5/ActivityDemo;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "Lcom/startup/foot5/databinding/ActivityMainBinding;", "db", "Lcom/google/firebase/database/FirebaseDatabase;", "root", "Landroid/widget/RelativeLayout;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "showRegisterWindow", "showSignInWindow", "app_debug"}
//)

public final class ActivityJavaSample extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase db;
    Button btnSignIn, btnRegister;
    DatabaseReference users;
    private ActivityMainBinding binding;
    private RelativeLayout root;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        FirebaseApp.initializeApp((Context) this);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        root = findViewById(id.root_element);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterWindow();
            }
        });

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInWindow();
            }
        });

    }

    private final void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder((Context) this);
        dialog.setTitle("Зареєструватись");
        dialog.setMessage("Введіть всі дані для реєстрації");

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_window = inflater.inflate(R.layout.register_window, null);
        dialog.setView(register_window);

        MaterialEditText email = register_window.findViewById(id.emailField);
        MaterialEditText pass = register_window.findViewById(id.passField);
        MaterialEditText name = register_window.findViewById(id.nameField);
        MaterialEditText phone = register_window.findViewById(id.phoneField);

        dialog.setPositiveButton("Прийняти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                boolean emailError = false, passError = false, nameError = false, phoneError = false;
                if (TextUtils.isEmpty(email.getText().toString()) && TextUtils.isEmpty(pass.getText().toString()) && pass.getText().toString().length() < 5 && TextUtils.isEmpty(name.getText().toString()) && TextUtils.isEmpty(phone.getText().toString())) {
                    {Snackbar.make(root, "Ви ввели мало даних", Snackbar.LENGTH_SHORT).show();
                        emailError = true;
                        passError = true;
                        nameError = true;
                        phoneError = true;
                    }
                }
                else if (TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(root, "Ви не ввели пошту", Snackbar.LENGTH_SHORT).show();
                    emailError = true;
                } else if (TextUtils.isEmpty(pass.getText().toString())) {
                    Snackbar.make(root, "Ви не ввели пароль", Snackbar.LENGTH_SHORT).show();
                    passError = true;
                } else if (pass.getText().toString().length() < 5) {
                    Snackbar.make(root, "Пароль має бути більше 5 символів", Snackbar.LENGTH_SHORT).show();
                    passError = true;
                } else if (TextUtils.isEmpty(name.getText().toString())) {
                    Snackbar.make(root, "Ви не ввели ім'я", Snackbar.LENGTH_SHORT).show();
                    nameError = true;

                } else if (TextUtils.isEmpty(phone.getText().toString())) {
                    Snackbar.make(root, "Ви не ввели телефон", Snackbar.LENGTH_SHORT).show();
                    phoneError = true;

                }

                if (emailError == false && passError == false && nameError == false && phoneError == false) {
                    auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            UserJavaSample user = new UserJavaSample();
                            user.setEmail(email.getText().toString());
                            user.setName(name.getText().toString());
                            user.setPhone(phone.getText().toString());
                            user.setPass(pass.getText().toString());

                            startActivity(new Intent(ActivityJavaSample.this, NewActivityJava.class));
                            finish();

                            Snackbar.make(root, "Ви успішно зареєстувались", Snackbar.LENGTH_LONG).show();
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(root, "Помилка реєстрації. " + e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    });

                }

            }
        });

        dialog.setNegativeButton("Відмінити", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();
    }

    private void showSignInWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder((Context) this);
        dialog.setTitle((CharSequence) "Ввійти");
        dialog.setMessage((CharSequence) "Введіть дані для входу");

        LayoutInflater inflater = LayoutInflater.from((Context) this);
        View sign_in_window = inflater.inflate(R.layout.sign_in_window, (ViewGroup) null);
        dialog.setView(sign_in_window);

        MaterialEditText email = sign_in_window.findViewById(id.emailField);
        MaterialEditText pass = sign_in_window.findViewById(id.passField);

        dialog.setPositiveButton("Прийняти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(root, "Введіть вашу пошту", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass.getText().toString())) {
                    Snackbar.make(root, "Введіть ваш пароль", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (pass.getText().toString().length() < 5) {
                    Snackbar.make(root, "Пароль має бути більше 5 символів", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(ActivityJavaSample.this, NewActivityJava.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root, "Помилка авторизацїї. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });

            }
        });

        dialog.setNegativeButton("Відмінити", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();
    }

}
