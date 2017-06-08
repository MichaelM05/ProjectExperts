package com.mjb.projectexperts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mjb.projectexperts.Domain.Route;

import java.util.ArrayList;

/**
 * Created by mm on 03/05/2017.
 */
public class DeleteSiteAdapter extends RecyclerView.Adapter<DeleteSiteAdapter.MyViewHolder> {


    private Context mContext;
    private ArrayList<Route> deleteSiteList;
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


    public DeleteSiteAdapter(Context mContext, ArrayList<Route> siteList) {
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
    public void onBindViewHolder(final DeleteSiteAdapter.MyViewHolder holder, int position) {
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



            }

        });


    }

    @Override
    public int getItemCount() {
        return deleteSiteList.size();
    }





}

