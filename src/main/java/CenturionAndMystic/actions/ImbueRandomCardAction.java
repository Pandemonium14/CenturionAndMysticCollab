package CenturionAndMystic.actions;

import CenturionAndMystic.cardmods.AbstractInfusion;
import CenturionAndMystic.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ImbueRandomCardAction extends AbstractGameAction {
    public static Predicate<AbstractCard> shenaniganFilter = c -> AbstractInfusion.usesVanillaTargeting(c) && c.costForTurn > -2;
    private final AbstractCardModifier mod;
    private final Predicate<AbstractCard> filter;

    public ImbueRandomCardAction(int amount, AbstractCardModifier mod) {
        this(amount, mod, c -> true);
    }

    public ImbueRandomCardAction(int amount, AbstractCardModifier mod, Predicate<AbstractCard> filter) {
        this.mod = mod;
        this.amount = amount;
        this.filter = filter.and(shenaniganFilter);
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> validCards = Wiz.adp().hand.group.stream().filter(filter).collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0 ; i < amount ; i++) {
            AbstractCard c = Wiz.getRandomItem(validCards);
            ImbueCardsInHandAction.doInfusion(c, mod);
        }
        ImbueCardsInHandAction.infusionSFX();
        this.isDone = true;
    }
}
