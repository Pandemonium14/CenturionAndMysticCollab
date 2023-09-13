package CenturionAndMystic.cards.abstracts;

import CenturionAndMystic.MainModfile;
import CenturionAndMystic.patches.CustomTags;
import CenturionAndMystic.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;

public abstract class AbstractMysticCard extends AbstractEasyCard {
    public static final Color MYSTIC_ART_COLOR = mix(mix(BLACK, RED), darken(RED));
    public AbstractMysticCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
        switch (type) {
            case ATTACK:
                this.setBackgroundTexture(MainModfile.ATTACK_S_MYSTIC, MainModfile.ATTACK_L_MYSTIC);
                break;
            case POWER:
                this.setBackgroundTexture(MainModfile.POWER_S_MYSTIC, MainModfile.POWER_L_MYSTIC);
                break;
            default:
                this.setBackgroundTexture(MainModfile.SKILL_S_MYSTIC, MainModfile.SKILL_L_MYSTIC);
                break;
        }
        setOrbTexture(MainModfile.CARD_ENERGY_S_MYSTIC, MainModfile.CARD_ENERGY_L_MYSTIC);
        tags.add(CustomTags.CAM_MYSTIC_CARD);
    }

    public AbstractMysticCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
        switch (type) {
            case ATTACK:
                this.setBackgroundTexture(MainModfile.ATTACK_S_MYSTIC, MainModfile.ATTACK_L_MYSTIC);
                break;
            case POWER:
                this.setBackgroundTexture(MainModfile.POWER_S_MYSTIC, MainModfile.POWER_L_MYSTIC);
                break;
            default:
                this.setBackgroundTexture(MainModfile.SKILL_S_MYSTIC, MainModfile.SKILL_L_MYSTIC);
                break;
        }
        setOrbTexture(MainModfile.CARD_ENERGY_S_MYSTIC, MainModfile.CARD_ENERGY_L_MYSTIC);
        tags.add(CustomTags.CAM_MYSTIC_CARD);
    }

    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, WHITE, BLACK, WHITE, MYSTIC_ART_COLOR, false);
    }
}
