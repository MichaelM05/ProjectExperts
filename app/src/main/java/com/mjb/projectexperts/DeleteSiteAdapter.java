package com.mjb.projectexperts;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.mjb.projectexperts.Domain.Route;

import org.json.JSONObject;

import java.util.ArrayList;

import layout.CreateRouteFragment;
import layout.ModifyRouteFragment;

/**
 * Created by mm on 03/05/2017.
 */
public class DeleteSiteAdapter extends RecyclerView.Adapter<DeleteSiteAdapter.MyViewHolder> {

    private ProgressDialog progressDialog;
    private Fragment mContext;
    private ArrayList<Route> deleteSiteList;
    private boolean flag;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txvNameRoute;
        public ImageView imcRoute;
        public Button btnDelete;
        public Button btnAddSite;

        public MyViewHolder(View view) {
            super(view);
            txvNameRoute = (TextView) view.findViewById(R.id.txvNameRoute);
            imcRoute = (ImageView) view.findViewById(R.id.imc_route);
            btnDelete = (Button) view.findViewById(R.id.btn_delete);
            btnAddSite = (Button) view.findViewById(R.id.btn_add_sites_update);
        }
    }


    public DeleteSiteAdapter(Fragment mContext, ArrayList<Route> siteList) {
        this.mContext = mContext;
        this.deleteSiteList = siteList;
        progressDialog = new ProgressDialog(mContext.getActivity());
        progressDialog.setMessage("Espere....");
        progressDialog.setCancelable(false);

    }



    @Override
    public DeleteSiteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delete_site_card, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final DeleteSiteAdapter.MyViewHolder holder, final int position) {
        final Route route = deleteSiteList.get(position);

        holder.txvNameRoute.setText(route.getNameRoute());

        try {
            Glide.with(mContext).load(route.getSites().get(0).getImagesSite().get(0)).into(holder.imcRoute);

        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                deleteSite(mContext,route.getIdRoute(),v,position);
            }

        });

        holder.btnAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MenuActivity) mContext.getActivity()).idRouteUpdate =  route.getIdRoute();
                ((MenuActivity) mContext.getActivity()).isUpdate = true;
                ((MenuActivity) mContext.getActivity()).nameUpdate = route.getNameRoute();
                ((MenuActivity) mContext.getActivity()).sitesCreate = route.getSites();
                CreateRouteFragment createRouteFragment = new CreateRouteFragment();
                FragmentTransaction ft = mContext.getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, createRouteFragment, "createRouteFragment");
                ft.commit();

            }

        });



    }

    @Override
    public int getItemCount() {
        return deleteSiteList.size();
    }


    private void deleteSite(final Fragment context, int idSite, final  View v,final int position){

        RequestQueue queue = Volley.newRequestQueue(context.getActivity());
        final String URL = "http://rutascr.esy.es/WebServices/predesignedroutes/"+idSite;

        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.DELETE,URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        flag = deleteSuccess(response);
                        if(flag){
                            deleteSiteList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, deleteSiteList.size());
                            Toast.makeText(v.getContext(), "Éxito al eliminar", Toast.LENGTH_SHORT).show();
                            ModifyRouteFragment modifyRoutesFragment = new ModifyRouteFragment();
                            FragmentTransaction ft = mContext.getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.frame, modifyRoutesFragment, "modifyRouteFragment");
                            ft.addToBackStack("modifyRouteFragment");
                            ft.commit();
                        }else {
                            Toast.makeText(v.getContext(), "Error al eliminar", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Toast.makeText(context.getActivity(), "Error problemas de conexión", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


        int socketTimeout = 15000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request_json.setRetryPolicy(policy);
        queue.add(request_json);

    }


    public boolean deleteSuccess(JSONObject response){

        try{
            if(response.getString("status").toString().equalsIgnoreCase("success")){
                return true;
            }else{
                return false;
            }

        }catch (Exception e){}

        return false;
    }




}

