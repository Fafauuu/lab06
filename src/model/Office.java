package model;

import com.google.gson.Gson;
import terminals.OfficeFrameListener;
import utils.DatabaseHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Office implements OfficeFrameListener {
    private List<Tour> tours;
    private List<Tour> tourOffers;
    private List<Guide> guides;
    private List<Tourist> tourists;
    private Socket socket;
    private String serverSocketHost;
    Gson gson = new Gson();

    private int serverSocketPort = 4001;
    private ServerSocket serverSocket = null;

    public static void main(String[] args) {
        Office office = new Office();
//        initializeData();
//        office.runServer();

//       OfficeFrame officeFrame =  new OfficeFrame();
//        officeFrame.setOfficeFrameListener(office);
//        officeFrame.requestFocusInWindow();

        office.saveData();
    }

    public Office() {
        initializeData();

        System.out.println(tours.get(0));
        String json = gson.toJson(tours.get(0));
        System.out.println(json);
//        addTourOffer("addTourOffer:" + json);
        removeTourOffer("removeTourOffer:" + json);

        Tour tour2 = gson.fromJson(json, Tour.class);
        System.out.println(tour2);

        System.out.println("tour offers:");
        for (Tour tourOffer : tourOffers) {
            System.out.println(tourOffer);
        }
    }

    private void initializeData() {
        this.tours = DatabaseHandler.readTourList("tours.json");
        this.tourOffers = DatabaseHandler.readTourList("tourOffers.json");
        this.guides = new ArrayList<>();
        this.tourists = DatabaseHandler.readTouristList("tourists.json");
    }

    private void saveData() {
        DatabaseHandler.saveTourList("tours.json", tours);
        DatabaseHandler.saveTourList("tourOffers.json", tourOffers);
        DatabaseHandler.saveTouristList("tourists.json", tourists);
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

    public void addTourOffer(String input) {
        String jsonTourOffer = input.substring(input.indexOf(":") + 1);
        Tour tourOffer = gson.fromJson(jsonTourOffer, Tour.class);
        for (Tour offer : tourOffers) {
            if(offer.equals(tourOffer)) return;
        }
        tourOffers.add(tourOffer);
    }

    public void removeTourOffer(String input) {
        String jsonTourOffer = input.substring(input.indexOf(":") + 1);
        Tour tourOffer = gson.fromJson(jsonTourOffer, Tour.class);
        tourOffers.removeIf(offer -> offer.equals(tourOffer));
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
}
