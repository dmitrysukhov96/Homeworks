package com.dmitrysukhov.locationmonitor;

public class MyLocation {
    String coordinates, address;

    public MyLocation(String address, String coordinates) {
        this.address = address;
        this.coordinates = coordinates;
    }

    public String getAddress() {
        return address;
    }

    public String getCoordinates() {
        return coordinates;
    }

}