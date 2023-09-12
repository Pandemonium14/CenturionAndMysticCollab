package CenturionAndMystic.actions;

import CenturionAndMystic.powers.ComboPower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class StarterAction extends AbstractGameAction {

    public AbstractCard card;

    public StarterAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        if (!AbstractDungeon.player.hasPower(ComboPower.POWER_ID)) {
            addToTop(new ApplyPowerAction(Wiz.adp(),Wiz.adp(),new ComboPower(Wiz.adp(), 1, card)));
        }
        isDone = true;
    }
}
