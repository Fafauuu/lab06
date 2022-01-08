package terminals.officeTerminal;

import model.Tour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.util.List;

public class OfficeTerminalWindow extends JFrame {
    private OfficeTerminalWindowListener officeTerminalViewListener;
    private JPanel mainPanel;
    private JList<Tour> tourJList;
    private DefaultListModel<Tour> model;
    private JPanel tourInfoPanel;
    private JPanel addTourPanel;
    private JTextField tourNameTextField;
    private boolean tourNameFilled;
    private JTextField tourDescriptionTextField;
    private boolean tourDescriptionFilled;
    private JTextField tourDataTextField;
    private boolean tourDataFilled;
    private JTextField tourSpotsTextField;
    private boolean tourSpotsFilled;
    private final JLabel tourNameLabel = new JLabel();
    private final JLabel tourDescriptionLabel = new JLabel();
    private JPanel serverResponsePanel;
    private JLabel serverResponseLabel;
    private JButton getTourOffersButton;
    private JButton addTourOfferButton;
    private JButton removeTourOfferButton;
    private List<Tour> tourOffers;

    public OfficeTerminalWindow() {
        this.setTitle("OFFICE TERMINAL");

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 1000, 600);
        mainPanel.setBackground(new Color(0x9B9B9B));

        setGetTourOffersButton();
        setAddTourOfferButton();
        setRemoveTourOfferButton();
        setTourList();
        setTourPanel();
        setAddTourOfferPanel();
        setServerResponsePanel();

        this.setResizable(false);
        this.setSize(1000, 610);
        this.add(mainPanel);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void setGetTourOffersButton() {
        getTourOffersButton = new JButton("GET TOUR OFFERS");
        getTourOffersButton.setBounds(50, 480, 200, 50);
        getTourOffersButton.addActionListener(e -> {
            if (e.getSource() == getTourOffersButton && officeTerminalViewListener != null) {
                officeTerminalViewListener.getTourOffers();
            }
        });
        this.add(getTourOffersButton);
    }

    private void setAddTourOfferButton() {
        addTourOfferButton = new JButton("ADD TOUR OFFER");
        addTourOfferButton.setEnabled(false);
        addTourOfferButton.setBounds(650, 480, 200, 50);
        addTourOfferButton.addActionListener(e -> {
            if (e.getSource() == addTourOfferButton && officeTerminalViewListener != null) {
                if (createTourOffer() != null){
                    officeTerminalViewListener.addTourOffer(createTourOffer());
                }
                tourNameTextField.setText("Tour name");
                tourNameFilled = false;
                tourDescriptionTextField.setText("Tour description");
                tourDescriptionFilled = false;
                tourSpotsTextField.setText("Tour spots");
                tourSpotsFilled = false;
                tourDataTextField.setText("Tour data (yyyy-mm-dd)");
                tourDataFilled = false;
                addTourOfferButton.setEnabled(false);
            }
        });
        this.add(addTourOfferButton);
    }

    private Tour createTourOffer() {
        Tour tour;
        try {
            tour = new Tour(tourNameTextField.getText(), Integer.parseInt(tourSpotsTextField.getText()));
            tour.setDescription(tourDescriptionTextField.getText());
            tour.setDate(LocalDate.parse(tourDataTextField.getText()));
        } catch (Exception e) {
            serverResponseLabel.setText("wrong tour information");
            return null;
        }
        return tour;
    }

    private void setRemoveTourOfferButton() {
        removeTourOfferButton = new JButton("REMOVE TOUR OFFER");
        removeTourOfferButton.setBounds(300, 480, 200, 50);
        removeTourOfferButton.addActionListener(e -> {
            if (e.getSource() == removeTourOfferButton && officeTerminalViewListener != null) {
                if (tourJList.getSelectedValue() != null) {
                    Tour tourToRemove = tourJList.getSelectedValue();
                    tourJList.clearSelection();
                    officeTerminalViewListener.removeTourOffer(tourToRemove);
                }
            }
        });
        this.add(removeTourOfferButton);
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

    private void setAddTourOfferPanel() {
        addTourPanel = new JPanel();
        addTourPanel.setLayout(null);
        addTourPanel.setBounds(550,50,400,200);
        setAddTourOfferLabels();
        this.add(addTourPanel);
    }

    private void setAddTourOfferLabels() {
        setAddTourNameTextField();
        setAddTourDescriptionTextField();
        setAddTourDateTextField();
        setAddTourSpotsTextField();
    }

    private void setAddTourNameTextField() {
        tourNameTextField = new JTextField("Tour name");
        tourNameTextField.setBounds(10,10,180,50);
        tourNameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                tourNameTextField.setText("");
                tourNameFilled = true;
                enableAddTourButton();
            }
            @Override
            public void focusLost(FocusEvent e) {}
        });
        addTourPanel.add(tourNameTextField);
    }

    private void setAddTourDescriptionTextField() {
        tourDescriptionTextField = new JTextField("Tour description");
        tourDescriptionTextField.setBounds(200,10,180,180);
        tourDescriptionTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                tourDescriptionTextField.setText("");
                tourDescriptionFilled = true;
                enableAddTourButton();
            }
            @Override
            public void focusLost(FocusEvent e) {}
        });
        addTourPanel.add(tourDescriptionTextField);
    }

    private void setAddTourDateTextField() {
        tourDataTextField = new JTextField("Tour data (yyyy-mm-dd)");
        tourDataTextField.setBounds(10,140,180,50);
        tourDataTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                tourDataTextField.setText("");
                tourDataFilled = true;
                enableAddTourButton();
            }
            @Override
            public void focusLost(FocusEvent e) {}
        });
        addTourPanel.add(tourDataTextField);
    }

    private void setAddTourSpotsTextField() {
        tourSpotsTextField = new JTextField("Tour spots");
        tourSpotsTextField.setBounds(10,75,180,50);
        tourSpotsTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                tourSpotsTextField.setText("");
                tourSpotsFilled = true;
                enableAddTourButton();
            }
            @Override
            public void focusLost(FocusEvent e) {}
        });
        addTourPanel.add(tourSpotsTextField);
    }

    private void enableAddTourButton() {
        if (tourNameFilled && tourDescriptionFilled && tourDataFilled && tourSpotsFilled){
            addTourOfferButton.setEnabled(true);
        }
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

    public void setOfficeTerminalViewListener(OfficeTerminalWindowListener officeTerminalViewListener) {
        this.officeTerminalViewListener = officeTerminalViewListener;
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
