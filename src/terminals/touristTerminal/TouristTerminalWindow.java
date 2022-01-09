package terminals.touristTerminal;

import model.Tour;
import model.Tourist;
import utils.JsonHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class TouristTerminalWindow extends JFrame {
    private TouristTerminalListener touristTerminalListener;
    private JButton getTourOffersButton;
    private JButton registerForTourButton;
    private JButton unregisterFromTourButton;
    private JList<Tour> tourJList;
    private DefaultListModel<Tour> model;
    private JPanel tourInfoPanel;
    private JLabel tourDescriptionLabel;
    private JLabel serverResponseLabel;
    private JPanel userInfoPanel;
    private JTextField userNameTextField;
    private JTextField userSurnameTextField;
    private boolean nameFilled;
    private boolean surnameFilled;
    private List<Tour> tourOffers;
    private Tourist tourist;

    public TouristTerminalWindow(){
        this.setTitle("TOURIST TERMINAL");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 1000, 600);
        mainPanel.setBackground(new Color(0x265010));
        setGetTourOffersButton();
        setRegisterForTourButton();
        setUnregisterFromTourButton();
        setUserInfoTextPanel();
        setTourList();
        setTourPanel();
        setServerResponsePanel();

        this.setResizable(false);
        this.setSize(1000, 610);
        this.add(mainPanel);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.getContentPane().requestFocusInWindow();
    }

    private void setGetTourOffersButton() {
        getTourOffersButton = new JButton("GET TOUR OFFERS");
        getTourOffersButton.setBounds(50, 480, 200, 50);
        getTourOffersButton.addActionListener(e -> {
            if (e.getSource() == getTourOffersButton && touristTerminalListener != null) {
                touristTerminalListener.getTourOffers();
            }
        });
        this.add(getTourOffersButton);
    }

    private void setRegisterForTourButton() {
        registerForTourButton = new JButton("REGISTER FOR TOUR");
        registerForTourButton.setEnabled(false);
        registerForTourButton.setBounds(300, 480, 200, 50);
        registerForTourButton.addActionListener(e -> {
            if (e.getSource() == registerForTourButton && touristTerminalListener != null) {
                String name = userNameTextField.getText();
                String surname = userSurnameTextField.getText();
                Tour tour = tourJList.getSelectedValue();
                if (!name.isEmpty() && !surname.isEmpty() && tour != null){
                    tourist = new Tourist(name, surname);
                    touristTerminalListener
                            .registerForTour(JsonHandler.touristToJson(tourist), JsonHandler.tourToJson(tour));
                } else {
                    showServerResponse("invalid data");
                }
            }
        });
        this.add(registerForTourButton);
    }

    private void setUnregisterFromTourButton() {
        unregisterFromTourButton = new JButton("UNREGISTER FROM TOUR");
        unregisterFromTourButton.setEnabled(false);
        unregisterFromTourButton.setBounds(550, 480, 200, 50);
        unregisterFromTourButton.addActionListener(e -> {
            if (e.getSource() == unregisterFromTourButton && touristTerminalListener != null) {
                String name = userNameTextField.getText();
                String surname = userSurnameTextField.getText();
                Tour tour = tourJList.getSelectedValue();
                if (!name.isEmpty() && !surname.isEmpty() && tour != null){
                    tourist = new Tourist(name, surname);
                    touristTerminalListener
                            .unregisterFromTour(JsonHandler.touristToJson(tourist), JsonHandler.tourToJson(tour));
                } else {
                    showServerResponse("invalid data");
                }
            }
        });
        this.add(unregisterFromTourButton);
    }

    private void setTourList() {
        tourJList = new JList<>();
        model = new DefaultListModel<>();
        tourJList.setModel(model);
        tourJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (tourOffers != null) {
            model.addAll(tourOffers);
        }
        tourJList.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()){
                tourChosenFromList(tourJList.getSelectedValue());
            }
        });
        System.out.println(tourOffers);
        System.out.println(model);
        tourJList.setFont(new Font("Arial", Font.BOLD,20));
        tourJList.setBounds(50,50,200,400);
        this.add(tourJList);
    }

    private void tourChosenFromList(Tour tourChosen) {
        if (tourChosen != null) {
            tourDescriptionLabel.setText(
                "<html>" +
                    "<p>" + tourChosen.getName() + "</p>" +
                    "<p><br>" + tourChosen.getDescription() +"</p>" +
                    "<p><br> Date: " + tourChosen.getDate() +"</p>" +
                    "<p><br>Spots: " + tourChosen.getSpots() +"</p>" +
                    "<p><br>Spots available: " + tourChosen.getSpotsAvailable() +"</p>" +
                "</html>"
            );
        } else {
            tourDescriptionLabel.setText("");
        }
    }

    private void setTourPanel() {
        tourInfoPanel = new JPanel();
        tourInfoPanel.setBounds(300,50,200,400);
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

    private void setServerResponsePanel() {
        JPanel serverResponsePanel = new JPanel();
        serverResponsePanel.setLayout(null);
        serverResponsePanel.setBounds(550,300, 400,50);
        serverResponseLabel = new JLabel();
        serverResponseLabel.setBounds(0,0,400,50);
        serverResponsePanel.add(serverResponseLabel);
        this.add(serverResponsePanel);
    }

    private void setUserInfoTextPanel() {
        userInfoPanel = new JPanel();
        userInfoPanel.setLayout(null);
        userInfoPanel.setBounds(600,100,300,50);
        setUserNameTextField();
        setUserSurnameTextField();
        this.add(userInfoPanel);
    }

    private void setUserSurnameTextField() {
        userSurnameTextField = new JTextField("surname");
        userSurnameTextField.setBounds(155,5,140,40);
        userSurnameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userSurnameTextField.setText("");
                surnameFilled = true;
                enableRegistrationButtons();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        userInfoPanel.add(userSurnameTextField);
    }

    private void setUserNameTextField() {
        userNameTextField = new JTextField("name");
        userNameTextField.setBounds(5,5,140,40);
        userNameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userNameTextField.setText("");
                nameFilled = true;
                enableRegistrationButtons();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        userInfoPanel.add(userNameTextField);
    }

    private void enableRegistrationButtons() {
        if (nameFilled && surnameFilled){
            registerForTourButton.setEnabled(true);
            unregisterFromTourButton.setEnabled(true);
        }
    }

    public void showServerResponse(String response){
        this.serverResponseLabel.setText(response);
    }

    public void setTouristTerminalListener(TouristTerminalListener touristTerminalListener) {
        this.touristTerminalListener = touristTerminalListener;
    }

    public void setTourOffers(List<Tour> tourOffers) {
        this.tourOffers = tourOffers;
        model.clear();
        if (!tourOffers.isEmpty()){
            model.addAll(tourOffers);
        }
        tourJList.updateUI();
    }
}
