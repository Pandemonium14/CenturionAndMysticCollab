package CenturionAndMystic.cards;

import CenturionAndMystic.actions.BetterSelectCardsInHandAction;
import CenturionAndMystic.actions.CallCardAction;
import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.EscapePlan;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Strategize extends AbstractMysticCard {
    public final static String ID = makeID(Strategize.class.getSimpleName());

    public Strategize() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BetterSelectCardsInHandAction(p.hand.size(), DiscardAction.TEXT[0], true, true, c -> true, l -> {
            for (AbstractCard c : l) {
                p.hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
                if (Wiz.isCenturionCard(c)) {
                    addToBot(new CallCardAction(1, Wiz::isMysticCard));
                } else {
                    addToBot(new CallCardAction(1, Wiz::isCenturionCard));
                }
            }
            l.clear();
        }));
    }

    @Override
    public void upp() {
        exhaust = false;
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return EscapePlan.ID;
    }
}