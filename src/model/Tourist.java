package model;

import terminals.touristTerminal.TouristTerminal;

public class Tourist extends Person {
    private Tour tourParticipated;
    private TouristTerminal touristTerminal;

    public Tourist(String name, String surname) {
        super(name, surname);
    }

    public Tour getTourParticipated() {
        return tourParticipated;
    }

    public void setTourParticipated(Tour tourParticipated) {
        this.tourParticipated = tourParticipated;
    }
}
