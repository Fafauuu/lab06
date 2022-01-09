package terminals.guideTerminal;

import model.Guide;
import model.Office;
import model.Tour;
import utils.JsonHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GuideTerminal implements GuideTerminalListener{
    private Socket officeSocket;
    private PrintWriter outputToOffice;
    private BufferedReader inputFromOffice;
    private Socket guideSocket;
    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;
    private Thread serverThread;
    private GuideTerminalWindow guideTerminalWindow;
    private String outputLine;

    private String guideServerHost = "localhost";
    private int guideServerPort;
    private ServerSocket guideServerSocket;

    public static void main(String[] args) {
        GuideTerminal guideTerminal = new GuideTerminal();
        guideTerminal.setGuideTerminalWindow(new GuideTerminalWindow());
        guideTerminal.startServer();
        guideTerminal.startConnection();
    }

    public void startServer() {
        serverThread = new Thread(() -> {
            try {
                guideServerPort = findPort();
                guideServerSocket = new ServerSocket(guideServerPort);
                System.out.println("guide socket server local port: " + guideServerPort);
                while (true) {
                    Socket officeSocket = guideServerSocket.accept();
                    outputToOffice = new PrintWriter(officeSocket.getOutputStream(), true);
                    inputFromOffice = new BufferedReader(new InputStreamReader(officeSocket.getInputStream()));
                    String officeInput = inputFromOffice.readLine();
                    if (officeInput.contains("updateTourInfo:")){
                        updateTourInfo(officeInput);
                    } else {
                        guideTerminalWindow.showServerResponse(officeInput);
                    }
                    officeSocket.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        });
        serverThread.start();
    }

    public void startConnection() {
        try {
            guideSocket = new Socket(Office.getServerSocketHost(), Office.getServerSocketPort());
            outputToServer = new PrintWriter(guideSocket.getOutputStream(), true);
            inputFromServer = new BufferedReader(new InputStreamReader(guideSocket.getInputStream()));
            String serverResponse;
            while((serverResponse = this.inputFromServer.readLine()) != null) {
                guideTerminalWindow.showServerResponse("server response: " + serverResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTourInfo(String input) {
        guideTerminalWindow.showServerResponse("server response: " + input);
        String jsonTourOffer = input.substring(input.indexOf(":") + 1);
        Tour tourOffer = JsonHandler.jsonToTour(jsonTourOffer);
        guideTerminalWindow.refreshTourPanel(tourOffer);
    }

    @Override
    public void addGuide(Guide guide) {
        String jsonGuide = JsonHandler.guideToJson(guide);
        outputLine = "addGuide:";
        System.out.println("send this to server: " + outputLine);
        outputToServer.println(outputLine + jsonGuide + "&" + guideServerHost + "&" + guideServerPort);
    }

    @Override
    public void removeGuide(Guide guide) {
        String jsonGuide = JsonHandler.guideToJson(guide);
        outputLine = "removeGuide:";
        System.out.println("send this to server: " + outputLine);
        outputToServer.println(outputLine + jsonGuide + "&" + guideServerHost + "&" + guideServerPort);
    }

    public void setGuideTerminalWindow(GuideTerminalWindow guideTerminalWindow) {
        this.guideTerminalWindow = guideTerminalWindow;
        guideTerminalWindow.setGuideTerminalListener(this);
    }

    private int findPort() {
        int startport = Office.getServerSocketPort() + 1;
        int stopport = 65535;
        for (int port = startport; port <= stopport; port++) {
            try {
                ServerSocket ss = new ServerSocket(port);
                ss.close();
                return port;
            } catch (IOException ex) {
                System.out.println("Port " + port + " is occupied.");
            }
        }
        return 0;
    }
}
