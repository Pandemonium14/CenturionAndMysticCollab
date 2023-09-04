package CenturionAndMystic.util;

import CenturionAndMystic.CenturionAndMystic;
import CenturionAndMystic.cards.abstracts.AbstractEasyCard;
import CenturionAndMystic.potions.LimitBreaker;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;

public class PotionLoader {
    public static void loadContent() {
        BaseMod.addPotion(LimitBreaker.class, Color.GOLD.cpy(), Color.GOLDENROD.cpy(), AbstractEasyCard.lighten(Color.GOLD.cpy()), LimitBreaker.POTION_ID, CenturionAndMystic.Enums.CENTURION_AND_MYSTIC);
    }
}
