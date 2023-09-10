package CenturionAndMystic.powers;

import CenturionAndMystic.MainModfile;
import CenturionAndMystic.patches.EnterCardGroupPatches;
import CenturionAndMystic.powers.interfaces.OnCreateCardPower;
import CenturionAndMystic.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.DamageModApplyingPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.List;

public class SnakeEyesPower extends AbstractPower implements NonStackablePower, EnterCardGroupPatches.OnEnterCardGroupPower, OnCreateCardPower, DamageModApplyingPower {
    public static final String POWER_ID = MainModfile.makeID(SnakeEyesPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SnakeEyesPower(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        this.loadRegion("confusion");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onInitialApplication() {
        for (AbstractCard c : Wiz.getAllCardsInCardGroups(true, true)) {
            changeCard(c);
        }
    }

    public void changeCard(AbstractCard card) {
        if (Wiz.isInCombat()) {
            if (card.cost >= 0) {
                if (card.cost != 1) {
                    card.cost = 1;
                    card.costForTurn = card.cost;
                    card.isCostModified = true;
                }
                card.freeToPlayOnce = false;
            }
        }
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        changeCard(card);
    }

    @Override
    public void onEnter(AbstractCard card, CardGroup group) {
        if (group == Wiz.adp().hand || group == Wiz.adp().discardPile || group == Wiz.adp().drawPile || group == Wiz.adp().exhaustPile || group == Wiz.adp().limbo) {
            changeCard(card);
        }
    }

    @Override
    public void onCreateCard(AbstractCard card) {
        changeCard(card);
    }

    @Override
    public void onGenerateCardOption(AbstractCard card) {
        changeCard(card);
    }

    @Override
    public boolean shouldPushMods(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        if (o instanceof AbstractCard) {
            changeCard((AbstractCard) o);
        }
        return false;
    }

    @Override
    public List<AbstractDamageModifier> modsToPush(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return null;
    }
}