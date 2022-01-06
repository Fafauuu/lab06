package model;

import com.google.gson.Gson;
import terminals.OfficeFrameListener;
import utils.DatabaseHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Office implements OfficeFrameListener {
    private final List<Tour> tours;
    private final List<Tour> tourOffers;
    private final List<Guide> guides;
    private final List<Tourist> participants;
    private Socket socket;
    private String serverSocketHost;
    Gson gson = new Gson();

    private int serverSocketPort = 4001;
    private ServerSocket serverSocket = null;

    public static void main(String[] args) {
        Office office = new Office();

//        office.runServer();

//       OfficeFrame officeFrame =  new OfficeFrame();
//        officeFrame.setOfficeFrameListener(office);
//        officeFrame.requestFocusInWindow();
    }

    public Office() {
        this.tours = DatabaseHandler.readTourList("tours.json");
        this.tourOffers = DatabaseHandler.readTourList("tourOffers.json");
        this.guides = new ArrayList<>();
        this.participants = new ArrayList<>();

        System.out.println(tours.get(0));
        String json = gson.toJson(tours.get(0));
        System.out.println(json);

        Tour tour2 = gson.fromJson(json, Tour.class);
        System.out.println(tour2);
    }

    private static void initializeData() {
        Tour tour = new Tour("Hawaii trip");
        tour.setDescription("Hawaii New Year trip");
        tour.setDate(LocalDate.parse("2022-12-30"));
        tour.setSpots(10);
        tour.setSpotsAvailable(10);

        Tour tour1 = new Tour("Italy tour");
        tour1.setDescription("Italy tour across whole country");
        tour1.setDate(LocalDate.parse("2022-08-25"));
        tour1.setSpots(20);
        tour1.setSpotsAvailable(20);

        List<Tour> tours = new ArrayList<>();
        tours.add(tour);
        tours.add(tour1);

        DatabaseHandler.saveTourList("tours.json", tours);

        Gson gson = new Gson();
        System.out.println(tour);
        String json = gson.toJson(tour);
        System.out.println(json);

        Tour tour2 = gson.fromJson(json, Tour.class);
        System.out.println(tour2);

    }

    private void runServer() {
        try {
            serverSocket = new ServerSocket(serverSocketPort);
            System.out.println("socket server local port: " +  serverSocket.getLocalPort());
//            Thread t = new Thread(() -> {
                while(true) {
                    try {
                        socket = serverSocket.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Socket local port: " + socket.getPort());
                }
//            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startServer(String host, int port) {
//        serverSocketHost = host;
//        serverSocketPort = port;
//        try {
//            serverSocket = new ServerSocket(port);
//            System.out.println("socket server local port: " +  serverSocket.getLocalPort());
//            Thread t = new Thread(() -> {
//                while(true) {
//                    try {
//                        socket = serverSocket.accept();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("Socket local port: " + socket.getPort());
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        try{
////            ServerSocket ss = new ServerSocket(4000);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
    }

    public void addTourOffer(String tour) {

    }

    public void removeTourOffer(String tour) {

    }

    public void getTourOffers() {

    }

    public void registerForTour(String tour, String tourist) {

    }

    public void unregisterFromTour(String tour, String tourist) {

    }

    public void updateTourInfo(String tourInfo) {

    }

    public void addGuide(String guideInfo, String host, String port) {

    }

    public void removeGuide(String guideInfo, String host, String port) {

    }
}
