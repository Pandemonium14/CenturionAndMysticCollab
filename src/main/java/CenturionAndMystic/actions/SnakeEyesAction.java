package CenturionAndMystic.actions;

import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class SnakeEyesAction extends AbstractGameAction {
    @Override
    public void update() {
        for (AbstractCard card : Wiz.getAllCardsInCardGroups(true, true)) {
            if (card.cost >= 0) {
                if (card.cost != 1) {
                    card.cost = 1;
                    card.costForTurn = card.cost;
                    card.isCostModified = true;
                    if (Wiz.adp().hand.contains(card)) {
                        card.superFlash();
                    }
                }
                card.freeToPlayOnce = false;
            }
        }
        this.isDone = true;
    }
}
