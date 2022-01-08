package terminals.touristTerminal;

import model.Tour;
import model.Tourist;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TouristTerminalWindow extends JFrame {
    private TouristTerminalListener touristTerminalListener;
    private JPanel mainPanel;
    private JButton getTourOffersButton;
    private JButton registerForTourButton;
    private JButton unregisterFromTourButton;
    private JList<Tour> tourJList;
    private DefaultListModel<Tour> model;
    private JPanel tourInfoPanel;
    private final JLabel tourNameLabel = new JLabel();
    private final JLabel tourDescriptionLabel = new JLabel();
    private JPanel serverResponsePanel;
    private JLabel serverResponseLabel;
    private List<Tour> tourOffers;
    private Tourist tourist;

    public TouristTerminalWindow() throws HeadlessException {
        this.setTitle("TOURIST TERMINAL");

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 1000, 600);
        mainPanel.setBackground(new Color(0x9B9B9B));

//        setLoginButton();
        setGetTourOffersButton();
        setRegisterForTourButton();
        setUnregisterFromTourButton();
//        setLoginTextField();
//        setUserInfoTextField();
        setTourList();
        setTourPanel();
        setServerResponsePanel();

        this.setResizable(false);
        this.setSize(1000, 610);
        this.add(mainPanel);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

//    private void setLoginButton() {
//        getTourOffersButton = new JButton("LOGIN");
//        getTourOffersButton.setBounds(50, 480, 200, 50);
//        getTourOffersButton.addActionListener(e -> {
//            if (e.getSource() == getTourOffersButton && touristTerminalListener != null) {
//                touristTerminalListener.getTourOffers();
//            }
//        });
//        this.add(getTourOffersButton);
//    }

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
//                touristTerminalListener.registerForTour();
                registerForTourButton.setEnabled(false);
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
//                touristTerminalListener.unregisterFromTour();
                unregisterFromTourButton.setEnabled(false);
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
            tourNameLabel.setText(tourChosen.getName());
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
            tourNameLabel.setText("");
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
        tourDescriptionLabel.setVerticalAlignment(JLabel.TOP);
        tourDescriptionLabel.setBounds(5,5,200,400);
        tourDescriptionLabel.setFont(new Font("Arial", Font.PLAIN,18));
        tourInfoPanel.add(tourDescriptionLabel);
    }

    private void setServerResponsePanel() {
        serverResponsePanel = new JPanel();
        serverResponsePanel.setLayout(null);
        serverResponsePanel.setBounds(550,300, 400,50);
        serverResponseLabel = new JLabel();
        serverResponseLabel.setBounds(0,0,400,50);
        serverResponsePanel.add(serverResponseLabel);
        this.add(serverResponsePanel);
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
