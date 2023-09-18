package CenturionAndMystic.cards;

import CenturionAndMystic.actions.CallCardAction;
import CenturionAndMystic.cards.abstracts.AbstractEasyCard;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Doppelganger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Teamwork extends AbstractEasyCard {
    public final static String ID = makeID(Teamwork.class.getSimpleName());

    public Teamwork() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        superFlash();
        if (Wiz.isCenturionCard(c)) {
            addToBot(new CallCardAction(1, Wiz::isMysticCard));
        } else {
            addToTop(new CallCardAction(1, Wiz::isCenturionCard));
        }
    }

    @Override
    public void upp() {
        isEthereal = false;
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return Doppelganger.ID;
    }
}