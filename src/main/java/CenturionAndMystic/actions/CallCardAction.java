package CenturionAndMystic.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CallCardAction extends AbstractGameAction {
    private final int amount;
    private final Predicate<AbstractCard> filter;
    private final Consumer<List<AbstractCard>> callback;
    private final List<AbstractCard> cardsMoved = new ArrayList<>();

    public CallCardAction(int amount, Predicate<AbstractCard> filter) {
        this(amount, filter, c -> {});
    }

    public CallCardAction(int amount, Predicate<AbstractCard> filter, Consumer<List<AbstractCard>> callback) {
        this.amount = amount;
        this.filter = filter;
        this.callback = callback;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> validCards = new ArrayList<>();
        boolean fromDiscardPile = false;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (filter.test(c)) {
                validCards.add(c);
            }
        }
        if (validCards.size() == 0) {
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (filter.test(c)) {
                    validCards.add(c);
                }
            }
            fromDiscardPile = true;
        }
        int cardsGot = 0;
        while (validCards.size() != 0 && AbstractDungeon.player.hand.group.size() < BaseMod.MAX_HAND_SIZE && cardsGot < amount) {
            AbstractCard card = validCards.get(AbstractDungeon.cardRng.random(validCards.size()-1));
            if (fromDiscardPile) {
                AbstractDungeon.player.hand.moveToHand(card, AbstractDungeon.player.discardPile);
            } else {
                AbstractDungeon.player.hand.moveToHand(card, AbstractDungeon.player.drawPile);
            }
            cardsMoved.add(card);
            validCards.remove(card);
            cardsGot++;
        }
        callback.accept(cardsMoved);
        isDone = true;
    }
}