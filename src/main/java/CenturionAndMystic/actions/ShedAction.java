package CenturionAndMystic.actions;

import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ShedAction extends BetterSelectCardsInHandAction {
    public ShedAction(int amount) {
        super(amount, ExhaustAction.TEXT[0], false, false, c -> true, l -> {
            for (AbstractCard c : l) {
                Wiz.adp().hand.moveToExhaustPile(c);
            }
            if (!l.isEmpty()) {
                Wiz.att(new DrawCardAction(l.size()));
            }
            l.clear();
        });
    }
}
