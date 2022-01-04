package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Office implements Runnable{
    private final List<Tour> tours;
    private final List<Guide> guides;
    private final List<Tourist> participants;

    private ServerSocket server;

    public Office() {
        tours = new ArrayList<>();
        guides = new ArrayList<>();
        participants = new ArrayList<>();
    }

    public void startServer() {
        try{
            this.server = new ServerSocket(4000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }

    public void addTourOffer(String tour) {

    }

    public void removeTourOffer(String tour) {

    }

    public void registerForTour(String tour, String tourist) {

    }

    public void unregisterFromTour(String tour, String tourist) {

    }

    public void getTourOffers() {

    }

    public void addGuide(String guide) {

    }

    public void removeGuide(String guide) {

    }

    public void updateTourInfo(String tourInfo) {

    }
}
