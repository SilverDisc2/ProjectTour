package com.example.project;


import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class TourFragment extends Fragment {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    CircleMenu circleMenu;
    String arrayName[] = {"Tour", "Wallet", "Budget"};
private TextView headlineTV;
    public TourFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tour, container, false);

        headlineTV=view.findViewById(R.id.headlineTV);
       circleMenu=(CircleMenu) view.findViewById(R.id.circle_menu);

        circleMenu.setMainMenu(Color.parseColor("#FFFFFF"), R.drawable.smile_e, R.drawable.ic_radio_button_checked_black_24dp)
                .addSubMenu(Color.parseColor("#1E90FF"), R.drawable.tour).addSubMenu(Color.parseColor("#F4A460"), R.drawable.wallet)
                .addSubMenu(Color.parseColor("#3CB371"), R.drawable.budget).setOnMenuSelectedListener(new OnMenuSelectedListener() {






            @Override
            public void onMenuSelected(int index) {

                if (arrayName[index] == "Tour") {

                        Toast.makeText(getActivity(), "" + arrayName[index], Toast.LENGTH_LONG).show();


                        initializeFragment(new AddTourFragment());

                }
                else if (arrayName[index] == "Wallet")
                {
initializeFragment(new ExpenseFragment());
                    Toast.makeText(getActivity(), "" + arrayName[index], Toast.LENGTH_LONG).show();
                }
                else if (arrayName[index] == "Budget")
                {

                    Toast.makeText(getActivity(), "" + arrayName[index], Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }


    protected void initializeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.naviFL, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
