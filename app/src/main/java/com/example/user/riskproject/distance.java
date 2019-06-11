package com.example.user.riskproject;

import java.util.ArrayList;

public class distance {
    private String from = "", to = "";
    private double distance;

    public distance(String from, String to, double distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getdistancefromstrings(String from, String to, ArrayList<distance> distances) {
        for (int i = 0; i < distances.size(); i++) {
            if (distances.get(i).getFrom().equalsIgnoreCase(from) && distances.get(i).getTo().equalsIgnoreCase(to)) {
                return distances.get(i).getDistance();
            }
        }

        return Double.parseDouble(null);
    }
}