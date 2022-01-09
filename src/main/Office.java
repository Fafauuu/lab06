package main;

import exceptions.TouristAlreadyParticipatesException;
import exceptions.TouristNotParticipatingException;
import model.Guide;
import model.Tour;
import model.Tourist;
import utils.DatabaseHandler;
import utils.JsonHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Office {
    private List<Tour> tourOffers;
    private List<Guide> guides;
    private List<Tourist> tourists;
    private static Socket clientSocket;
    PrintWriter outputToClient;
    BufferedReader inputFromClient;
    PrintWriter outputToGuide;

    private static final String SERVER_SOCKET_HOST = "127.0.0.1";
    private static final int SERVER_SOCKET_PORT = 4002;
    private static ServerSocket serverSocket;

    public Office() {
        initializeData();
    }

    private void initializeData() {
        this.tourOffers = DatabaseHandler.readTourList("tourOffers.json");
        this.guides = DatabaseHandler.readGuideList("guides.json");
        this.tourists = DatabaseHandler.readTouristList("tourists.json");
    }

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(SERVER_SOCKET_PORT);
            System.out.println("Server started");
            Thread thread = new Thread(() -> {
                boolean stop = false;
                while (!stop) {
                    try {
                        clientSocket = serverSocket.accept();
                    } catch (IOException e) {
                        stop = true;
                        e.printStackTrace();
                    }
                    new Office().startServerThread(serverSocket);
                }
            });
            thread.start();
        } catch (IOException e) {
            System.out.println("cant start server");
        }
    }

    public void startServerThread(ServerSocket serverSocket) {
        Thread thread = new Thread(() -> {
            try {
                System.out.println("socket server local port: " + serverSocket.getLocalPort());
                System.out.println("Socket local port: " + clientSocket.getPort());
                outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
                inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String clientInput;
                while (true) {
                    try {
                        clientInput = this.inputFromClient.readLine();
                        System.out.println("input from client: " + clientInput);
                        if (clientInput.contains("getTourOffers:")) {
                            String responseToClient = getTourOffers();
                            outputToClient.println(responseToClient);
                        }
                        else if (clientInput.contains("addTourOffer:")) {
                            String responseToClient = addTourOffer(clientInput);
                            outputToClient.println(responseToClient);
                            DatabaseHandler.saveTourList("tourOffers.json", tourOffers);
                        }
                        else if (clientInput.contains("removeTourOffer:")) {
                            String responseToClient = removeTourOffer(clientInput);
                            outputToClient.println(responseToClient);
                            DatabaseHandler.saveTourList("tourOffers.json", tourOffers);
                            DatabaseHandler.saveTouristList("tourists.json", tourists);
                        }
                        else if (clientInput.contains("registerForTour:")) {
                            guides = DatabaseHandler.readGuideList("guides.json");
                            String responseToClient = registerForTour(clientInput);
                            outputToClient.println(responseToClient);
                            DatabaseHandler.saveTourList("tourOffers.json", tourOffers);
                            DatabaseHandler.saveTouristList("tourists.json", tourists);
                        }
                        else if (clientInput.contains("unregisterFromTour:")) {
                            String responseToClient = unregisterFromTour(clientInput);
                            outputToClient.println(responseToClient);
                            DatabaseHandler.saveTourList("tourOffers.json", tourOffers);
                            DatabaseHandler.saveTouristList("tourists.json", tourists);
                        }
                        else if (clientInput.contains("addGuide:")) {
                            guides = DatabaseHandler.readGuideList("guides.json");
                            tourOffers = DatabaseHandler.readTourList("tourOffers.json");
                            addGuide(clientInput);
                            DatabaseHandler.saveGuideList("guides.json", guides);
                        }
                        else if (clientInput.contains("removeGuide:")) {
                            guides = DatabaseHandler.readGuideList("guides.json");
                            removeGuide(clientInput);
                            DatabaseHandler.saveGuideList("guides.json", guides);
                        }
                    } catch (IOException e) {
                        stopServer();
                    }
                }
            } catch (IOException e) {
                stopServer();
            }
        });
        thread.start();
    }

    public void stopServer() {
        try {
            inputFromClient.close();
            outputToClient.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String addTourOffer(String input) {
        String jsonTourOffer = input.substring(input.indexOf(":") + 1);
        Tour tourOffer;
        try {
            tourOffer = JsonHandler.jsonToTour(jsonTourOffer);
        } catch (Exception e) {
            return "Wrong tour data type";
        }
        for (Tour offer : tourOffers) {
            if (offer.equals(tourOffer)) return "Tour offer " + tourOffer.getName() + " already exists";
        }
        tourOffers.add(tourOffer);
        return "Tour offer \"" + tourOffer.getName() + "\" added successfully";
    }

    private String removeTourOffer(String input) {
        String jsonTourOffer = input.substring(input.indexOf(":") + 1);
        Tour tourOffer = JsonHandler.jsonToTour(jsonTourOffer);
        System.out.println(tourOffers);
        for (Tour offer : tourOffers) {
            if (offer.equals(tourOffer)) {
                for (Tourist tourist : tourists) {
                    if (tourist.getToursParticipated().contains(offer)) {
                        try {
                            tourist.removeTourParticipated(offer);
                        } catch (TouristNotParticipatingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                tourOffers.remove(offer);
                return "Tour offer \"" + tourOffer.getName() + "\" removed successfully";
            }
        }
        return "Tour offer " + tourOffer.getName() + " doesnt exist";
    }

    private String getTourOffers() {
        //TODO: remove hard reading data
        tourOffers = DatabaseHandler.readTourList("tourOffers.json");
        return "tourOffers:" + JsonHandler.tourListToJson(tourOffers);
    }

    private String registerForTour(String input) {
        Object[] data = parseRegistrationData(input);
        Tourist tourist = (Tourist) data[0];
        Tour tour = (Tour) data[1];
        for (Tour tourAvailable : tourOffers) {
            if (tourAvailable.equals(tour)) {
                for (Tourist touristAvailable : tourists) {
                    if (touristAvailable.equals(tourist)) {
                        return tryToRegister(touristAvailable, tourAvailable);
                    }
                }
                tourists.add(tourist);
                return tryToRegister(tourist, tourAvailable);
            }
        }
        return "Registration failed";
    }

    private String tryToRegister(Tourist tourist, Tour tour) {
        Guide tourGuide = null;
        for (Guide guide : guides) {
            if (guide.getTourGuided().equals(tour)){
                tourGuide = guide;
            }
        }
        if (tourGuide == null) {
            return "there is no guide of tour: " + tour.getName();
        }
        try {
            tourist.setToursParticipated(tour);
            tour.removeSpotAvailable();
        } catch (TouristAlreadyParticipatesException e) {
            return tourist.getName() + " "
                    + tourist.getSurname() + " already participates in this tour";
        }
        updateTourInfo(tour, tourGuide.getHost(), tourGuide.getPort(), null);
        return tourist.getName()
                + " "
                + tourist.getSurname()
                + " successfully registered for tour: \""
                + tour.getName()
                + "\"";
    }

    private String unregisterFromTour(String input) {
        Object[] data = parseRegistrationData(input);
        Tourist tourist = (Tourist) data[0];
        Tour tour = (Tour) data[1];
        for (Tour tourAvailable : tourOffers) {
            System.out.println("start");
            System.out.println(tourAvailable);
            System.out.println(tour);
            if (tourAvailable.equals(tour)) {
                System.out.println("got tour");
                for (Tourist touristAvailable : tourists) {
                    if (touristAvailable.equals(tourist)) {
                        try {
                            touristAvailable.removeTourParticipated(tourAvailable);
                            tourAvailable.addSpotAvailable();
                        } catch (TouristNotParticipatingException e) {
                            return e.getMessage();
                        }
                        for (Guide guide : guides) {
                            if (guide.getTourGuided().equals(tourAvailable)){
                                updateTourInfo(tourAvailable, guide.getHost(), guide.getPort(), null);
                            }
                        }
                        return touristAvailable.getName()
                                + " "
                                + touristAvailable.getSurname()
                                + " successfully unregistered from tour: \""
                                + tourAvailable.getName();
                    }
                }
                return tourist.getName()
                        + " "
                        + tourist.getSurname()
                        + "not registered for tour: \""
                        + tour.getName()
                        + "\"";
            }
        }
        return "Unregistration failed";
    }

    private Object[] parseRegistrationData(String input) {
        Object[] output = new Object[2];
        String jsonInput = input.substring(input.indexOf(":") + 1);
        String[] parts = jsonInput.split("&");
        String jsonTourist = parts[0];
        String jsonTour = parts[1];
        output[0] = JsonHandler.jsonToTourist(jsonTourist);
        output[1] = JsonHandler.jsonToTour(jsonTour);
        return output;
    }

    private void addGuide(String input) {
        Object[] data = parseGuideData(input);
        Guide guide = (Guide) data[0];
        String guideHost = (String) data[1];
        Integer guidePort = (Integer) data[2];
        Guide newGuide = new Guide(guide.getName(), guide.getSurname());
        for (Guide guideOnList : guides) {
            if (guideOnList.equals(guide)){
                newGuide = guideOnList;
            }
        }
        if (!guides.contains(newGuide)){
            guides.add(newGuide);
        }
        newGuide.setHost(guideHost);
        newGuide.setPort(guidePort);
        if (newGuide.getTourGuided() != null) {
            updateTourInfo(newGuide.getTourGuided(), newGuide.getHost(), newGuide.getPort()
                    , "you are already assigned to a tour");
            return;
        }
        for (Tour tourOffer : tourOffers) {
            boolean tourGuided = false;
            for (Guide guideOnList : guides) {
                if (guideOnList.getTourGuided() != null && guideOnList.getTourGuided().equals(tourOffer)) {
                    tourGuided = true;
                }
            }
            if (!tourGuided) {
                newGuide.setTourGuided(tourOffer);
                System.out.println("added to tour: " + tourOffer.getName());
                updateTourInfo(newGuide.getTourGuided(), newGuide.getHost(), newGuide.getPort(),
                        null);
                return;
            }
        }
        updateTourInfo(newGuide.getTourGuided(), newGuide.getHost(), newGuide.getPort(),
                "there is no tour to assign");
    }

    private void removeGuide(String input) {
        Object[] data = parseGuideData(input);
        Guide guide = (Guide) data[0];
        String guideHost = (String) data[1];
        Integer guidePort = (Integer) data[2];
        Guide newGuide = new Guide(guide.getName(), guide.getSurname());
        if (!guides.contains(newGuide)){
            updateTourInfo(newGuide.getTourGuided(), guideHost, guidePort,
                    "you are not added to database");
            return;
        }
        for (Guide guideOnList : guides) {
            if (guideOnList.equals(guide)){
                guideOnList.removeTourGuided();
                updateTourInfo(newGuide.getTourGuided(), guideHost, guidePort,
                        newGuide.getName() + " " + newGuide.getSurname() + " unassigned from tour");
                return;
            }
        }
    }

    private void updateTourInfo(Tour tour, String host, Integer port, String otherMessage) {
        try {
            Socket guideSocket = new Socket(host, port);
            outputToGuide = new PrintWriter(guideSocket.getOutputStream(), true);
            if (otherMessage == null){
                outputToGuide.println("updateTourInfo:" + JsonHandler.tourToJson(tour));
            } else outputToGuide.println(otherMessage);
            guideSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object[] parseGuideData(String input) {
        Object[] output = new Object[3];
        String jsonInput = input.substring(input.indexOf(":") + 1);
        String[] parts = jsonInput.split("&");
        String jsonGuide = parts[0];
        output[0] = JsonHandler.jsonToGuide(jsonGuide);
        output[1] = parts[1];
        output[2] = Integer.parseInt(parts[2]);
        return output;
    }

    public static String getServerSocketHost() {
        return SERVER_SOCKET_HOST;
    }

    public static int getServerSocketPort() {
        return SERVER_SOCKET_PORT;
    }
}
