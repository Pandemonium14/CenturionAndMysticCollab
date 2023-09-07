package CenturionAndMystic.powers;

import CenturionAndMystic.MainModfile;
import CenturionAndMystic.damageMods.AbstractDamageType;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.DamageModApplyingPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Collections;
import java.util.List;

public abstract class AbstractInfusionPower extends AbstractPower implements DamageModApplyingPower {

    public AbstractInfusionPower(String ID, String name, AbstractCreature owner, int amount) {
        this.ID = ID;
        this.name = name;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        updateDescription();
    }

    abstract AbstractDamageType getDamageType();

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + (int)(100*amount* AbstractDamageType.PER_STACK) + DESCRIPTIONS[1];
    }

    @Override
    public void onAddedDamageModsToDamageInfo(DamageInfo info, Object instigator) {
        flash();
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public boolean shouldPushMods(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return o instanceof AbstractCard && ((AbstractCard) o).type == AbstractCard.CardType.ATTACK;
    }

    @Override
    public List<AbstractDamageModifier> modsToPush(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return Collections.singletonList(getDamageType());
    }
}