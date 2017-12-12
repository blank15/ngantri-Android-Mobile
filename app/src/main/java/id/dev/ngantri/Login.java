package id.dev.ngantri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPass;
    Button buttonLogin;
    TextView textViewRegis;
    ProgressDialog progres;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;

    private static final String TAG = Login.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPass = (EditText)findViewById(R.id.editTextPasword);
        textViewRegis = (TextView)findViewById(R.id.textViewNewMember);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);

        textViewRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Registrasi.class);
                startActivity(intent);
            }
        });

        fAuth = FirebaseAuth.getInstance();
        progres = new ProgressDialog(Login.this);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextEmail.getText().toString().isEmpty()){
                    editTextEmail.setError("Email tidak boleh kosong!");
                }else if(editTextPass.getText().toString().isEmpty()){
                    editTextPass.setError("Password tidak boleh kosong");
                }else {

                    progres.setTitle("Login");
                    progres.setMessage("Harap Tunggu");
                    progres.show();
                    progres.setCancelable(false);
                    progres.setCanceledOnTouchOutside(false);
                    signIn(editTextEmail.getText().toString(),editTextPass.getText().toString());
                }
            }
        });

    }

    private void signIn(final String email, final String password) {
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progres.dismiss();
                Log.d("Ket",task.isSuccessful()+" " + email + " " +password);
                if(!task.isSuccessful()){
                    Toast.makeText(Login.this,"Email atau password tidak Cocok!",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
