package terminals.guideTerminal;

import exceptions.TouristNotParticipatingException;
import model.Guide;
import model.Office;
import model.Tour;
import model.Tourist;
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

    private String guideServerHost;
    private int guideServerPort;
    private ServerSocket guideServerSocket;

    public static void main(String[] args) {
        GuideTerminal guideTerminal = new GuideTerminal();
        guideTerminal.setGuideTerminalWindow(new GuideTerminalWindow());
    }

    public void startServer() {
        serverThread = new Thread(() -> {
            try {
                guideServerSocket = new ServerSocket(guideServerPort);
                System.out.println("guide socket server local port: " + guideServerPort);
                while (true) {
                    Socket officeSocket = guideServerSocket.accept();
                    System.out.println("office socket local port: " + officeSocket.getPort());
                    outputToOffice = new PrintWriter(officeSocket.getOutputStream(), true);
                    inputFromOffice = new BufferedReader(new InputStreamReader(officeSocket.getInputStream()));
                    String officeInput = inputFromOffice.readLine();
                    if (officeInput.equals("updateTourInfo:")){
//                    updateTourInfo();
                    }
                    officeSocket.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        });
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

    @Override
    public void updateTourInfo(Tour tour) {

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
