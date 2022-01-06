package model;

import exceptions.InvalidSpotsAmountException;

import java.time.LocalDate;

public class Tour {
    private final String name;
    private String description;
    private LocalDate date;
    private int spots;
    private int spotsAvailable;

    public Tour(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getSpots() {
        return spots;
    }

    public void setSpots(int spots) {
        this.spots = spots;
    }

    public int getSpotsAvailable() {
        return spotsAvailable;
    }

    public void setSpotsAvailable(int spotsAvailable){
        if (spotsAvailable > spots) {
            throw new InvalidSpotsAmountException(
                    "SpotsAvailable: " + spotsAvailable + " is greater then overall spots: " + spots);
        }
        this.spotsAvailable = spotsAvailable;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", spots=" + spots +
                ", spotsAvailable=" + spotsAvailable +
                '}';
    }
}
