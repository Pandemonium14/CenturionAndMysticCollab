package CenturionAndMystic.potions;

import CenturionAndMystic.MainModfile;
import CenturionAndMystic.actions.ModifyCardsInHandAction;
import CenturionAndMystic.patches.ForcedUpgradesPatches;
import CenturionAndMystic.util.CustomLighting;
import CenturionAndMystic.util.KeywordManager;
import CenturionAndMystic.util.Wiz;
import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

public class LimitBreaker extends CustomPotion implements CustomLighting {
    public static final String POTION_ID = MainModfile.makeID(LimitBreaker.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    public static final int EFFECT = 1;

    public LimitBreaker() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.BOTTLE, PotionColor.FIRE);
        isThrown = false;
        targetRequired = false;
    }

    @Override
    public void use(AbstractCreature target) {
        addToBot(new VFXAction(Wiz.adp(), new InflameEffect(Wiz.adp()), 0.5F));
        addToBot(new ModifyCardsInHandAction(Wiz.adp().hand.size(), l -> {
            for (AbstractCard card : l) {
                ForcedUpgradesPatches.applyUnlockIfNeeded(card);
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(card.hb.cX, card.hb.cY));
                card.superFlash();
                card.applyPowers();
                for (int i = 0 ; i < potency ; i++) {
                    card.upgrade();
                }
            }
        }));
    }

    // This is your potency.
    @Override
    public int getPotency(final int ascensionLevel) {
        return EFFECT;
    }

    @Override
    public void initializeData() {
        potency = getPotency();
        if (potency == 1) {
            description = potionStrings.DESCRIPTIONS[0];
        } else {
            description = potionStrings.DESCRIPTIONS[1] + potency + potionStrings.DESCRIPTIONS[2];
        }
        tips.clear();
        tips.add(new PowerTip(name, description));
        //tips.add(new PowerTip(BaseMod.getKeywordTitle(KeywordManager.UNLOCK), BaseMod.getKeywordDescription(KeywordManager.UNLOCK)));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.UPGRADE.NAMES[0]), GameDictionary.keywords.get(GameDictionary.UPGRADE.NAMES[0])));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new LimitBreaker();
    }

    @Override
    public float[] _lightsOutGetXYRI() {
        return new float[] {posX, posY, 100f*Settings.scale, 0.8f};
    }

    @Override
    public Color[] _lightsOutGetColor() {
        return new Color[] {Color.GOLD};
    }
}
