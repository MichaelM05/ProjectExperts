package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mjb.projectexperts.MenuActivity;
import com.mjb.projectexperts.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {


    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_welcome, container, false);

        Button btnCreate = (Button)v.findViewById(R.id.btn_create_routes);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MenuActivity) getActivity()).isUpdate = false;
                CreateRouteFragment createRouteFragment = new CreateRouteFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, createRouteFragment, "createRouteFragment");
                ft.commit();
            }
        });

        Button btnModify = (Button)v.findViewById(R.id.btn_modify_routes);
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MenuActivity) getActivity()).isUpdate = true;
                ModifyRouteFragment modifyRouteFragment = new ModifyRouteFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, modifyRouteFragment, "modifyRouteFragment");
                ft.addToBackStack("modifyRouteFragment");
                ft.commit();
            }
        });

        // Inflate the layout for this fragment
        return v;
    }



}
