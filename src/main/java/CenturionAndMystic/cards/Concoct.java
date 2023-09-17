package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.powers.ConcoctPower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.green.Alchemize;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Concoct extends AbstractMysticCard {
    public final static String ID = makeID(Concoct.class.getSimpleName());

    public Concoct() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new ConcoctPower(p, magicNumber));
    }

    @Override
    public void upp() {
        //upgradeBaseCost(0);
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Alchemize.ID;
    }
}