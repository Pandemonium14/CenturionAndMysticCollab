package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.powers.KillingIntentPower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.red.Berserk;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class KillingIntent extends AbstractCenturionCard {
    public final static String ID = makeID(KillingIntent.class.getSimpleName());

    public KillingIntent() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new KillingIntentPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
        //updateCost(1);
    }

    @Override
    public String cardArtCopy() {
        return Berserk.ID;
    }
}