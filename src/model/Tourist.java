package model;

public class Tourist extends Person {
    private Tour tourParticipated;

    public Tourist(String name, String surname) {
        super(name, surname);
        tourParticipated = null;
    }

    public Tour getTourParticipated() {
        return tourParticipated;
    }

    public void setTourParticipated(Tour tourParticipated) {
        this.tourParticipated = tourParticipated;
    }

    @Override
    public String toString() {
        return "Tourist{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
//                ", tourParticipated=" + tourParticipated.getName() +
                '}';
    }
}
