package terminals.guideTerminal;

import model.Guide;

public interface GuideTerminalListener {
    void addGuide(Guide guide);
    void removeGuide(Guide guide);
    void startServer(String host, int port);
}
