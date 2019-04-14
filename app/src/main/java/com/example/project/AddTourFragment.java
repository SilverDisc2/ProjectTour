package com.example.project;


import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.databinding.FragmentAddTourBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTourFragment extends Fragment {

    private String tripName, description, startDate, endDate;
    FragmentAddTourBinding fragmentAddTourBinding;
    private EditText tripNameET, descriptionET;
    private TextView openDatePickerStar_TV, openDatePickerEnd_TV;
    private Button add_tripBTN;
    private Date date;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private Button openDatePickerStartBtn, openDatePickerEndBtn;
    private SimpleDateFormat dateAndTimeSDF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private SimpleDateFormat dateSDF = new SimpleDateFormat("dd MMM yyyy");
    private List<Trips> tripsList;
    ;
    private long setMiliSec;
    private long miliSecStartTime, miliSecEndTime;

    public AddTourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        fragmentAddTourBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_tour, container, false);
        View view = fragmentAddTourBinding.getRoot();
        //  fragmentAddTourBinding= DataBindingUtil.findBinding(view);

        //fragmentAddTourBinding.tripNameET.getText();
        // fragmentAddTourBinding.descriptionET.getText();
        //   fragmentAddTourBinding..getText().toString();
        //   fragmentAddTourBinding.endDateET.getText().toString();

        //Toast.makeText(getActivity(), "" +fragmentAddTourBinding.tripNameET, Toast.LENGTH_SHORT).show();


        setUpFields(view);

        fragmentAddTourBinding.addTourBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripName = fragmentAddTourBinding.tripNameET.getText().toString();
                description = fragmentAddTourBinding.descriptionET.getText().toString();
                startDate = fragmentAddTourBinding.datePickerFromTV.getText().toString();
                endDate = fragmentAddTourBinding.datePickerToTV.getText().toString();
                addToDatabase(new Trips(tripName, startDate, endDate,description));
                Toast.makeText(getActivity(), "" + tripName, Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }

    public void setUpFields(View view) {

        //  tripNameET = view.findViewById(R.id.trip_nameET);
        //   descriptionET = view.findViewById(R.id.descriptionET);

        //  add_tripBTN = view.findViewById(R.id.add_tourBTN);
        // openDatePickerStar_TV = view.findViewById(R.id.datePickerFrom_TV);
        //  openDatePickerEnd_TV = view.findViewById(R.id.datePickerTo_TV);
        fragmentAddTourBinding.datePickerFromTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpDatePicker(fragmentAddTourBinding.datePickerFromTV);

            }
        });
        fragmentAddTourBinding.datePickerToTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpDatePicker(fragmentAddTourBinding.datePickerToTV);
            }
        });
    }

    private void setUpDatePicker(final TextView tv) {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override

            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                String value = year + "/" + month + "/" + dayOfMonth + " 00:00:00";


                date = null;

                try {
                    date = dateAndTimeSDF.parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                setMiliSec = date.getTime();
                Log.d("Date in MS", "" + setMiliSec);
                //miliSec1=setMiliSec;
                tv.setText(dateSDF.format(date));
            }
        };
//miliSec=value;
        // miliSec = setMiliSec;
        //button.setText(String.valueOf(miliSec));
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    private void addToDatabase(Trips trips) {
   /*     Map<String, Object> trips = new HashMap<>();
        trips.put("name", trip_name);
        trips.put("descripion", decription);
        trips.put("startDate", start_date);
        trips.put("endDate", end_date);*/


        String uerId = firebaseAuth.getCurrentUser().getUid();
        //trips.put("uerID", uerId);

        DatabaseReference databaseReference = database.getReference().child("UsersList").child(uerId).child("Trips");
        databaseReference.push().setValue(trips);
        Toast.makeText(getActivity(), "Trip Added Successfully", Toast.LENGTH_LONG).show();
    }
}
