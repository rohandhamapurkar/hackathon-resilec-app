package io.fragmentationsalt.resilec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static io.fragmentationsalt.resilec.ContactsActivity.MyPREFERENCES;

public class ViewDetailsActivity extends AppCompatActivity {


    private TextView Dname;
    private TextView Dnum;
    private TextView Dname1;
    private TextView Dnum1;
    private TextView Dname2;
    private TextView Dnum2;
    private TextView Dname3;
    private TextView Dnum3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        Dname = (TextView) findViewById(R.id.dname);
        Dname1 = (TextView) findViewById(R.id.dcname1);
        Dname2 = (TextView) findViewById(R.id.dcname2);
        Dname3 = (TextView) findViewById(R.id.dcname3);
        Dnum = (TextView) findViewById(R.id.dnum);
        Dnum1 = (TextView) findViewById(R.id.dcnum1);
        Dnum2 = (TextView) findViewById(R.id.dcnum2);
        Dnum3 = (TextView) findViewById(R.id.dcnum3);

        SharedPreferences sharedpreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Dname.setText(sharedpreferences.getString("UserName", null));
        Dnum.setText(sharedpreferences.getString("OwnContact", null));
        Dname1.setText(sharedpreferences.getString("Name1", null));
        Dnum1.setText(sharedpreferences.getString("Contact1", null));
        Dname2.setText(sharedpreferences.getString("Name2", null));
        Dnum2.setText(sharedpreferences.getString("Contact2", null));
        Dname3.setText(sharedpreferences.getString("Name3", null));
        Dnum3.setText(sharedpreferences.getString("Contact3", null));

        setContentView(R.layout.activity_view_details);
        Button closeButton = (Button)findViewById(R.id.button2);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityChangeIntent = new Intent(ViewDetailsActivity.this, MainActivity.class);

                // currentContext.startActivity(activityChangeIntent);

                ViewDetailsActivity.this.startActivity(activityChangeIntent);
            }
        });
    }
}
