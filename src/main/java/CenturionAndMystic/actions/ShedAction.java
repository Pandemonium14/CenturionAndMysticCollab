package CenturionAndMystic.actions;

import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ShedAction extends BetterSelectCardsInHandAction {
    public ShedAction(int amount) {
        this(amount, c -> true, l -> {});
    }

    public ShedAction(int amount, Predicate<AbstractCard> filter) {
        this(amount, filter, l -> {});
    }

    public ShedAction(int amount, Consumer<List<AbstractCard>> callback) {
        this(amount, c -> true, callback);
    }

    public ShedAction(int amount, Predicate<AbstractCard> filter, Consumer<List<AbstractCard>> callback) {
        super(amount, ExhaustAction.TEXT[0], false, false, filter, l -> {
            for (AbstractCard c : l) {
                Wiz.adp().hand.moveToExhaustPile(c);
            }
            if (!l.isEmpty()) {
                Wiz.att(new DrawCardAction(l.size()));
            }
            callback.accept(l);
            l.clear();
        });
    }
}
