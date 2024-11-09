package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Promotion {
    private String name;
    private int buyQuantity;
    private int freeQuantity;

    private LocalDate startDate;
    private LocalDate endDate;

    public Promotion(String name, int buyQuantity, int freeQuantity, String startDate, String endDate) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.freeQuantity = freeQuantity;
        this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
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

    public boolean checkPeriod(LocalDateTime date) {
        return checkPeriod(date.toLocalDate());
    }
    public boolean checkPeriod(LocalDate date) {
        return date.compareTo(startDate) >= 0 && endDate.compareTo(date) >= 0;
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
