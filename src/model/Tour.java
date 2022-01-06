package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tour {
    private final String name;
    private String description;
    private LocalDate date;
    private List<Tourist> participants;
    private int spots;
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

    public void addParticipant(Tourist tourist) {
        if (participants == null) {
            participants = new ArrayList<>();
        }
        if (!participants.contains(tourist)) {
            participants.add(tourist);
            spotsAvailable--;
        } else System.out.println(tourist + " already participates in tour: " + this.name);
    }

    public void removeParticipant(Tourist tourist) {
        for (Tourist participant : participants) {
            if (participant.equals(tourist)) {
                participants.remove(participant);
                spotsAvailable++;
            }
        }
    }

    @Override
    public String toString() {
        return "Tour{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
//                ", participants=" + participants +
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
