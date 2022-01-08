package model;

import exceptions.TouristAlreadyParticipatesException;
import exceptions.TouristNotParticipatingException;
import utils.DatabaseHandler;
import utils.JsonHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Office {
    private List<Tour> tourOffers;
    private List<Guide> guides;
    private List<Tourist> tourists;
    private Socket clientSocket;
    PrintWriter outputToClient;
    BufferedReader inputFromClient;

    private static final String SERVER_SOCKET_HOST = "127.0.0.1";
    private static final int SERVER_SOCKET_PORT = 4002;
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(SERVER_SOCKET_PORT);
            for (int i = 0; i < 5; i++) {
                new Thread(() -> {
                    try {
                        new Office().startServer(serverSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            System.out.println("cant start server");
        }

//        OfficeWindow officeFrame =  new OfficeWindow();
//        officeFrame.setOfficeFrameListener(office);
//        officeFrame.requestFocusInWindow();

    }

    public Office() {
        initializeData();
    }

    private void initializeData() {
        this.tourOffers = DatabaseHandler.readTourList("tourOffers.json");
        this.guides = new ArrayList<>();
        this.tourists = DatabaseHandler.readTouristList("tourists.json");
    }

    private void saveData() {
        DatabaseHandler.saveTourList("tourOffers.json", tourOffers);
        DatabaseHandler.saveTouristList("tourists.json", tourists);
    }

    public void startServer(ServerSocket serverSocket) throws IOException {
        System.out.println("socket server local port: " +  serverSocket.getLocalPort());
        clientSocket = serverSocket.accept();
        System.out.println("Socket local port: " + clientSocket.getPort());
        outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);
        inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputFromClient;
        while(true) {
            try {
                inputFromClient= this.inputFromClient.readLine();
                System.out.println("input from client: " + inputFromClient);
                if (inputFromClient.contains("getTourOffers:")) {
                    String responseToClient = getTourOffers();
                    outputToClient.println(responseToClient);
                }
                if (inputFromClient.contains("addTourOffer:")) {
                    String responseToClient = addTourOffer(inputFromClient);
                    outputToClient.println(responseToClient);
                    DatabaseHandler.saveTourList("tourOffers.json", tourOffers);
                }
                if (inputFromClient.contains("removeTourOffer:")) {
                    String responseToClient = removeTourOffer(inputFromClient);
                    outputToClient.println(responseToClient);
                    DatabaseHandler.saveTourList("tourOffers.json", tourOffers);
                    DatabaseHandler.saveTouristList("tourists.json", tourists);
                }
                if (inputFromClient.contains("registerForTour:")) {
                    String responseToClient = registerForTour(inputFromClient);
                    outputToClient.println(responseToClient);
                    DatabaseHandler.saveTourList("tourOffers.json", tourOffers);
                    DatabaseHandler.saveTouristList("tourists.json", tourists);
                }
                if (inputFromClient.contains("unregisterFromTour:")) {
                    String responseToClient = unregisterFromTour(inputFromClient);
                    outputToClient.println(responseToClient);
                    DatabaseHandler.saveTourList("tourOffers.json", tourOffers);
                    DatabaseHandler.saveTouristList("tourists.json", tourists);
                }
            } catch (IOException e) {
                stopServer();
            }
        }
    }


    public void stopServer(){
        try {
            inputFromClient.close();
            outputToClient.close();
            clientSocket.close();
            serverSocket.close();
//            this.saveData();
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
            if(offer.equals(tourOffer)) return "Tour offer " + tourOffer.getName() + " already exists";
        }
        tourOffers.add(tourOffer);
        return "Tour offer \"" + tourOffer.getName() + "\" added successfully";
    }

    private String removeTourOffer(String input) {
        String jsonTourOffer = input.substring(input.indexOf(":") + 1);
        Tour tourOffer = JsonHandler.jsonToTour(jsonTourOffer);
        System.out.println(tourOffers);
        for (Tour offer : tourOffers) {
            if (offer.equals(tourOffer)){
                for (Tourist tourist : tourists) {
                    if (tourist.getToursParticipated().contains(offer)){
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
        Tourist tourist = (Tourist) parseRegistrationData(input)[0];
        Tour tour = (Tour) parseRegistrationData(input)[1];
        for (Tour tourAvailable : tourOffers) {
            if (tourAvailable.equals(tour)) {
                for (Tourist touristAvailable : tourists) {
                    if (touristAvailable.equals(tourist)){
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
        try {
            tourist.setToursParticipated(tour);
            tour.removeSpotAvailable();
        } catch (TouristAlreadyParticipatesException e) {
            return tourist.getName() + " "
                    + tourist.getSurname() + " already participates in this tour";
        }
        return tourist.getName()
                + " "
                + tourist.getSurname()
                + " successfully registered for tour: \""
                + tour.getName()
                + "\"";
    }

    private String unregisterFromTour(String input) {
        Tourist tourist = (Tourist) parseRegistrationData(input)[0];
        Tour tour = (Tour) parseRegistrationData(input)[1];


//        tourOffers = DatabaseHandler.readTourList("tourOffers.json");


        for (Tour tourAvailable : tourOffers) {
            if (tourAvailable.equals(tour)) {
                for (Tourist touristAvailable : tourists) {
                    if (touristAvailable.equals(tourist)){
                        try {
                            touristAvailable.removeTourParticipated(tourAvailable);
                            tourAvailable.addSpotAvailable();
                        } catch (TouristNotParticipatingException e) {
                            return e.getMessage();
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

    private void updateTourInfo(String tourInfo) {

    }

    private void addGuide(String guideInfo, String host, String port) {

    }

    private void removeGuide(String guideInfo, String host, String port) {

    }

    public static String getServerSocketHost() {
        return SERVER_SOCKET_HOST;
    }

    public static int getServerSocketPort() {
        return SERVER_SOCKET_PORT;
    }
}
