package terminals.guideTerminal;

import model.Guide;
import model.Tour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class GuideTerminalWindow extends JFrame {
    private GuideTerminalListener guideTerminalListener;
    private JLabel serverResponseLabel;
    private JButton addGuideButton;
    private JButton removeGuideButton;
    private JPanel guideInfoPanel;
    private JTextField guideHostTextField;
    private JTextField guidePortTextField;
    private JTextField guideNameTextField;
    private JTextField guideSurnameTextField;
    private boolean hostFilled;
    private boolean portFilled;
    private boolean nameFilled;
    private boolean surnameFilled;
    private boolean serverStarted;
    private JPanel tourInfoPanel;
    private JLabel tourDescriptionLabel;

    public GuideTerminalWindow(){
        this.setTitle("GUIDE TERMINAL");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 850, 600);
        mainPanel.setBackground(new Color(0x770F37));

        setAddGuideButton();
        setRemoveGuideButton();
        setGuideInfoTextPanel();
        setServerResponsePanel();
        setTourPanel();

        this.setResizable(false);
        this.setSize(850, 610);
        this.add(mainPanel);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.getContentPane().requestFocusInWindow();
    }

    private void setAddGuideButton() {
        addGuideButton = new JButton("ADD GUIDE");
        addGuideButton.setBounds(70, 480, 200, 50);
        addGuideButton.setEnabled(false);
        addGuideButton.addActionListener(e -> {
            if (e.getSource() == addGuideButton && guideTerminalListener != null) {
                String name = guideNameTextField.getText();
                String surname = guideSurnameTextField.getText();
                String host = guideHostTextField.getText();
                String port = guidePortTextField.getText();
                if (!name.isEmpty() && !surname.isEmpty() && !host.isEmpty() && !port.isEmpty()){
                    if (!serverStarted){
                        serverStarted = true;
                        guideTerminalListener.startServer(host, Integer.parseInt(port));
                    }
                    guideTerminalListener.addGuide(new Guide(name, surname));
                }
            }
        });
        this.add(addGuideButton);
    }

    private void setRemoveGuideButton() {
        removeGuideButton = new JButton("REMOVE GUIDE");
        removeGuideButton.setBounds(320, 480, 200, 50);
        removeGuideButton.setEnabled(false);
        removeGuideButton.addActionListener(e -> {
            if (e.getSource() == removeGuideButton && guideTerminalListener != null) {
                String name = guideNameTextField.getText();
                String surname = guideSurnameTextField.getText();
                String host = guideHostTextField.getText();
                String port = guidePortTextField.getText();
                if (!name.isEmpty() && !surname.isEmpty() && !host.isEmpty() && !port.isEmpty()){
                    if (!serverStarted){
                        serverStarted = true;
                        guideTerminalListener.startServer(host, Integer.parseInt(port));
                    }
                    guideTerminalListener.removeGuide(new Guide(name, surname));
                }
            }
        });
        this.add(removeGuideButton);
    }

    private void setGuideInfoTextPanel() {
        guideInfoPanel = new JPanel();
        guideInfoPanel.setLayout(null);
        guideInfoPanel.setBounds(150,200,300,100);
        setGuideHostTextField();
        setGuidePortTextField();
        setGuideNameTextField();
        setGuideSurnameTextField();
        this.add(guideInfoPanel);
    }

    private void setGuideHostTextField() {
        guideHostTextField = new JTextField("host");
        guideHostTextField.setBounds(5,5,140,40);
        guideHostTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                guideHostTextField.setText("");
                hostFilled = true;
                enableRegistrationButtons();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        guideInfoPanel.add(guideHostTextField);
    }

    private void setGuidePortTextField() {
        guidePortTextField = new JTextField("port");
        guidePortTextField.setBounds(155,5,140,40);
        guidePortTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                guidePortTextField.setText("");
                portFilled = true;
                enableRegistrationButtons();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        guideInfoPanel.add(guidePortTextField);
    }

    private void setGuideNameTextField() {
        guideNameTextField = new JTextField("name");
        guideNameTextField.setBounds(5,55,140,40);
        guideNameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                guideNameTextField.setText("");
                nameFilled = true;
                enableRegistrationButtons();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        guideInfoPanel.add(guideNameTextField);
    }

    private void setGuideSurnameTextField() {
        guideSurnameTextField = new JTextField("surname");
        guideSurnameTextField.setBounds(155,55,140,40);
        guideSurnameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                guideSurnameTextField.setText("");
                surnameFilled = true;
                enableRegistrationButtons();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        guideInfoPanel.add(guideSurnameTextField);
    }

    private void enableRegistrationButtons() {
        if (hostFilled && portFilled && nameFilled && surnameFilled){
            addGuideButton.setEnabled(true);
            removeGuideButton.setEnabled(true);
        }
    }

    private void setServerResponsePanel() {
        JPanel serverResponsePanel = new JPanel();
        serverResponsePanel.setLayout(null);
        serverResponsePanel.setBounds(100,50, 400,50);
        serverResponseLabel = new JLabel();
        serverResponseLabel.setBounds(0,0,400,50);
        serverResponsePanel.add(serverResponseLabel);
        this.add(serverResponsePanel);
    }

    private void setTourPanel() {
        tourInfoPanel = new JPanel();
        tourInfoPanel.setBounds(570,50,200,400);
        tourInfoPanel.setLayout(null);
        setDescriptionLabel();
        this.add(tourInfoPanel);
    }

    private void setDescriptionLabel() {
        tourDescriptionLabel = new JLabel();
        tourDescriptionLabel.setVerticalAlignment(JLabel.TOP);
        tourDescriptionLabel.setBounds(5,5,200,400);
        tourDescriptionLabel.setFont(new Font("Arial", Font.PLAIN,18));
        tourInfoPanel.add(tourDescriptionLabel);
    }

    public void showServerResponse(String response){
        this.serverResponseLabel.setText(response);
    }

    public void refreshTourPanel(Tour tour){
        if (tour != null) {
            tourDescriptionLabel.setText(
                "<html>" +
                    "<p>" + tour.getName() + "</p>" +
                    "<p><br>" + tour.getDescription() +"</p>" +
                    "<p><br> Date: " + tour.getDate() +"</p>" +
                    "<p><br>Spots: " + tour.getSpots() +"</p>" +
                    "<p><br>Spots available: " + tour.getSpotsAvailable() +"</p>" +
                "</html>"
            );
        } else {
            tourDescriptionLabel.setText("");
        }
    }

    public void setGuideTerminalListener(GuideTerminalListener guideTerminalListener) {
        this.guideTerminalListener = guideTerminalListener;
    }
}
