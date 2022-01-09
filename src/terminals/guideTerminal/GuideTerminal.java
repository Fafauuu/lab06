package terminals.guideTerminal;

import model.Guide;
import main.Office;
import model.Tour;
import utils.JsonHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GuideTerminal implements GuideTerminalListener{
    private BufferedReader inputFromOffice;
    private PrintWriter outputToServer;
    private GuideTerminalWindow guideTerminalWindow;
    private String outputLine;

    private String guideServerHost;
    private int guideServerPort;
    private ServerSocket guideServerSocket;

    public static void main(String[] args) {
        GuideTerminal guideTerminal = new GuideTerminal();
        guideTerminal.setGuideTerminalWindow(new GuideTerminalWindow());
//        guideTerminal.startServer();
        guideTerminal.startConnection();
    }

    @Override
    public void startServer(String host, int port) {
        guideServerHost = host;
        guideServerPort = port;
        Thread serverThread = new Thread(() -> {
            try {
                guideServerSocket = new ServerSocket(guideServerPort);
                System.out.println("guide socket server local port: " + guideServerPort);
                while (true) {
                    Socket officeSocket = guideServerSocket.accept();
                    inputFromOffice = new BufferedReader(new InputStreamReader(officeSocket.getInputStream()));
                    String officeInput = inputFromOffice.readLine();
                    if (officeInput.contains("updateTourInfo:")) {
                        updateTourInfo(officeInput);
                    } else {
                        guideTerminalWindow.showServerResponse(officeInput);
                    }
                    officeSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }

    public void startConnection() {
        try {
            Socket guideSocket = new Socket(Office.getServerSocketHost(), Office.getServerSocketPort());
            outputToServer = new PrintWriter(guideSocket.getOutputStream(), true);
            BufferedReader inputFromServer = new BufferedReader(new InputStreamReader(guideSocket.getInputStream()));
            String serverResponse;
            while((serverResponse = inputFromServer.readLine()) != null) {
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
}
