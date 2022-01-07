package terminals.officeTerminal;

import model.Tour;

public interface OfficeTerminalViewListener {
    void addTourOffer(Tour tour);
    void removeTourOffer(Tour tour);
    void getTourOffers();
}
