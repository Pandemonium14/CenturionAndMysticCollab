package CenturionAndMystic.cards;

import CenturionAndMystic.actions.SurveyAction;
import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.purple.Foresight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Clairvoyance extends AbstractMysticCard {
    public final static String ID = makeID(Clairvoyance.class.getSimpleName());

    public Clairvoyance() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new SurveyAction(magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Foresight.ID;
    }
}