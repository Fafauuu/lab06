package model;

import terminals.GuideTerminal;

public class Guide extends Person {
    private Tour tourGuided;
    private GuideTerminal guideTerminal;

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
