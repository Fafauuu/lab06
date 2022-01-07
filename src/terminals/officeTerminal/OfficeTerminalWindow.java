package terminals.officeTerminal;

import model.Tour;

import javax.swing.*;
import java.time.LocalDate;

public class OfficeTerminalWindow extends JFrame {
    private OfficeTerminalWindowListener officeTerminalViewListener;
    private JPanel panel;
    private JButton getTourOffersButton;
    private JButton addTourOfferButton;
    private JButton removeTourOfferButton;

    public OfficeTerminalWindow() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 500, 500);

        setGetTourOffersButton();
        setAddTourOfferButton();
        setRemoveTourOfferButton();

        this.setResizable(false);
        this.setSize(500, 510);
        this.add(panel);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void setGetTourOffersButton() {
        getTourOffersButton = new JButton("GET TOUR OFFERS");
        getTourOffersButton.setBounds(210, 10, 200, 50);
        getTourOffersButton.addActionListener(e -> {
            if (e.getSource() == getTourOffersButton && officeTerminalViewListener != null) {
                officeTerminalViewListener.getTourOffers();
            }
        });
        this.add(getTourOffersButton);
    }

    private void setAddTourOfferButton() {
        addTourOfferButton = new JButton("ADD TOUR OFFER");
        addTourOfferButton.setBounds(210, 100, 200, 50);
        addTourOfferButton.addActionListener(e -> {
            if (e.getSource() == addTourOfferButton && officeTerminalViewListener != null) {
                Tour tour = new Tour("Spain tour", 30);
                tour.setDescription("¡Vamos a la Madrid y Barcelona!");
                tour.setDate(LocalDate.parse("2022-06-10"));
                officeTerminalViewListener.addTourOffer(tour);
            }
        });
        this.add(addTourOfferButton);
    }
    private void setRemoveTourOfferButton() {
        removeTourOfferButton = new JButton("REMOVE TOUR OFFER");
        removeTourOfferButton.setBounds(210, 200, 200, 50);
        removeTourOfferButton.addActionListener(e -> {
            if (e.getSource() == removeTourOfferButton && officeTerminalViewListener != null) {
                Tour tour = new Tour("Spain tour", 30);
                tour.setDescription("¡Vamos a la Madrid y Barcelona!");
                tour.setDate(LocalDate.parse("2022-06-10"));
                officeTerminalViewListener.removeTourOffer(tour);
            }
        });
        this.add(removeTourOfferButton);
    }


    public void setOfficeTerminalViewListener(OfficeTerminalWindowListener officeTerminalViewListener) {
        this.officeTerminalViewListener = officeTerminalViewListener;
    }
}
