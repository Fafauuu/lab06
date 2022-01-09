package terminals.officeTerminal;

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

public class OfficeTerminal implements OfficeTerminalWindowListener {
    private List<Tour> tourOffers;
    private PrintWriter outputToServer;
    private String outputLine;
    private OfficeTerminalWindow officeTerminalWindow;

    public static void main(String[] args) {
        OfficeTerminal officeTerminal = new OfficeTerminal();
        officeTerminal.setOfficeTerminalWindow(new OfficeTerminalWindow());
        officeTerminal.startConnection();
    }

    public OfficeTerminal() {
        this.tourOffers = new ArrayList<>();
    }

    public void startConnection() {
        try {
            Socket socket = new Socket(Office.getServerSocketHost(), Office.getServerSocketPort());
            outputToServer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverResponse;
            while((serverResponse = inputFromServer.readLine()) != null) {
                officeTerminalWindow.showServerResponse("server response: " + serverResponse);
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

    public void setOfficeTerminalWindow(OfficeTerminalWindow officeTerminalView) {
        this.officeTerminalWindow = officeTerminalView;
        officeTerminalView.setOfficeTerminalViewListener(this);
    }

    private void addTourOffersToWindow(){
        officeTerminalWindow.setTourOffers(tourOffers);
    }
}
