package com.mjb.projectexperts;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import layout.ModifyRouteFragment;

public class RouteModifyAdapter extends RecyclerView.Adapter<RouteModifyAdapter.MyViewHolder> {


    private Fragment mContext;
    private ArrayList<Route> routeList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txvNameRoute, txvDescriptionRoute;
        public ImageView imcRoute;
        public Button btnSearch;

        public MyViewHolder(View view) {
            super(view);
            txvNameRoute = (TextView) view.findViewById(R.id.txvNameRoute);
            txvDescriptionRoute = (TextView) view.findViewById(R.id.txvDescriptionRoute);
            imcRoute = (ImageView) view.findViewById(R.id.imc_route);
            btnSearch = (Button) view.findViewById(R.id.btn_view);
        }
    }


    public RouteModifyAdapter(Fragment mContext, ArrayList<Route> phoneList) {
        this.mContext = mContext;
        this.routeList = phoneList;
    }



    @Override
    public RouteModifyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.routes_card, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final RouteModifyAdapter.MyViewHolder holder, int position) {
        final Route route = routeList.get(position);
        holder.txvNameRoute.setText(route.getNameRoute());
        holder.txvDescriptionRoute.setText(route.getDescriptionRoute());

        try {
            Glide.with(mContext).load(route.getImage()).into(holder.imcRoute);

        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ModifyRouteFragment modifyRouteFragment = new ModifyRouteFragment();
                FragmentTransaction ft = mContext.getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, modifyRouteFragment, "modifyRouteFragment");
                ft.commit();

            }

        });


    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }





}
