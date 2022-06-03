package com.example.richbroken.utils;

public class GiphyQueryParams {
    private String api_key;
    private String q;
    private int limit;
    private int offset;
    private String rating;
    private String lang ;


    public GiphyQueryParams(String api_key, String q, int limit, int offset, String rating, String lang) {
        this.api_key = api_key;
        this.q = q;
        this.limit = limit;
        this.offset = offset;
        this.rating = rating;
        this.lang = lang;
    }

    public GiphyQueryParams(String api_key, String q) {
        this.api_key = api_key;
        this.q = q;
    }

    public GiphyQueryParams() {
    }

    public String getApi_key() {
        return api_key;
    }

    public String getQ() {
        return q;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public String getRating() {
        return rating;
    }

    public String getLang() {
        return lang;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiphyQueryParams)) return false;

        GiphyQueryParams that = (GiphyQueryParams) o;

        return q.equals(that.q);
    }

}
