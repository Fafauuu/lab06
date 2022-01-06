package terminals.guideTerminal;

public interface GuideTerminalListener {
    void updateTourInfo(String tourInfo);
    void addGuide(String guideInfo, String host, String port);
    void removeGuide(String guideInfo, String host, String port);
}
