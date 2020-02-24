package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.time.LocalDate;
import java.util.Objects;


public class RegularOffer implements Offer {

    private String productName;
    private LocalDate date;
    private String description;
    private double price;
    private double shippingPrice;
    private double totalPrice;


    public RegularOffer(String productName, LocalDate date, String description, double price, double shippingPrice) {
        this.date = date;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.shippingPrice = shippingPrice;
        getTotalPrice();
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getShippingPrice() {
        return shippingPrice;
    }

    @Override
    public double getTotalPrice() {
        this.totalPrice = this.price + this.shippingPrice;
        return this.totalPrice;
    }

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
}
