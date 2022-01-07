package terminals.officeTerminal;

import model.Tour;

public interface OfficeTerminalWindowListener {
    void addTourOffer(Tour tour);
    void removeTourOffer(Tour tour);
    void getTourOffers();
}
