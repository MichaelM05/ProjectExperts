package com.mjb.projectexperts.Domain;

import java.util.ArrayList;

/**
 * Created by mm on 03/05/2017.
 */
public class Route {

    private String nameRoute,descriptionRoute;
    private ArrayList<Site> sites;

    public Route() {
    }

    public Route(String nameRoute,String descriptionRoute, ArrayList<Site> sites) {
        this.nameRoute = nameRoute;
        this.descriptionRoute = descriptionRoute;
        this.sites = sites;
    }

    public String getNameRoute() {
        return nameRoute;
    }

    public void setNameRoute(String nameRoute) {
        this.nameRoute = nameRoute;
    }

    public ArrayList<Site> getSites() {
        return sites;
    }

    public void setSites(ArrayList<Site> sites) {
        this.sites = sites;
    }

    public String getDescriptionRoute() {
        return descriptionRoute;
    }

    public void setDescriptionRoute(String descriptionRoute) {
        this.descriptionRoute = descriptionRoute;
    }
}
