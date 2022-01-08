package terminals.touristTerminal;

public interface TouristTerminalListener {
    void registerForTour(String tourist, String tour);
    void unregisterFromTour(String tourist, String tour);
    void getTourOffers();
}
