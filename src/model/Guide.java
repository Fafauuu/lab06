package model;

public class Guide extends Person {
    private Tour tourGuided;

    public Guide(String name, String surname) {
        super(name, surname);
    }

    public Tour getTourGuided() {
        return tourGuided;
    }

    public void setTourGuided(Tour tourGuided) {
        this.tourGuided = tourGuided;
    }
}
