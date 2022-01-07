package model;

import java.time.LocalDate;
import java.util.Objects;

public class Tour {
    private final String name;
    private String description;
    private LocalDate date;
    private final int spots;
    private int spotsAvailable;

    public Tour(String name, int spots) {
        this.name = name;
        this.spots = spots;
        this.spotsAvailable = spots;
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

    public void removeSpotAvailable() {
        this.spotsAvailable--;
    }

    public void addSpotAvailable() {
        this.spotsAvailable++;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return Objects.equals(name, tour.name) && Objects.equals(description, tour.description) && Objects.equals(date, tour.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, date);
    }
}
