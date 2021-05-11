package com.cavanosa.tutorialmemory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText correo, pass;
    Button login;
    FirebaseAuth auth;//FIREBASE AUTENTIFICACION

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo = findViewById(R.id.correoL);
        pass = findViewById(R.id.passL);
        login = findViewById(R.id.login);

        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = correo.getText().toString();
                String password = pass.getText().toString();

                //VALIDACION CORREO ELECTRONICO
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    correo.setError("Correo Invalido");
                    correo.setFocusable(true);
                    //VALIDACION CONTRASEÑA
                }else if (password.length()<6){
                    pass.setError("Contraseña debe ser mayor a 6");
                    pass.setFocusable(true);
                }else{
                    LoginJugador(email, password);
                }
            }
        });
    }

    private void LoginJugador(String email, String password) {

        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            startActivity(new Intent(Login.this, MainActivity.class));
                            Toast.makeText(Login.this, "BIENVENIDO", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    //FALLA EL LOGUEO
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}