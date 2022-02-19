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
public class Location {
    private int locationId;
    private String streetAddress, postalCode, city, stateProvince, country;

    public Location(String streetAddress, String postalCode, String city, String stateProvince, String country) {
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.city = city;
        this.stateProvince = stateProvince;
        this.country = country;
    }
    
    public Location(int locationId, String streetAddress, String postalCode, String city, String stateProvince, String country) {
        this(streetAddress, postalCode, city, stateProvince, country);
        this.locationId = locationId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Location{" + "locationId=" + locationId + ", streetAddress=" + streetAddress + ", postal_code=" + postalCode + ", city=" + city + ", state_province=" + stateProvince + ", country=" + country + '}';
    }
}
