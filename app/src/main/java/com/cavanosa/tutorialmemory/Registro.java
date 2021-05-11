package com.cavanosa.tutorialmemory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Registro extends AppCompatActivity {

    EditText correo, pass, nombre;
    TextView fechaT;
    Button registrar;
    FirebaseAuth auth;//FIREBASE AUTENTIFICACION

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // CONEXION CON LA VISTA
        correo = findViewById(R.id.correo);
        pass = findViewById(R.id.pass);
        nombre = findViewById(R.id.nombre);
        fechaT = findViewById(R.id.fecha);
        registrar = findViewById(R.id.registrar);

        auth = FirebaseAuth.getInstance();

        Date date = new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("d 'de' MMMM 'del' yyyy");//22 de Diciembre del 2021
        String StringFecha = fecha.format(date);
        fechaT.setText(StringFecha);

        registrar.setOnClickListener(new View.OnClickListener() {
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
                    Registrarjugador(email, password);
                }
            }
        });


    }

    private void Registrarjugador(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //EL JUGADOR FUE REGISTRADO SATISFACTORIAMENTE
                    if(task.isSuccessful()){
                        FirebaseUser user = auth.getCurrentUser();

                        int contador = 0;


                        assert user != null;//EL USUARIO NO ES NULL
                        String uidString = user.getUid();
                        String correoString = correo.getText().toString();
                        String passString = pass.getText().toString();
                        String nombreString = nombre.getText().toString();
                        String fechaString = fechaT.getText().toString();

                        HashMap<Object, Object> DatosJUGADOR = new HashMap<>();

                        DatosJUGADOR.put("Uid",uidString);
                        DatosJUGADOR.put("CORREO",correoString);
                        DatosJUGADOR.put("PASSWORD",passString);
                        DatosJUGADOR.put("NOMBRE",nombreString);
                        DatosJUGADOR.put("FECHA",fechaString);
                        DatosJUGADOR.put("MEMORY",contador);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("MI DATA BASE JUGADORES");//NOMBRE DE BD
                        reference.child(uidString).setValue(DatosJUGADOR);
                        startActivity(new Intent(Registro.this, MainActivity.class));
                        Toast.makeText(Registro.this, "USUARIO REGISTRADO EXITOSAMENTE", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(Registro.this, "HA OCURRIDO UN ERROR", Toast.LENGTH_SHORT).show();
                    }
                }
            })
             //SI FALLA EL REGISTRO
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registro.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}