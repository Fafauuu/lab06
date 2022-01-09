package terminals.guideTerminal;

import model.Guide;
import model.Tour;

public interface GuideTerminalListener {
    void updateTourInfo(Tour tour);
    void addGuide(Guide guide);
    void removeGuide(Guide guide);
}
