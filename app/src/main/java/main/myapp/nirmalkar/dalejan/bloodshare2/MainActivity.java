package main.myapp.nirmalkar.dalejan.bloodshare2;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener, AdapterView.OnItemSelectedListener {


    private Spinner spinner;
    private static final String[] paths = {"Select Blood Group", "A+", "B+", "A-", "B-", "AB+", "AB-", "O+", "O-"};
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton btnSignIn;
    private Button btnSignOut, help;

    private LinearLayout llProfileLayout;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail, jjjj, jjjj1, jjjj2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        btnSignOut = (Button) findViewById(R.id.btn_sign_out);
        help = (Button) findViewById(R.id.help);
        llProfileLayout = (LinearLayout) findViewById(R.id.llProfile);
        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        jjjj = (TextView) findViewById(R.id.jjjj);
        jjjj1 = (TextView) findViewById(R.id.jjjj1);
        jjjj2 = (TextView) findViewById(R.id.jjjj2);
        spinner = (Spinner) findViewById(R.id.spineer);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        btnSignIn.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
        help.setOnClickListener(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                SearchBlood.bg = "Select Blood Group";
                break;

            case 1:
                SearchBlood.bg = "A+";
                startActivity(new Intent(MainActivity.this, SearchBlood.class));
                break;
            case 2:
                SearchBlood.bg = "B+";
                startActivity(new Intent(MainActivity.this, SearchBlood.class));
                // Whatever you want to happen when the second item gets selected
                break;
            case 3:
                SearchBlood.bg = "A-";
                startActivity(new Intent(MainActivity.this, SearchBlood.class));
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 4:
                SearchBlood.bg = "B-";
                startActivity(new Intent(MainActivity.this, SearchBlood.class));
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 5:
                SearchBlood.bg = "AB+";
                startActivity(new Intent(MainActivity.this, SearchBlood.class));
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 6:
                SearchBlood.bg = "AB-";
                startActivity(new Intent(MainActivity.this, SearchBlood.class));
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 7:
                SearchBlood.bg = "O+";
                startActivity(new Intent(MainActivity.this, SearchBlood.class));

                // Whatever you want to happen when the thrid item gets selected
                break;
            case 8:
                SearchBlood.bg = "O-";
                startActivity(new Intent(MainActivity.this, SearchBlood.class));
                // Whatever you want to happen when the thrid item gets selected
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(MainActivity.this, "Wrong", Toast.LENGTH_LONG).show();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(false);
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {

        String personPhotoUrl = "";
        if (result.isSuccess()) {

            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();

            if (acct.getPhotoUrl() != null) {
                personPhotoUrl = acct.getPhotoUrl().toString();
            }

            OnRecive.user = acct.getEmail();
            txtName.setText(personName);
            txtEmail.setText(acct.getEmail());

            Glide.with(getApplicationContext()).load(personPhotoUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfilePic);
            Intent i1 = new Intent(MainActivity.this, OnRecive.class);
            startService(i1);
            updateUI(true);
        } else {
            updateUI(false);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_sign_in:
                if (isGPSEnable()) {
                    signIn();
                }
                break;

            case R.id.btn_sign_out:
                updateUI(false);
                signOut();
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(MainActivity.this, "You has been Logged out", Toast.LENGTH_LONG).show();
                break;

            case R.id.help:
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                break;


        }
    }

    public Boolean isGPSEnable() {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Toast.makeText(getApplicationContext(), "please Switch on your location", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (isGPSEnable()) {
            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
            if (opr.isDone())
            {
                GoogleSignInResult result = opr.get();
                handleSignInResult(result);
            }
            else
            {
                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                        handleSignInResult(googleSignInResult);
                    }
                });
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


    private void updateUI(boolean isSignedIn) {
        if (isGPSEnable()) {
            if (isSignedIn) {
                btnSignIn.setVisibility(View.GONE);
                jjjj.setVisibility(View.GONE);
                jjjj1.setVisibility(View.GONE);
                jjjj2.setVisibility(View.GONE);
                btnSignOut.setVisibility(View.VISIBLE);
                llProfileLayout.setVisibility(View.VISIBLE);
            }
        } else {
            btnSignIn.setVisibility(View.VISIBLE);
            jjjj.setVisibility(View.VISIBLE);
            jjjj1.setVisibility(View.VISIBLE);
            jjjj2.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);
            llProfileLayout.setVisibility(View.GONE);
        }
    }


}