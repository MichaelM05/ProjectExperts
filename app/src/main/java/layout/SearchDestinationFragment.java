package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.mjb.projectexperts.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchDestinationFragment extends Fragment {

    String[] duracion = {"1 HORA","2 HORA","3 HORA"};
    String[] actividad = {"Actividad 1","Actividad 2"};
    String[] distancia = {"1 KM","2 KM"};


    public SearchDestinationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search_destination, container, false);

        ArrayAdapter<String> arrayAdapterLocation = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, distancia);
        MaterialBetterSpinner materialDesignSpinnerLocation = (MaterialBetterSpinner)
                v.findViewById(R.id.spinner_actividad);
        materialDesignSpinnerLocation.setAdapter(arrayAdapterLocation);

        ArrayAdapter<String> arrayAdapterActivity = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, distancia);
        MaterialBetterSpinner materialDesignSpinnerActivity = (MaterialBetterSpinner)
                v.findViewById(R.id.spinner_distancia);
        materialDesignSpinnerActivity.setAdapter(arrayAdapterActivity);

        ArrayAdapter<String> arrayAdapterTT = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, duracion);
        MaterialBetterSpinner materialDesignSpinnerTT = (MaterialBetterSpinner)
                v.findViewById(R.id.spinner_duracion);
        materialDesignSpinnerTT.setAdapter(arrayAdapterTT);


        Button btnAccept = (Button)v.findViewById(R.id.btn_accept_search);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoutesFoundFragment routesFoundFragment = new RoutesFoundFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, routesFoundFragment, "routesFoundFragment");
                ft.commit();
            }
        });

        return v;
    }

}
