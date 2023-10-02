package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.powers.CursedFrailPower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.green.Setup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static CenturionAndMystic.MainModfile.makeID;

public class HexOfTheFrail extends AbstractMysticCard {
    public final static String ID = makeID(HexOfTheFrail.class.getSimpleName());

    public HexOfTheFrail() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        Wiz.applyToEnemy(m, new CursedFrailPower(m, 1));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Setup.ID;
    }
}