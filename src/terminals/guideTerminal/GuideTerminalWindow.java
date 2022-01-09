package terminals.guideTerminal;

import model.Guide;
import model.Tourist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class GuideTerminalWindow extends JFrame {
    private GuideTerminalListener guideTerminalListener;
    private JPanel mainPanel;
    private JPanel serverResponsePanel;
    private JLabel serverResponseLabel;
    private JButton addGuideButton;
    private JButton removeGuideButton;
    private JPanel guideInfoPanel;
    private JTextField guideNameTextField;
    private JTextField guideSurnameTextField;
    private boolean nameFilled;
    private boolean surnameFilled;
    private JPanel tourInfoPanel;
    private JLabel tourDescriptionLabel;
    private Guide guide;

    public GuideTerminalWindow(){
        this.setTitle("GUIDE TERMINAL");

        mainPanel = new JPanel();
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
                if (!name.isEmpty() && !surname.isEmpty()){
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
//                guideTerminalListener.removeGuide();
            }
        });
        this.add(removeGuideButton);
    }

    private void setGuideInfoTextPanel() {
        guideInfoPanel = new JPanel();
        guideInfoPanel.setLayout(null);
        guideInfoPanel.setBounds(150,250,300,50);
        setGuideNameTextField();
        setGuideSurnameTextField();
        this.add(guideInfoPanel);
    }

    private void setGuideSurnameTextField() {
        guideSurnameTextField = new JTextField("surname");
        guideSurnameTextField.setBounds(155,5,140,40);
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

    private void setGuideNameTextField() {
        guideNameTextField = new JTextField("name");
        guideNameTextField.setBounds(5,5,140,40);
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

    private void enableRegistrationButtons() {
        if (nameFilled && surnameFilled){
            addGuideButton.setEnabled(true);
            removeGuideButton.setEnabled(true);
        }
    }

    private void setServerResponsePanel() {
        serverResponsePanel = new JPanel();
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

    public void setGuideTerminalListener(GuideTerminalListener guideTerminalListener) {
        this.guideTerminalListener = guideTerminalListener;
    }
}
