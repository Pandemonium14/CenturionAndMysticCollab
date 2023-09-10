package CenturionAndMystic.cards;

import CenturionAndMystic.actions.SnakeEyesAction;
import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.red.SeeingRed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class SnakeEyes extends AbstractCenturionCard {
    public final static String ID = makeID(SnakeEyes.class.getSimpleName());

    public SnakeEyes() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new SnakeEyesAction());
    }

    @Override
    public void upp() {
        upgradeBaseCost(1);
    }

    @Override
    public String cardArtCopy() {
        return SeeingRed.ID;
    }
}