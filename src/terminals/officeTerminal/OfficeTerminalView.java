package terminals.officeTerminal;

import javax.swing.*;

public class OfficeTerminalView extends JFrame {
    private OfficeTerminalViewListener officeTerminalViewListener;
    private JPanel panel;
    private JButton getTourOffersButton;

    public OfficeTerminalView() {
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

    public void setOfficeTerminalViewListener(OfficeTerminalViewListener officeTerminalViewListener) {
        this.officeTerminalViewListener = officeTerminalViewListener;
    }
}
