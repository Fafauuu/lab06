package terminals;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class OfficeFrame extends JFrame {
    private OfficeFrameListener officeFrameListener;
    private JPanel panel;
    private JTextField hostField;
    private JTextField portField;
    private boolean hostNumberFilled;
    private boolean portNumberFilled;
    private JButton startServerButton;

    public OfficeFrame() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 400, 100);

        setHostField();
        setPortField();
        setStartButton();

        this.setResizable(false);
        this.setSize(400, 110);
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
            if (e.getSource() == startServerButton && officeFrameListener != null) {
                this.setVisible(false);
                officeFrameListener.startServer(hostField.getText(), Integer.parseInt(portField.getText()));
            }
        });

        this.add(startServerButton);
    }

    private void enableStartButton() {
        if (hostNumberFilled && portNumberFilled) {
            startServerButton.setEnabled(true);
        }
    }

    public void setOfficeFrameListener(OfficeFrameListener officeFrameListener) {
        this.officeFrameListener = officeFrameListener;
    }
}
