package terminals.officeTerminal;

import model.Tour;

import java.util.ArrayList;
import java.util.List;

public class OfficeTerminal implements OfficeTerminalViewListener {
    private final List<Tour> tours;

    public static void main(String[] args) {
        OfficeTerminal officeTerminal = new OfficeTerminal();
        OfficeTerminalView officeTerminalView = new OfficeTerminalView();
        officeTerminalView.setOfficeTerminalViewListener(officeTerminal);
    }

    public OfficeTerminal() {
        this.tours = new ArrayList<>();
    }

    @Override
    public void addTourOffer(Tour tour) {

    }

    @Override
    public void removeTourOffer(Tour tour) {

    }

    @Override
    public void getTourOffers() {

    }
}
