package model;

import exceptions.TouristAlreadyParticipatesException;
import exceptions.TouristNotParticipatingException;
import terminals.OfficeWindow;
import terminals.OfficeWindowListener;
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
    private List<Tour> tours;
    private List<Tour> tourOffers;
    private List<Guide> guides;
    private List<Tourist> tourists;
    private Socket clientSocket;
    private String serverSocketHost;
    PrintWriter outputToClient;
    BufferedReader inputFromClient;

    private int serverSocketPort = 4002;
    private ServerSocket serverSocket = null;

    public static void main(String[] args) {
        Office office = new Office();
        office.startServer();

//        OfficeWindow officeFrame =  new OfficeWindow();
//        officeFrame.setOfficeFrameListener(office);
//        officeFrame.requestFocusInWindow();

        office.saveData();
    }

    public Office() {
        initializeData();

        String jsonTourist = JsonHandler.touristToJson(tourists.get(1));
        String jsonTour = JsonHandler.tourToJson(tours.get(1));
//        System.out.println(jsonTourist);
//        System.out.println(jsonTour);

//        System.out.println(tourists.get(0));
//        System.out.println(tours.get(0));
//        System.out.println(unregisterFromTour("unregisterFromTour:" + jsonTourist + "&" + jsonTour));
//        System.out.println(registerForTour("registerForTour:" + jsonTourist + "&" + jsonTour));
//        System.out.println("after change");
//        System.out.println(tourists.get(0));
//        System.out.println(tours.get(0));
    }

    private void initializeData() {
        this.tours = DatabaseHandler.readTourList("tours.json");
        this.tourOffers = DatabaseHandler.readTourList("tourOffers.json");
        this.guides = new ArrayList<>();
        this.tourists = DatabaseHandler.readTouristList("tourists.json");

//        tourists.add(new Tourist("Ksawery", "Lis"));
//        tourists.add(new Tourist("Antoni", "Kowalski"));
//        tourists.add(new Tourist("Gabriel", "Mazurek"));


//        Tour tour = new Tour("Hawaii trip", 10);
//        tour.setDescription("Hawaii New Year trip");
//        tour.setDate(LocalDate.parse("2022-12-30"));
//
//        Tour tour1 = new Tour("Italy tour", 20);
//        tour1.setDescription("Italy tour across whole country");
//        tour1.setDate(LocalDate.parse("2022-08-25"));
//
//        List<Tour> tours = new ArrayList<>();
//        tours.add(tour);
//        tours.add(tour1);
//
//        DatabaseHandler.saveTourList("tours.json", tours);

    }

    private void saveData() {
        DatabaseHandler.saveTourList("tours.json", tours);
        DatabaseHandler.saveTourList("tourOffers.json", tourOffers);
        DatabaseHandler.saveTouristList("tourists.json", tourists);
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(serverSocketPort);
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
                    }
                } catch (IOException e) {
                    stopServer();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Override
    public void stopServer(){
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
        Tour tourOffer = JsonHandler.jsonToTour(jsonTourOffer);
        for (Tour offer : tourOffers) {
            if(offer.equals(tourOffer)) return "Tour offer " + tourOffer.getName() + " already exists";
        }
        tourOffers.add(tourOffer);
        return "Tour offer \"" + tourOffer.getName() + "\" added successfully";
    }

    private String removeTourOffer(String input) {
        String jsonTourOffer = input.substring(input.indexOf(":") + 1);
        Tour tourOffer = JsonHandler.jsonToTour(jsonTourOffer);
        for (Tour offer : tourOffers) {
            if (offer.equals(tourOffer)){
                System.out.println("before remove");
                tourOffers.remove(offer);
                return "Tour offer \"" + tourOffer.getName() + "\" removed successfully";
            }
        }
        return "Tour offer " + tourOffer.getName() + " doesnt exist";
    }

    private String getTourOffers() {
        return "tourOffers:" + JsonHandler.tourListToJson(tourOffers);
    }

    private String registerForTour(String input) {
        Tourist tourist = (Tourist) parseRegistrationData(input)[0];
        Tour tour = (Tour) parseRegistrationData(input)[1];
        for (Tour tourAvailable : tours) {
            if (tourAvailable.equals(tour)) {
                for (Tourist touristAvailable : tourists) {
                    if (touristAvailable.equals(tourist)){
                        try {
                            touristAvailable.setToursParticipated(tourAvailable);
                            tourAvailable.removeSpotAvailable();
                        } catch (TouristAlreadyParticipatesException e) {
                            return e.getMessage();
                        }
                        return touristAvailable.getName()
                                + " "
                                + touristAvailable.getSurname()
                                + " successfully registered for tour: \""
                                + tourAvailable.getName()
                                + "\"";
                    }
                }
            }
        }
        return "Registration failed";
    }

    private String unregisterFromTour(String input) {
        Tourist tourist = (Tourist) parseRegistrationData(input)[0];
        Tour tour = (Tour) parseRegistrationData(input)[1];
        for (Tour tourAvailable : tours) {
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

//    @Override
//    public void startServer(String host, int port) {
////        serverSocketHost = host;
////        serverSocketPort = port;
////        try {
////            serverSocket = new ServerSocket(port);
////            System.out.println("socket server local port: " +  serverSocket.getLocalPort());
////            Thread t = new Thread(() -> {
////                while(true) {
////                    try {
////                        socket = serverSocket.accept();
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                    System.out.println("Socket local port: " + socket.getPort());
////                }
////            });
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//////        try{
//////            ServerSocket ss = new ServerSocket(4000);
//////        } catch (IOException e) {
//////            e.printStackTrace();
//////        }
//    }
}
