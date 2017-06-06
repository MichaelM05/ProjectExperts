package com.mjb.projectexperts.Domain;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mm on 03/05/2017.
 */
public class Route {

    private String nameRoute;
    private String descriptionRoute;
    private String image;

    public Route(String nameRoute, String descriptionRoute, String image) {
        this.nameRoute = nameRoute;
        this.descriptionRoute = descriptionRoute;
        this.image = image;
    }

    public Route(JSONObject json){

        try {
            this.nameRoute = json.getString("name");
            this.descriptionRoute = json.getString("desciption");
            this.image = json.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public String getNameRoute() {
        return nameRoute;
    }

    public void setNameRoute(String nameRoute) {
        this.nameRoute = nameRoute;
    }

    public String getDescriptionRoute() {
        return descriptionRoute;
    }

    public void setDescriptionRoute(String descriptionRoute) {
        this.descriptionRoute = descriptionRoute;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
