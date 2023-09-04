package CenturionAndMystic.util;

import CenturionAndMystic.potions.LimitBreaker;
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod;

public class WidePotionLoader {
    public static void loadCrossoverContent() {
        WidePotionsMod.whitelistSimplePotion(LimitBreaker.POTION_ID);
    }
}
