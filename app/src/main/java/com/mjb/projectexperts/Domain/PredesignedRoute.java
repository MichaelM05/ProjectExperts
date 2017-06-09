package com.mjb.projectexperts.Domain;

import java.util.ArrayList;

/**
 * Created by mm on 09/06/2017.
 */
public class PredesignedRoute {



    private String routename;
    private int user;
    private ArrayList<Integer> places;

    public PredesignedRoute(String routename, int user, ArrayList<Integer> places) {
        this.routename = routename;
        this.user = user;
        this.places = places;
    }


    public ArrayList<Integer> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Integer> places) {
        this.places = places;
    }

    public String getRoutename() {
        return routename;
    }

    public void setRoutename(String routename) {
        this.routename = routename;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }





}
