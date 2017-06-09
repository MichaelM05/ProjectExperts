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

/**
 * Created by mm on 03/05/2017.
 */
public class AddSiteAdapter extends RecyclerView.Adapter<AddSiteAdapter.MyViewHolder> {


    private Fragment mContext;
    private ArrayList<Route> routeList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txvNameRoute;
        public ImageView imcRoute;
        public Button btnAdd;

        public MyViewHolder(View view) {
            super(view);
            txvNameRoute = (TextView) view.findViewById(R.id.txvNameRoute);
            imcRoute = (ImageView) view.findViewById(R.id.imc_route);
            btnAdd = (Button) view.findViewById(R.id.btn_add);
        }
    }


    public AddSiteAdapter(Fragment mContext, ArrayList<Route> phoneList) {
        this.mContext = mContext;
        this.routeList = phoneList;
    }



    @Override
    public AddSiteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_site_card, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final AddSiteAdapter.MyViewHolder holder, int position) {
        final Route route = routeList.get(position);
        holder.txvNameRoute.setText(route.getNameRoute());

        try {
            Glide.with(mContext).load(route.getSites().get(0).getImagesSite().get(0)).into(holder.imcRoute);

        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ((MenuActivity) mContext.getActivity()).sites.add(route);


                ModifyRouteFragment modifyRoutesFragment = new ModifyRouteFragment();
                FragmentTransaction ft = mContext.getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, modifyRoutesFragment, "modifyRouteFragment");
                ft.addToBackStack("modifyRouteFragment");
                ft.commit();

            }

        });


    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }





}
