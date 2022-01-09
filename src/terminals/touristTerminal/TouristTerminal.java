package terminals.touristTerminal;

import main.Office;
import model.Tour;
import utils.JsonHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TouristTerminal implements TouristTerminalListener{
    private List<Tour> tourOffers;
    private Socket socket;
    private PrintWriter outputToServer;
    private String outputLine;
    private TouristTerminalWindow touristTerminalWindow;

    public static void main(String[] args) {
        TouristTerminal touristTerminal = new TouristTerminal();
        touristTerminal.setTouristTerminalWindow(new TouristTerminalWindow());
        touristTerminal.startConnection();
        try {
            touristTerminal.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TouristTerminal() {
        this.tourOffers = new ArrayList<>();
    }

    public void startConnection() {
        try {
            System.out.println(Office.getServerSocketHost());
            System.out.println(Office.getServerSocketPort());
            socket = new Socket(Office.getServerSocketHost(), Office.getServerSocketPort());
            outputToServer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverResponse;
            while((serverResponse = inputFromServer.readLine()) != null) {
                touristTerminalWindow.showServerResponse("server response: " + serverResponse);
                if (serverResponse.contains("tourOffers:")) {
                    String jsonTourOffer = serverResponse.substring(serverResponse.indexOf(":") + 1);
                    tourOffers = JsonHandler.jsonToTourList(jsonTourOffer);
                    addTourOffersToWindow();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerForTour(String tourist, String tour) {
        outputLine = "registerForTour:";
        System.out.println("send this to server: " + outputLine);
        outputToServer.println(outputLine + tourist + "&" + tour);
    }

    @Override
    public void unregisterFromTour(String tourist, String tour) {
        outputLine = "unregisterFromTour:";
        System.out.println("send this to server: " + outputLine);
        outputToServer.println(outputLine + tourist + "&" + tour);
    }

    @Override
    public void getTourOffers() {
        outputLine = "getTourOffers:";
        System.out.println("send this to server: " + outputLine);
        outputToServer.println(outputLine);
    }

    private void addTourOffersToWindow(){
        touristTerminalWindow.setTourOffers(tourOffers);
    }

    public void setTouristTerminalWindow(TouristTerminalWindow touristTerminalWindow) {
        this.touristTerminalWindow = touristTerminalWindow;
        touristTerminalWindow.setTouristTerminalListener(this);
    }

    public Socket getSocket() {
        return socket;
    }
}
