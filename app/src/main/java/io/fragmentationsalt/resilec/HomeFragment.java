package io.fragmentationsalt.resilec;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.test.mock.MockPackageManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import static io.fragmentationsalt.resilec.ContactsActivity.MyPREFERENCES;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    double latitude;
    double longitude;
    int counter = 0;
    private static final int REQUEST_CODE_PERMISSION = 2;
    GPSTracker gps;
    String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;
    String sPermission = Manifest.permission.SEND_SMS;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            if ((ActivityCompat.checkSelfPermission(getActivity(), mPermission)
                    != MockPackageManager.PERMISSION_GRANTED)&&(ActivityCompat.checkSelfPermission(getActivity(), sPermission)!= MockPackageManager.PERMISSION_GRANTED)){

                ActivityCompat.requestPermissions(getActivity(), new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);
                ActivityCompat.requestPermissions(getActivity(), new String[]{sPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);
        final Button btn = (Button) homeView.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                if(counter%2 == 0){
                    String number = "+16122236429";
                    gps = new GPSTracker(getActivity());

                    // check if GPS enabled
                    if(gps.canGetLocation()){

                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();

                        // \n is for new line
                        Toast.makeText(getActivity(), "Your Location is - \nLat: "
                                + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    }else{
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gps.showSettingsAlert();
                    }
                    SharedPreferences sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    String Name = sharedpreferences.getString("UserName", "defaultString");
                    String Contact1 = sharedpreferences.getString("Contact1", "defaultString");
                    String Contact2 = sharedpreferences.getString("Contact2", "defaultString");
                    String Contact3 = sharedpreferences.getString("Contact3", "defaultString");
                    String text = Name+","+Contact1+","+Contact2+","+Contact3+","+String.valueOf(latitude)+","+String.valueOf(longitude);
                    byte[] data = new byte[0];
                    try {
                        data = text.getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String base64 = Base64.encodeToString(data, Base64.DEFAULT);
                    sendSMS(number,base64);
                }
                else{
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Tap again to send SMS", Snackbar.LENGTH_LONG).show();
                }

            }
        });
        return homeView;
    }



    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }


    //TODO: http://stackoverflow.com/questions/25461014/how-to-do-the-new-playstore-parallax-effect
}
