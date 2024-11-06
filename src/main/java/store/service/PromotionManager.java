package store.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.factory.PromotionFactory;
import store.model.Promotion;
import util.MarkDownUtils;

public class PromotionManager {

    private static final String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";
    private static PromotionManager instance;

    private List<Promotion> promotions;
    private Map<String, Promotion> promotionsByNames;

    private PromotionManager() {
        promotions = null;
        promotionsByNames = null;
    }

    public static PromotionManager getInstance() {
        if (instance == null) {
            instance = new PromotionManager();
        }
        return instance;
    }

    public List<Promotion> getPromotions() {
        initPromotions();
        return promotions;
    }

    public Promotion searchPromotionByName(String promotionName) {
        initPromotions();
        Promotion promotion = promotionsByNames.getOrDefault(promotionName, null);
        if(!(promotionName == null || promotionName.equals("null")) && promotion == null)   {
            System.out.println("[ERROR] 찾을 수 없는 프로모션 입니다.");
        }
        return promotion;
    }

    private void initPromotions() {
        if (promotions == null) {
            promotions = new ArrayList<>();
            promotionsByNames = new HashMap<>();
            List<String> promotionLines = MarkDownUtils.readMarkDownFile(PROMOTIONS_FILE_PATH);
            for (String line : promotionLines) {
                Promotion promotion = PromotionFactory.createPromotion(line);
                promotions.add(promotion);
                promotionsByNames.put(promotion.getName(), promotion);
            }
        }
    }
}
