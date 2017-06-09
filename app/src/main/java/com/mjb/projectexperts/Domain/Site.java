package com.mjb.projectexperts.Domain;

import java.util.ArrayList;

/**
 * Created by Brayan on 07/06/2017.
 */

public class Site {

    private int idSite,priceSite;
    private String nameSite,descriptionSite,latSite,lengSite,
            typeActivity,pathVideo;
    private ArrayList<String> imagesSite;

    private ArrayList<String> videos;

    public Site() {
        this.descriptionSite = "";
        this.idSite = 0;
        this.latSite = "";
        this.lengSite = "";
        this.nameSite = "";
        this.priceSite = 0;
        this.typeActivity = "";
        this.imagesSite = new ArrayList<>();
    }

    public Site(String descriptionSite, int idSite, ArrayList<String> imagesSite, String latSite, String lengSite, String nameSite, int priceSite, String typeActivity) {
        this.descriptionSite = descriptionSite;
        this.idSite = idSite;
        this.imagesSite = imagesSite;
        this.latSite = latSite;
        this.lengSite = lengSite;
        this.nameSite = nameSite;
        this.priceSite = priceSite;
        this.typeActivity = typeActivity;
    }

    public String getDescriptionSite() {
        return descriptionSite;
    }

    public void setDescriptionSite(String descriptionSite) {
        this.descriptionSite = descriptionSite;
    }
    public ArrayList<String> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<String> videos) {
        this.videos = videos;
    }

    public int getIdSite() {
        return idSite;
    }

    public void setIdSite(int idSite) {
        this.idSite = idSite;
    }

    public ArrayList<String> getImagesSite() {
        return imagesSite;
    }

    public void setImagesSite(ArrayList<String> imagesSite) {
        this.imagesSite = imagesSite;
    }

    public String getLatSite() {
        return latSite;
    }

    public void setLatSite(String latSite) {
        this.latSite = latSite;
    }

    public String getLengSite() {
        return lengSite;
    }

    public void setLengSite(String lengSite) {
        this.lengSite = lengSite;
    }

    public String getNameSite() {
        return nameSite;
    }

    public void setNameSite(String nameSite) {
        this.nameSite = nameSite;
    }

    public int getPriceSite() {
        return priceSite;
    }

    public void setPriceSite(int priceSite) {
        this.priceSite = priceSite;
    }

    public String getTypeActivity() {
        return typeActivity;
    }

    public void setTypeActivity(String typeActivity) {
        this.typeActivity = typeActivity;
    }

    public String getPathVideo() {
        return pathVideo;
    }

    public void setPathVideo(String pathVideo) {
        this.pathVideo = pathVideo;
    }
}
