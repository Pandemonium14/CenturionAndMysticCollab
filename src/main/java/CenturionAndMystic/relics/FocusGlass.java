package CenturionAndMystic.relics;

import CenturionAndMystic.CenturionAndMystic;
import CenturionAndMystic.powers.FocusedPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static CenturionAndMystic.MainModfile.makeID;

public class FocusGlass extends AbstractEasyRelic {
    public static final String ID = makeID(FocusGlass.class.getSimpleName());

    public FocusGlass() {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK, CenturionAndMystic.Enums.SHADOW_BLUE_COLOR);
    }

    public void atTurnStart() {
        this.counter = 0;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            ++this.counter;
            if (this.counter % 3 == 0) {
                this.counter = 0;
                this.flash();
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusedPower(AbstractDungeon.player, 1)));
            }
        }
    }

    public void onVictory() {
        this.counter = -1;
    }
}
