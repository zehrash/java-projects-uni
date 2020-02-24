package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.time.LocalDate;
import java.util.Objects;

public class PremiumOffer implements Offer {

    public static final double MAX_VALUE = 100.0;
    public static final int MIN = 0;
    public static final int MAX = 100;
    private String productName;
    private LocalDate date;
    private String description;
    private double price;
    private double shippingPrice;
    private double discount;
    private double totalPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Offer that = (Offer) o;
        return Double.compare(that.getTotalPrice(), totalPrice) == 0 &&
                productName.toLowerCase().equals(that.getProductName().toLowerCase()) &&
                date.equals(that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName.toLowerCase(), date, totalPrice);
    }

    public double setDiscount(double discount) {
        if (discount < MIN || discount > MAX) {
            throw new IllegalArgumentException();
        }
        return Math.round(discount * MAX_VALUE) / MAX_VALUE;
    }

    public PremiumOffer(String productName, LocalDate date, String description,
                        double price, double shippingPrice, double discount) {
        this.productName = productName;
        this.date = date;
        this.description = description;
        this.price = price;
        this.shippingPrice = shippingPrice;
        this.discount = setDiscount(discount);
        getTotalPrice();

    }

    @Override
    public String getProductName() {
        return this.productName;
    }

    @Override
    public LocalDate getDate() {

        return this.date;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public double getShippingPrice() {
        return this.shippingPrice;
    }

    @Override
    public double getTotalPrice() {
        this.totalPrice = this.shippingPrice + this.price -
                (this.shippingPrice + this.price) * (this.discount / MAX_VALUE);
        return this.totalPrice;
    }
}
