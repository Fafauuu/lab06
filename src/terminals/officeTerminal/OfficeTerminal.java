package terminals.officeTerminal;

import model.Tour;
import utils.JsonHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class OfficeTerminal implements OfficeTerminalWindowListener {
    private final List<Tour> tourOffers;
    private Socket socket;
    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;
    private String outputLine;
    private String responseFromServer;
    private boolean sendMessageToServer = false;

    public static void main(String[] args) {
        OfficeTerminal officeTerminal = new OfficeTerminal();
        OfficeTerminalWindow officeTerminalView = new OfficeTerminalWindow();
        officeTerminalView.setOfficeTerminalViewListener(officeTerminal);
        officeTerminal.startConnection();
    }

    public OfficeTerminal() {
        this.tourOffers = new ArrayList<>();
    }

    public void startConnection() {
        try {
            socket = new Socket("localhost", 4002);
            outputToServer = new PrintWriter(socket.getOutputStream(), true);
            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverResponse;
            while(true) {
                try {
                    serverResponse = this.inputFromServer.readLine();
                    System.out.println("response from server: " + serverResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTourOffer(Tour tour) {
        outputLine = "addTourOffer:" + JsonHandler.tourToJson(tour);
        System.out.println("send this to server: " + outputLine);
        outputToServer.println(outputLine);
    }

    @Override
    public void removeTourOffer(Tour tour) {
        outputLine = "removeTourOffer:" + JsonHandler.tourToJson(tour);
        System.out.println("send this to server: " + outputLine);
        outputToServer.println(outputLine);
    }

    @Override
    public void getTourOffers() {
        outputLine = "getTourOffers:";
        System.out.println("send this to server: " + outputLine);
        outputToServer.println(outputLine);
    }
}
