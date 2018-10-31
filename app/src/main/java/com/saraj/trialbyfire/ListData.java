package com.saraj.trialbyfire;

import java.util.List;

public class ListData {
    private String title;
    private int year;
    private List<String> genres;
    private List<String> cast;

    public ListData() {
    }

    public ListData(String title, int year, List<String> genres, List<String> cast) {
        this.title = title;
        this.year = year;
        this.genres = genres;
        this.cast = cast;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }
}
