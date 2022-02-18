/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author RAI
 */
public class Country {
    
    private String countryId, name;
    private int region;

    public Country(String countryId, String name, int region) {
        this.countryId = countryId;
        this.name = name;
        this.region = region;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Country{" + "countryId=" + countryId + ", name=" + name + ", region=" + region + '}';
    }
}
