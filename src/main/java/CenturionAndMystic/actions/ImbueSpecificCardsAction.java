package CenturionAndMystic.actions;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ImbueSpecificCardsAction extends AbstractGameAction {
    private final List<AbstractCard> cards;
    private final AbstractCardModifier mod;
    private final Predicate<AbstractCard> filter;

    public ImbueSpecificCardsAction(List<AbstractCard> cards, AbstractCardModifier mod) {
        this(cards, mod, c -> true);
    }

    public ImbueSpecificCardsAction(List<AbstractCard> cards, AbstractCardModifier mod, Predicate<AbstractCard> filter) {
        this.cards = cards;
        this.mod = mod;
        this.filter = filter;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> validCards = cards.stream().filter(filter).collect(Collectors.toCollection(ArrayList::new));
        for (AbstractCard c : validCards) {
            ImbueCardsInHandAction.doInfusion(c, mod);
        }
        ImbueCardsInHandAction.infusionSFX();
        this.isDone = true;
    }
}
