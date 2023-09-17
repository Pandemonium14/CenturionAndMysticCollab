package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.powers.IndefatigablePower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.green.Dash;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Indefatigable extends AbstractCenturionCard {
    public final static String ID = makeID(Indefatigable.class.getSimpleName());

    public Indefatigable() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new IndefatigablePower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBaseCost(1);
    }

    @Override
    public String cardArtCopy() {
        return Dash.ID;
    }
}