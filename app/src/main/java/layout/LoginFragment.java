package layout;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mjb.projectexperts.Domain.User;
import com.mjb.projectexperts.Domain.VolleyS;
import com.mjb.projectexperts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    private String userName;
    private String password;
    private TextView txtUserName;
    private TextView txtPassword;
    private VolleyS volley;
    protected RequestQueue fRequestQueue;
    private boolean flag;

    public LoginFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        Button btnAccept = (Button)v.findViewById(R.id.btn_accept_login);

        txtUserName = (TextView) v.findViewById(R.id.txtUserName);
        txtPassword = (TextView) v.findViewById(R.id.txtPassword);




        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = txtUserName.getText().toString();
                password = txtPassword.getText().toString();

                if(validateFields(userName,password)){

                    if(login(v.getContext(),userName,password)){

                        Toast.makeText(v.getContext(), "¡Bienvenido !" + userName, Toast.LENGTH_LONG).show();
                        WelcomeFragment welcomeFragment = new WelcomeFragment();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frame, welcomeFragment, "welcomeFragment");
                        ft.addToBackStack("welcomeFragment");
                        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                        Menu nav_Menu = navigationView.getMenu();
                        nav_Menu.findItem(R.id.nav_login).setVisible(false);
                        nav_Menu.findItem(R.id.nav_register).setVisible(false);
                        nav_Menu.findItem(R.id.nav_routes_client).setVisible(true);
                        nav_Menu.findItem(R.id.nav_signout).setVisible(true);
                        nav_Menu.findItem(R.id.nav_welcome_client).setVisible(true);
                        ft.commit();
                    }else{
                        Toast.makeText(v.getContext(), "usuario no registrado" + userName, Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(v.getContext(), "Datos erroneos", Toast.LENGTH_LONG).show();
                }

            }
        });
        return v;
    }



    private boolean login(final Context context, final String userName, final String password){

        RequestQueue queue = Volley.newRequestQueue(context);
        final String URL = "http://rutascr.esy.es/WebServices/login";

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName",userName);
        params.put("userPassword",password);

        JsonObjectRequest request_json = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        flag = true;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                flag = false;
            }
        });

        queue.add(request_json);

        return flag;

    }


    private boolean validateFields(String userName, String password){

        if(userName.length() > 0 && password.length() > 0 ){
            return true;
        }else{
            return false;
        }

    }





}
