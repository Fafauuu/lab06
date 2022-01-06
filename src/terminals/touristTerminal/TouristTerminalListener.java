package terminals.touristTerminal;

public interface TouristTerminalListener {
    void registerForTour(String tour, String tourist);
    void unregisterFromTour(String tour, String tourist);
    void getTourOffers();
}
