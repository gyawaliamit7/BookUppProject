package com.example.amitgyawali.bookupp;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.method.HideReturnsTransformationMethod;
        import android.text.method.PasswordTransformationMethod;
        import android.widget.CompoundButton;
        import android.widget.CompoundButton.OnCheckedChangeListener;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    //defining views
    private Button buttonLogin;
    private EditText edituserName;
    private EditText editpassword;
    private CheckBox showPasswd;
    private Button buttonSingUp;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        buttonLogin = findViewById(R.id.loginButton);
        edituserName = findViewById(R.id.username);
        editpassword = findViewById(R.id.password);
        showPasswd = findViewById(R.id.checkboxPaswd);
        buttonSingUp = findViewById(R.id.signUpButton);

        progressDialog = new ProgressDialog(this);

        //attaching click listener
        buttonLogin.setOnClickListener(this);
        buttonSingUp.setOnClickListener(this);
        // add onCheckedListener on checkbox
        // when user clicks on this checkbox, this is the handler.
        showPasswd.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    editpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    editpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

    }

    //method for user login
    private void userLogin(){
        String email = edituserName.getText().toString().trim();
        String password  = editpassword.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomePage.class));
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == buttonLogin){
            userLogin();
        }

        if(view == buttonSingUp){
            finish();
            startActivity(new Intent(this, SignUp.class));
        }
    }
}
