package layout;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mjb.projectexperts.Domain.User;
import com.mjb.projectexperts.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistryFragment extends Fragment {


    private EditText txtName;
    private EditText txtUserName;
    private EditText txtEmail;
    private EditText txtPassword;
    private User user;
    private ProgressDialog progressDialog;

    public RegistryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registry, container, false);
        Button btnRegistry = (Button) v.findViewById(R.id.btn_accept_registry_);

        txtName = (EditText) v.findViewById(R.id.txtName);
        txtEmail = (EditText) v.findViewById(R.id.txtEmail);
        txtUserName = (EditText) v.findViewById(R.id.txtUserName);
        txtPassword = (EditText) v.findViewById(R.id.txtPassword);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Espere....");
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Registrando");



        btnRegistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = new User(txtName.getText().toString(), txtEmail.getText().toString(),
                        txtUserName.getText().toString(), txtPassword.getText().toString());

                if(validateFields(user)) {
                    progressDialog.show();
                    registry(v.getContext(), user);
                }else{
                    Toast.makeText(v.getContext(), "Datos erroneos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }



    private void registry(final Context context, final User user){

         RequestQueue queue = Volley.newRequestQueue(context);
         final String URL = "http://rutascr.esy.es/WebServices/users";

         HashMap<String, String> params = new HashMap<String, String>();
         params.put("idUser","0");
         params.put("fullName",user.getName());
         params.put("email",user.getEmail());
         params.put("userName",user.getUserName());
         params.put("userPassword",user.getPassword());
         params.put("admin","0");

         JsonObjectRequest request_json = new JsonObjectRequest(URL, new JSONObject(params),
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    boolean result = createSuccess(response);
                    if(result == true){
                        Toast.makeText(context, "¡Bienvenido " + user.getName() + " !", Toast.LENGTH_SHORT).show();

                        SearchDestinationFragment searchDestinationFragment = new SearchDestinationFragment();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frame, searchDestinationFragment, "searchDestinationFragment");
                        ft.addToBackStack("searchDestinationFragment");
                        ft.commit();
                        progressDialog.dismiss();

                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(context, "Erro al registrar", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                progressDialog.dismiss();
                Toast.makeText(context, "Error problemas de conexión", Toast.LENGTH_SHORT).show();

            }
        });

        int socketTimeout = 15000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request_json.setRetryPolicy(policy);
        queue.add(request_json);


    }

    public boolean createSuccess(JSONObject response){

        try{
            if(response.getString("status").toString().equalsIgnoreCase("success")){
                return true;
            }else{
                return false;
            }

        }catch (Exception e){}

        return false;
    }


    private boolean validateFields(User user){

        if(user.getName().length() > 0 && user.getEmail().length() > 0 &&
                user.getUserName().length() > 0 && user.getPassword().length() > 0){
            return true;
        }else{
            return false;
        }

    }
}
