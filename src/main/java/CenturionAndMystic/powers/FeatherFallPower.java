package CenturionAndMystic.powers;

import CenturionAndMystic.MainModfile;
import CenturionAndMystic.util.Wiz;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

public class FeatherFallPower extends AbstractPower {
    public static final String POWER_ID = MainModfile.makeID(FeatherFallPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FeatherFallPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("ritual");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (amount > 0) {
            flash();
            amount--;
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    this.isDone = AbstractDungeon.effectList.stream().noneMatch(e -> e instanceof ExhaustCardEffect && ReflectionHacks.getPrivate(e, ExhaustCardEffect.class, "c") == card);
                    if (isDone) {
                        addToTop(new MoveCardsAction(Wiz.adp().discardPile, Wiz.adp().exhaustPile, c -> c == card));
                    }
                }
            });
            if (amount > 0) {
                updateDescription();
            } else {
                addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            }
        }
    }
}