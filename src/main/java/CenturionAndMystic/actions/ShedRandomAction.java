package CenturionAndMystic.actions;

import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ShedRandomAction extends AbstractGameAction {
    private final Predicate<AbstractCard> filter;
    private final Consumer<List<AbstractCard>> callback;

    public ShedRandomAction(int amount) {
        this(amount, c -> true, l -> {});
    }

    public ShedRandomAction(int amount, Predicate<AbstractCard> filter) {
        this(amount, filter, l -> {});
    }

    public ShedRandomAction(int amount, Consumer<List<AbstractCard>> callback) {
        this(amount, c -> true, callback);
    }

    public ShedRandomAction(int amount, Predicate<AbstractCard> filter, Consumer<List<AbstractCard>> callback) {
        this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
        this.amount = amount;
        this.filter = filter;
        this.callback = callback;
    }

    @Override
    public void update() {
        CardGroup validCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : Wiz.adp().hand.group) {
            if (filter.test(c)) {
                validCards.group.add(c);
            }
        }
        if (amount >= validCards.size()) {
            for (AbstractCard c : validCards.group) {
                Wiz.adp().hand.moveToExhaustPile(c);
            }
            if (!validCards.isEmpty()) {
                Wiz.att(new DrawCardAction(validCards.size()));
            }
            callback.accept(validCards.group);
        } else {
            ArrayList<AbstractCard> shedCards = new ArrayList<>();
            for (int i = 0 ; i < amount ; i++) {
                AbstractCard card = validCards.getRandomCard(true);
                Wiz.adp().hand.moveToExhaustPile(card);
                shedCards.add(card);
            }
            if (!shedCards.isEmpty()) {
                Wiz.att(new DrawCardAction(shedCards.size()));
            }
            callback.accept(shedCards);
        }
        this.isDone = true;
    }
}
