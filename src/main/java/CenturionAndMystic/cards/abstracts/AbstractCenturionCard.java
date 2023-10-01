package CenturionAndMystic.cards.abstracts;

import CenturionAndMystic.MainModfile;
import CenturionAndMystic.patches.CustomTags;
import CenturionAndMystic.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;

public abstract class AbstractCenturionCard extends AbstractEasyCard {
    public static final Color CENTURION_ART_COLOR = colorFromHSL(20, 1f, 0.25f, 1.0f);
    public AbstractCenturionCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
        switch (type) {
            case ATTACK:
                this.setBackgroundTexture(MainModfile.ATTACK_S_CENTURION, MainModfile.ATTACK_L_CENTURION);
                break;
            case POWER:
                this.setBackgroundTexture(MainModfile.POWER_S_CENTURION, MainModfile.POWER_L_CENTURION);
                break;
            default:
                this.setBackgroundTexture(MainModfile.SKILL_S_CENTURION, MainModfile.SKILL_L_CENTURION);
                break;
        }
        setOrbTexture(MainModfile.CARD_ENERGY_S_CENTURION, MainModfile.CARD_ENERGY_L_CENTURION);
        tags.add(CustomTags.CAM_CENTURION_CARD);
    }

    public AbstractCenturionCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
        switch (type) {
            case ATTACK:
                this.setBackgroundTexture(MainModfile.ATTACK_S_CENTURION, MainModfile.ATTACK_L_CENTURION);
                break;
            case POWER:
                this.setBackgroundTexture(MainModfile.POWER_S_CENTURION, MainModfile.POWER_L_CENTURION);
                break;
            default:
                this.setBackgroundTexture(MainModfile.SKILL_S_CENTURION, MainModfile.SKILL_L_CENTURION);
                break;
        }
        setOrbTexture(MainModfile.CARD_ENERGY_S_CENTURION, MainModfile.CARD_ENERGY_L_CENTURION);
        tags.add(CustomTags.CAM_CENTURION_CARD);
    }

    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.5f, 0.5f, 0.5f, 0.5f, false);
        //return new CardArtRoller.ReskinInfo(ID, WHITE, BLACK, WHITE, CENTURION_ART_COLOR, false);
    }
}
