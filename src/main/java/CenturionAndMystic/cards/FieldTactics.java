package CenturionAndMystic.cards;

import CenturionAndMystic.actions.CallCardAction;
import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.colorless.MasterOfStrategy;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import static CenturionAndMystic.MainModfile.makeID;

public class FieldTactics extends AbstractCenturionCard {
    public final static String ID = makeID(FieldTactics.class.getSimpleName());

    public FieldTactics() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new CallCardAction(1, Wiz::isCenturionCard));
        addToBot(new CallCardAction(1, Wiz::isMysticCard));
        Wiz.applyToSelf(new DrawCardNextTurnPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return MasterOfStrategy.ID;
    }

}