package com.aravind.popularmovies2.model;

/**
 * This class encapsulates Youtube trailer details
 */
public class YTBTrailer {

    private String id;
    private String name;
    private String key;

    public static String getUrl(YTBTrailer t) {
        return String.format("http://www.youtube.com/watch?v=%1$s", t.getKey());

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
