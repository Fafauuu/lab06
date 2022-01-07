package terminals.officeTerminal;

import javax.swing.*;

public class OfficeTerminalWindow extends JFrame {
    private OfficeTerminalWindowListener officeTerminalViewListener;
    private JPanel panel;
    private JButton getTourOffersButton;

    public OfficeTerminalWindow() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 500, 500);

        setGetTourOffersButton();

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

    public void setOfficeTerminalViewListener(OfficeTerminalWindowListener officeTerminalViewListener) {
        this.officeTerminalViewListener = officeTerminalViewListener;
    }
}
