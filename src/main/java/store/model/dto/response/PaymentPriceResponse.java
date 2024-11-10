package store.model.dto.response;

import java.math.BigDecimal;
import util.StringUtils;

public class PaymentPriceResponse {
    private BigDecimal purchaseAmount;
    private int purchaseQuantity;
    private BigDecimal applyPromotion;
    private BigDecimal applyMembership;
    private BigDecimal actualAmount = BigDecimal.ZERO;

    public PaymentPriceResponse() {
        this.purchaseAmount = BigDecimal.ZERO;
        this.purchaseQuantity = 0;
        this.applyPromotion = BigDecimal.ZERO;
        this.applyMembership = BigDecimal.ZERO;
    }
    public PaymentPriceResponse(int purchaseAmount, int purchaseQuantity, int applyPromotion, int applyMembership) {
        this.purchaseAmount = new BigDecimal(purchaseAmount);
        this.purchaseQuantity = purchaseQuantity;
        this.applyPromotion = new BigDecimal(applyPromotion);
        this.applyMembership = new BigDecimal(applyMembership);
        calcActualAmount();
    }



    public void calcActualAmount() {
        this.actualAmount = purchaseAmount.subtract(applyPromotion).subtract(applyMembership);
    }

    public BigDecimal getPurchaseAmount() {
        return purchaseAmount;
    }

    public BigDecimal getApplyPromotion() {
        return applyPromotion;
    }

    public BigDecimal getApplyMembership() {
        return applyMembership;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public int getPurchaseAmountIntValue() {
        return purchaseAmount.intValue();
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public int getApplyPromotionIntValue() {
        return applyPromotion.intValue();
    }

    public int getApplyMemberShipIntValue() {
        return applyMembership.intValue();
    }

    public int getActualAmountIntValue() {
        return actualAmount.intValue();
    }

    public String getPurchaseAmountString() {
        return StringUtils.convertToDecimalFormat(purchaseAmount);
    }

    public String getApplyPromotionString() {
        return StringUtils.convertToDecimalFormat(applyPromotion);
    }

    public String getApplyMemberShipString() {
        return StringUtils.convertToDecimalFormat(applyMembership);
    }

    public String getActualAmountString() {
        return StringUtils.convertToDecimalFormat(actualAmount);
    }

    public void setPurchaseAmount(int amount) {
        setPurchaseAmount(new BigDecimal(amount));
    }

    public void setPurchaseAmount(BigDecimal amount){
        this.purchaseAmount = amount;
    }

    public void setPurchaseQuantity(int quantity) {
        this.purchaseQuantity = quantity;
    }

    public void setApplyPromotion(int applyPromotion) {
        setApplyPromotion(new BigDecimal(applyPromotion));
    }

    public void setApplyPromotion(BigDecimal applyPromotion) {
        this.applyPromotion = applyPromotion;
    }

    public void setApplyMembership(int applyMembership) {
        setApplyMembership(new BigDecimal(applyMembership));
    }

    public void setApplyMembership(BigDecimal applyMembership) {
        this.applyMembership = applyMembership;
    }

    @Override
    public String toString() {
        return "PaymentResponse{" +
                "purchaseAmount=" + purchaseAmount.intValue() +
                ", purchaseQuantity=" + purchaseQuantity +
                ", applyPromotion=" + applyPromotion.intValue() +
                ", applyMembership=" + applyMembership.intValue() +
                ", actualAmount=" + actualAmount.intValue() +
                '}';
    }
}
