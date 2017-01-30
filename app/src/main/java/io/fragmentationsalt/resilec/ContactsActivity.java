package io.fragmentationsalt.resilec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactsActivity extends AppCompatActivity {
    private EditText mContact1;
    private EditText mContact2;
    private EditText mContact3;
    private EditText mName1;
    private EditText mName2;
    private EditText mName3;
    private EditText mUserName;
    private EditText ownContact;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Contact1=null;
    public static final String Contact2=null;
    public static final String Contact3=null;
    public static final String Name1=null;
    public static final String Name2=null;
    public static final String Name3=null;
    public static final String UserName = null;
    public static final String OwnContact = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        mUserName = (EditText) findViewById(R.id.Name);
        ownContact = (EditText) findViewById(R.id.OwnNumber);
        mContact1 = (EditText) findViewById(R.id.CNumber1);
        mContact2 = (EditText) findViewById(R.id.CNumber2);
        mContact3 = (EditText) findViewById(R.id.CNumber3);
        mName1 = (EditText) findViewById(R.id.CName1);
        mName2 = (EditText) findViewById(R.id.CName2);
        mName3 = (EditText) findViewById(R.id.CName3);
        Button submitBtn = (Button) findViewById(R.id.Submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnectivity();
                set_em_contact();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public void set_em_contact() {
        boolean cancel = false;
        View focusView = null;
        String contact1=mContact1.getText().toString();
        String contact2=mContact2.getText().toString();
        String contact3=mContact3.getText().toString();
        String name1=mName1.getText().toString();
        String name2=mName2.getText().toString();
        String name3=mName3.getText().toString();
        String userName=mUserName.getText().toString();
        String ownNumber=ownContact.getText().toString();
        if(!checkName(userName))
        {
            cancel = true;
            mUserName.setError(getString(R.string.invalid));
            focusView = mUserName;
        }
        if(!checkName(name1))
        {
            cancel = true;
            mName1.setError(getString(R.string.invalid));
            focusView = mName1;
        }
        if(!checkName(name2))
        {
            cancel = true;
            mName2.setError(getString(R.string.invalid));
            focusView = mName2;
        }
        if(!checkName(name3))
        {
            cancel = true;
            mName3.setError(getString(R.string.invalid));
            focusView = mName3;
        }
        if(isEmpty(contact1) || (isEmpty(contact2)) || (isEmpty(contact3)) || (isEmpty(name1)) || (isEmpty(name2)) || (isEmpty(name3)) || (isEmpty(userName)) || (isEmpty(ownNumber)))
        {
            cancel = true;
            Toast.makeText(this,"Input is empty",Toast.LENGTH_LONG);
        }
        if(!TextUtils.isDigitsOnly(contact1) ||  !TextUtils.isDigitsOnly(contact2) || !TextUtils.isDigitsOnly(contact3))
        {
            cancel = true;
            Toast.makeText(this,"Contact details invalid",Toast.LENGTH_LONG);
        }
        if(cancel)
        {
            assert focusView != null;
            focusView.requestFocus();
        }
        else
        {
            SharedPreferences sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("OwnContact",ownNumber);
            editor.putString("UserName",userName);
            editor.putString("Contact1",contact1);
            editor.putString("Contact2",contact2);
            editor.putString("Contact3",contact3);
            editor.putString("Name1",name1);
            editor.putString("Name2",name2);
            editor.putString("Name3",name3);
            editor.commit();
            Toast.makeText(this,"Changes success",Toast.LENGTH_LONG).show();
        }

    }

    private boolean checkName(String Name){
        Pattern pattern = Pattern.compile("^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}");
        Matcher matcher = pattern.matcher(Name);
        return matcher.find();
    }

    private boolean isEmpty(String test)
    {
        return TextUtils.isEmpty(test);
    }

    public void checkConnectivity() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                Toast.makeText(this, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                Toast.makeText(this, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No Network Found. Please connect data", Toast.LENGTH_SHORT).show();
        }
    }

}