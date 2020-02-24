package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.NoOfferFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.OfferAlreadySubmittedException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.ProductNotFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.time.LocalDate;
import java.util.*;

public class ShoppingDirectoryImpl implements ShoppingDirectory {
    public static final int DAYS_TO_SUBTRACT = 30;
    private HashSet<Offer> offers = new HashSet<>();

    @Override
    public Collection<Offer> findAllOffers(String productName) throws ProductNotFoundException {
        if (productName == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<Offer> products = new ArrayList<>();

        for (Offer offer : offers) {
            if (offer.getProductName().equalsIgnoreCase(productName)
                    && offer.getDate().isAfter(LocalDate.now().minusDays(DAYS_TO_SUBTRACT))) {
                products.add(offer);
            }
        }
        if (products.size() == 0) {
            throw new ProductNotFoundException("No such product");
        }
        products.sort(Comparator.comparingDouble(Offer::getTotalPrice));
        return products;
    }

    @Override
    public Offer findBestOffer(String productName) throws ProductNotFoundException, NoOfferFoundException {
        boolean isThere = false;
        if (productName == null) {
            throw new IllegalArgumentException();
        }
        for (Offer offer : offers) {
            if (offer.getProductName().equalsIgnoreCase(productName)) {
                isThere = true;
                break;
            }
        }
        if (!isThere) {
            throw new ProductNotFoundException("No such product");
        }
        ArrayList<Offer> products = new ArrayList<>();
        for (Offer offer : offers) {
            if (offer.getProductName().equalsIgnoreCase(productName) &&
                    (offer.getDate().isEqual(LocalDate.now().minusDays(DAYS_TO_SUBTRACT))
                            || offer.getDate().isAfter(LocalDate.now().minusDays(DAYS_TO_SUBTRACT)))) {
                products.add(offer);
            }
        }
        if (products.size() == 0) {
            throw new NoOfferFoundException("No offers found");
        }
        products.sort((o1, o2) -> Double.compare(o1.getTotalPrice(), o2.getTotalPrice()));
        return products.get(0);
    }

    @Override
    public Collection<PriceStatistic> collectProductStatistics(String productName) throws ProductNotFoundException {
        if (productName == null) {
            throw new IllegalArgumentException("Product name is null");
        }
        boolean isThere = false;
        for (Offer offer : offers) {
            if (offer.getProductName().equalsIgnoreCase(productName)) {
                isThere = true;
                break;
            }
        }
        if (!isThere) {
            throw new ProductNotFoundException("No such product");
        }

        HashSet<Offer> products = new HashSet<>();
        for (Offer offer : offers) {
            if (offer.getProductName().equalsIgnoreCase(productName)) {
                products.add(offer);
            }
        }
        TreeSet<PriceStatistic> stats = new TreeSet<>((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        for (Offer o : products) {
            stats.add(new PriceStatistic(o.getDate(), products));
        }
        return stats;
    }

    @Override
    public void submitOffer(Offer offer) throws OfferAlreadySubmittedException {
        if (offers.contains(offer)) {
            throw new OfferAlreadySubmittedException("Offer already submitted");
        }
        if (offer == null) {
            throw new IllegalArgumentException("Offer is null");
        }
        offers.add(offer);
    }
}
