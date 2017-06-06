package com.mjb.projectexperts;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mjb.projectexperts.Domain.Route;

import java.util.ArrayList;

import layout.MapFragment;
public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.MyViewHolder> {


    private Fragment mContext;
    private ArrayList<Route> routeList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txvNameRoute, txvDescriptionRoute;
        public ImageView imcRoute;
        //public Button btnSearch;

        public MyViewHolder(View view) {
            super(view);
            txvNameRoute = (TextView) view.findViewById(R.id.txvNameRoute);
            txvDescriptionRoute = (TextView) view.findViewById(R.id.txvDescriptionRoute);
            imcRoute = (ImageView) view.findViewById(R.id.imc_route);
            //btnSearch = (Button) view.findViewById(R.id.btn_view);
        }
    }


    public RouteAdapter(Fragment mContext, ArrayList<Route> phoneList) {
        this.mContext = mContext;
        this.routeList = phoneList;
    }



    @Override
    public RouteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.routes_card, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final RouteAdapter.MyViewHolder holder, int position) {
        final Route route = routeList.get(position);
        holder.txvNameRoute.setText(route.getNameRoute());
        holder.txvDescriptionRoute.setText(route.getDescriptionRoute());

        try {
            Glide.with(mContext).load(route.getImage()).into(holder.imcRoute);

        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.imcRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MapFragment mapFragment = new MapFragment();
                FragmentTransaction ft = mContext.getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, mapFragment, "mapFragment");
                ft.addToBackStack("mapFragment");
                ft.commit();

            }

        });


    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }





}
