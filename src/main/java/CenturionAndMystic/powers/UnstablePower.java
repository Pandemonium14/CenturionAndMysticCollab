package CenturionAndMystic.powers;

import CenturionAndMystic.MainModfile;
import CenturionAndMystic.util.PowerIconMaker;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class UnstablePower extends AbstractPower {
    public static final String POWER_ID = MainModfile.makeID(UnstablePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean appliedOffTurn;

    // TODO ideas:
    //Stores damage received, explodes at the start of its next turn?
    public UnstablePower(AbstractCreature owner, int amount) {
        this(owner, amount, false);
    }

    public UnstablePower(AbstractCreature owner, int amount, boolean appliedOffTurn) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.priority = -1;
        this.isTurnBased = true;
        this.appliedOffTurn = appliedOffTurn;
        PowerIconMaker.setIcons(this, "MosaicHope");
        updateDescription();
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if (damageType == DamageInfo.DamageType.NORMAL) {
            return damage + amount;
        }
        return damage;
    }

    @Override
    public void atEndOfRound() {
        if (!appliedOffTurn) {
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }
        appliedOffTurn = false;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}