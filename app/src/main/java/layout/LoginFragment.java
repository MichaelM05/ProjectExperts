package layout;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mjb.projectexperts.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        Button btnAccept = (Button)v.findViewById(R.id.btn_accept_login);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WelcomeFragment welcomeFragment = new WelcomeFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, welcomeFragment, "welcomeFragment");
                ft.addToBackStack("welcomeFragment");
                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_login).setVisible(false);
                nav_Menu.findItem(R.id.nav_register).setVisible(false);
                ft.commit();
                //finish();
            }
        });
        return v;
    }

}
