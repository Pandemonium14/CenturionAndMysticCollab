package CenturionAndMystic.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static CenturionAndMystic.MainModfile.makeID;

public class HeavyPlatingPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(HeavyPlatingPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = strings.NAME;
    public static final String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public HeavyPlatingPower(AbstractCreature owner, int amount) {
        super(NAME, PowerType.BUFF, true, owner, amount);
        this.ID = POWER_ID;
        this.loadRegion("heatsink");
        this.priority = 25;
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        if (damage >= amount) {
            return damage - amount;
        }
        return 0;
    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
