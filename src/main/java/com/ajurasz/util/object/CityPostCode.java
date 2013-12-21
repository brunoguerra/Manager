package com.ajurasz.util.object;

import java.io.Serializable;

/**
 * @author Arek Jurasz
 */
public class CityPostCode implements Serializable {
    private String city;
    private String postCode;

    public CityPostCode() {}

    public CityPostCode(String city, String postCode) {
        this.city = city;
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
