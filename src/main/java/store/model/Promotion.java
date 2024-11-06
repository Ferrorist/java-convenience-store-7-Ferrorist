package store.model;

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
}
