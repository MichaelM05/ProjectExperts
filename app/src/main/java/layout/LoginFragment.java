package layout;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mjb.projectexperts.Domain.User;
import com.mjb.projectexperts.Domain.VolleyS;
import com.mjb.projectexperts.MenuActivity;
import com.mjb.projectexperts.R;

import org.json.JSONObject;

import java.util.HashMap;

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
    private ProgressDialog progressDialog;
    public LoginFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        Button btnAccept = (Button)v.findViewById(R.id.btn_accept_login);

        txtUserName = (TextView) v.findViewById(R.id.txtUserName);
        txtPassword = (TextView) v.findViewById(R.id.txtPassword);


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Espere....");
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Autenticando");

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = txtUserName.getText().toString();
                password = txtPassword.getText().toString();

                if(validateFields(userName,password)){
                    progressDialog.show();
                    login(v.getContext(),userName,password,v);
                }else{
                    Toast.makeText(v.getContext(), "Datos erroneos", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return v;
    }



    private void login(final Context context, final String userName, final String password,final View v){

        RequestQueue queue = Volley.newRequestQueue(context);
        final String URL = "http://rutascr.esy.es/WebServices/login";

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName",userName);
        params.put("userPassword",password);

        JsonObjectRequest request_json = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(isUser(response)){
                            Toast.makeText(v.getContext(), "¡Bienvenido " + userName + " !", Toast.LENGTH_SHORT).show();
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
                            progressDialog.dismiss();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(v.getContext(), "Usuario no registrado", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                progressDialog.dismiss();
                Toast.makeText(v.getContext(), "Error problemas de conexión", Toast.LENGTH_SHORT).show();
            }
        });


        int socketTimeout = 15000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request_json.setRetryPolicy(policy);
        queue.add(request_json);


    }


    public boolean isUser(JSONObject response){
        try {
            User user = new User(response);
            if (user.getIdUser().length() > 0) {
                ((MenuActivity) getActivity()).user = user;
                return true;
            } else {
                return false;
            }
        }catch (Exception e){}
        return false;
    }


    private boolean validateFields(String userName, String password){

        if(userName.length() > 0 && password.length() > 0 ){
            return true;
        }else{
            return false;
        }

    }





}
