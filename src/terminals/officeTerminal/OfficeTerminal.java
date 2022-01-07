package terminals.officeTerminal;

import model.Tour;

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
//        officeTerminal.setDaemon(true);
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
            outputLine = "";
            while (true) {
//                System.out.println(sendMessageToServer);
//                if (sendMessageToServer){
//                    System.out.println("send this to server: " + outputLine);
//                    outputToServer.println(outputLine);
//                    outputLine = null;
//                }

//                System.out.println("send this to server: " + outputLine);
//                outputToServer.println(outputLine);

//                System.out.println(outputLine);
//                if (outputLine.isEmpty()) {
//                    System.out.println("send this to server: " + outputLine);
//                    outputToServer.println(outputLine);
//                    outputLine = "";
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTourOffer(Tour tour) {

    }

    @Override
    public void removeTourOffer(Tour tour) {

    }

    @Override
    public void getTourOffers() {
//        System.out.println("parsed");
        outputLine = "getTourOffers:";
//        System.out.println(outputLine);
//        sendMessageToServer = true;
//        outputToServer.println("getTourOffers:");


//        System.out.println("send this to server: " + outputLine);
//        outputToServer.println("outputLine");
    }
}
