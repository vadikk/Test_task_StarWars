package com.example.vadym.starwars.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vadym on 14.03.2018.
 */

public class StarWarResponce {

    private String count;
    private String next;
    @SerializedName("results")
    private List<StarWar> listStar = new ArrayList<>();

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<StarWar> getListStar() {
        return listStar;
    }

    public void setListStar(List<StarWar> listStar) {
        if (listStar != null) {
            this.listStar.addAll(listStar);
        }
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
