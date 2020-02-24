package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

class PriceStatistic {
    private HashSet<Offer> offers;
    private LocalDate date;
    private double lowestPrice;
    private double averagePrice;


    PriceStatistic(LocalDate date, HashSet<Offer> offer) {
        this.date = date;
        this.offers = offer;
        lowestPrice = getLowestPrice();
        averagePrice = getAveragePrice();
    }


    public LocalDate getDate() {
        return date;
    }

    public double getLowestPrice() {

        ArrayList<Offer> specificOffers = new ArrayList<>();
        for (Offer o : offers) {
            if (o.getDate().isEqual(getDate())) {
                specificOffers.add(o);
            }
        }
        specificOffers.sort((o1, o2) -> Double.compare(o1.getTotalPrice(), o2.getTotalPrice()));

        return specificOffers.get(0).getTotalPrice();
    }

    public double getAveragePrice() {
        ArrayList<Offer> specificOffers = new ArrayList<>();
        for (Offer o : offers) {
            if (o.getDate().equals(getDate())) {
                specificOffers.add(o);
            }
        }
        int averagePrice = 0;
        for (Offer o : specificOffers) {
            averagePrice += o.getTotalPrice();
        }
        return averagePrice / specificOffers.size();
    }
}
