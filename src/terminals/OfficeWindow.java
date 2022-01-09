package terminals;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class OfficeWindow extends JFrame {
    private OfficeWindowListener officeWindowListener;
    private JPanel panel;
    private JTextField hostField;
    private JTextField portField;
    private boolean hostNumberFilled;
    private boolean portNumberFilled;
    private JButton startServerButton;
    private JButton stopServerButton;

    public OfficeWindow() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 500, 100);

        setHostField();
        setPortField();
        setStartButton();
        setStopButton();

        this.setResizable(false);
        this.setSize(500, 110);
        this.add(panel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void setHostField() {
        hostField = new JTextField("host");
        hostField.setBounds(10, 10, 90, 50);
        hostField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                hostField.setText("");
                hostNumberFilled = true;
                enableStartButton();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        this.add(hostField);
    }

    private void setPortField() {
        portField = new JTextField("port");
        portField.setBounds(110, 10, 90, 50);
        portField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                portField.setText("");
                portNumberFilled = true;
                enableStartButton();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        this.add(portField);
    }

    private void setStartButton() {
        startServerButton = new JButton("START");
        startServerButton.setBounds(210, 10, 90, 50);
        startServerButton.setEnabled(false);
        startServerButton.addActionListener(e -> {
            if (e.getSource() == startServerButton && officeWindowListener != null) {
                officeWindowListener.setServerProperties(hostField.getText(), Integer.parseInt(portField.getText()));
                startServerButton.setVisible(false);
                stopServerButton.setVisible(true);
            }
        });

        this.add(startServerButton);
    }

    private void enableStartButton() {
        if (hostNumberFilled && portNumberFilled) {
            startServerButton.setEnabled(true);
        }
    }

    public void setOfficeFrameListener(OfficeWindowListener officeWindowListener) {
        this.officeWindowListener = officeWindowListener;
    }

    public void setStopButton() {
        stopServerButton = new JButton("STOP");
        stopServerButton.setBounds(210, 10, 90, 50);
        stopServerButton.setVisible(false);
        stopServerButton.addActionListener(e -> {
            if (e.getSource() == stopServerButton && officeWindowListener != null) {
                this.setVisible(false);
                officeWindowListener.stopServer();
            }
        });

        this.add(stopServerButton);
    }
}
