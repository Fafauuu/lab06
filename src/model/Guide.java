package model;

public class Guide extends Person {
    private Tour tourGuided;
    private String host;
    private int port;

    public Guide(String name, String surname) {
        super(name, surname);
    }

    public Tour getTourGuided() {
        return tourGuided;
    }

    public void setTourGuided(Tour tourGuided) {
        this.tourGuided = tourGuided;
    }

    public void removeTourGuided(){
        this.tourGuided = null;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
