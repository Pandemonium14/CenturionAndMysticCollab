package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.powers.TempleWithinPower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.purple.Sanctity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class TempleWithin extends AbstractMysticCard {
    public final static String ID = makeID(TempleWithin.class.getSimpleName());

    public TempleWithin() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new TempleWithinPower(p, magicNumber));
    }

    @Override
    public void upp() {
        //upgradeBaseCost(0);
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Sanctity.ID;
    }
}