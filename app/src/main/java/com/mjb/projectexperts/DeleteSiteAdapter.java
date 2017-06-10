package com.mjb.projectexperts;

import android.support.v4.app.Fragment;
import android.content.Context;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.mjb.projectexperts.Domain.Route;
import com.mjb.projectexperts.Domain.Site;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import layout.MapFragment;
import layout.ModifyRouteFragment;
import layout.MyRoutesFragment;

/**
 * Created by mm on 03/05/2017.
 */
public class DeleteSiteAdapter extends RecyclerView.Adapter<DeleteSiteAdapter.MyViewHolder> {


    private Fragment mContext;
    private ArrayList<Route> deleteSiteList;
    private boolean flag;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txvNameRoute;
        public ImageView imcRoute;
        public Button btnDelete;

        public MyViewHolder(View view) {
            super(view);
            txvNameRoute = (TextView) view.findViewById(R.id.txvNameRoute);
            imcRoute = (ImageView) view.findViewById(R.id.imc_route);
            btnDelete = (Button) view.findViewById(R.id.btn_delete);
        }
    }


    public DeleteSiteAdapter(Fragment mContext, ArrayList<Route> siteList) {
        this.mContext = mContext;
        this.deleteSiteList = siteList;
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
            ArrayList<Site> sites =  route.getSites();
            Site site =  sites.get(position);

              if(deleteSite(mContext,site.getIdSite())){
                  Toast.makeText(v.getContext(), "Éxito al eliminar", Toast.LENGTH_SHORT).show();
                  ((MenuActivity) mContext.getActivity()).preRouteList.remove(position);
                  ModifyRouteFragment modifyRoutesFragment = new ModifyRouteFragment();
                  FragmentTransaction ft = mContext.getActivity().getSupportFragmentManager().beginTransaction();
                  ft.replace(R.id.frame, modifyRoutesFragment, "modifyRouteFragment");
                  ft.addToBackStack("modifyRouteFragment");
                  ft.commit();

              }else{
                  Toast.makeText(v.getContext(), "Error al eliminar", Toast.LENGTH_SHORT).show();
              }



            }

        });


    }

    @Override
    public int getItemCount() {
        return deleteSiteList.size();
    }



    private boolean deleteSite(final Fragment context, int idSite){

        RequestQueue queue = Volley.newRequestQueue(context.getActivity());
        final String URL = "http://rutascr.esy.es/WebServices/predesignedroutes/"+idSite;



        JsonObjectRequest request_json = new JsonObjectRequest(URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        flag = deleteSuccess(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                flag = false;
            }
        });


        int socketTimeout = 15000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request_json.setRetryPolicy(policy);
        queue.add(request_json);

        return flag;

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

