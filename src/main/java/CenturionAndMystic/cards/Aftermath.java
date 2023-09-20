package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.powers.AftermathPower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.green.NoxiousFumes;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Aftermath extends AbstractMysticCard {
    public final static String ID = makeID(Aftermath.class.getSimpleName());

    public Aftermath() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new AftermathPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(3);
    }

    @Override
    public String cardArtCopy() {
        return NoxiousFumes.ID;
    }
}