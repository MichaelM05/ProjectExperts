package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mjb.projectexperts.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistryFragment extends Fragment {


    public RegistryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_registry, container, false);
        Button btnRegistry = (Button)v.findViewById(R.id.btn_accept_registry_);
        btnRegistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDestinationFragment searchDestinationFragment = new SearchDestinationFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, searchDestinationFragment, "searchDestinationFragment");
                ft.addToBackStack("searchDestinationFragment");
                ft.commit();
            }
        });

        return v;
    }

}
