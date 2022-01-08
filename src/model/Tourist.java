package model;

import exceptions.TouristAlreadyParticipatesException;
import exceptions.TouristNotParticipatingException;

import java.util.ArrayList;
import java.util.List;

public class Tourist extends Person {
    private final List<Tour> toursParticipated;

    public Tourist(String name, String surname) {
        super(name, surname);
        toursParticipated = new ArrayList<>();
    }

    public List<Tour> getToursParticipated() {
        return toursParticipated;
    }

    public void setToursParticipated(Tour tour) throws TouristAlreadyParticipatesException {
        for (Tour tourParticipated : toursParticipated) {
            if (tour.equals(tourParticipated)) {
                throw new TouristAlreadyParticipatesException("Already participating in tour: " + tour.getName());
            }
        }
        toursParticipated.add(tour);
    }

    public void removeTourParticipated(Tour tour) throws TouristNotParticipatingException {
        for (Tour tourParticipated : toursParticipated) {
            if (tourParticipated.equals(tour)){
                toursParticipated.remove(tourParticipated);
                return;
            }
        }
        throw new TouristNotParticipatingException("Not participating in tour: " + tour.getName());
    }
}
