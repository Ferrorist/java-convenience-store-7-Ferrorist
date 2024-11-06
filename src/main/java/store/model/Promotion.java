package store.model;

import java.util.Objects;

public class Promotion {
    private String name;
    private int buyQuantity;
    private int freeQuantity;

    private String startDate;
    private String endDate;

    public Promotion(String name, int buyQuantity, int freeQuantity, String startDate, String endDate) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.freeQuantity = freeQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Promotion promotion = (Promotion) o;
        return this.hashCode() == promotion.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, buyQuantity, freeQuantity, startDate, endDate);
    }
}
