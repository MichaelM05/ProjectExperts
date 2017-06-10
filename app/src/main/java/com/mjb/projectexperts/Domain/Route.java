package com.mjb.projectexperts.Domain;

import java.util.ArrayList;

/**
 * Created by mm on 03/05/2017.
 */
public class Route {

    private String nameRoute,descriptionRoute;
    private String idUser;



    private int idRoute;
    private ArrayList<Site> sites;

    public Route() {
        this.nameRoute = "";
        this.descriptionRoute = "";
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
    public int getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(int idRoute) {
        this.idRoute = idRoute;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
