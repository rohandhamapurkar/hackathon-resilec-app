package io.fragmentationsalt.resilec;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelplineFragment extends Fragment {
    final String[] helplineService = new String[] { "Police", "Fire", "Ambulance",
            "Emergency Disaster Management", "Women Helpline", "Emergency Medical Service (Local Area)", "Railway Helpline", "Centralized Accident & Trauma Service",
            "All-in-One Emergency", "Child Abuse Hotline","Air Accidents","Train Accidents","Road Accidents","Anti Terror Helpline","Earthquake Helpline","Natural Disaster Control Room"};
    final String[] helplineNumber = new String[] { "100", "101", "102",
            "108", "181", "1056", "1512", "1099",
            "112", "1098","1071","1072","1073","1090","1092","1096"};
    public String phoneNo;
    public HelplineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View helplineView = inflater.inflate(R.layout.fragment_helpline, container, false);
        ListView list = (ListView) helplineView.findViewById(R.id.helplineListview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, helplineService);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Log.v("TAG", "CLICKED row number: " + arg2);
                Toast.makeText(getActivity(), helplineService[arg2], Toast.LENGTH_SHORT).show();
                phoneNo = helplineNumber[arg2];
                Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNo));
                startActivity(i);
            }

        });

        return helplineView;
    }

}
