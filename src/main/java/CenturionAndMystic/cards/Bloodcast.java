package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.red.Bloodletting;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;

import static CenturionAndMystic.MainModfile.makeID;

public class Bloodcast extends AbstractMysticCard {
    public final static String ID = makeID(Bloodcast.class.getSimpleName());

    public Bloodcast() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        cardsToPreview = new Wound();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.makeInHand(new Wound());
        Wiz.applyToSelf(new EnergizedBluePower(p, magicNumber));
        //Wiz.applyToSelf(new FreeAttackPower(p, 1));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(1);
        upgradeBaseCost(0);
    }

    @Override
    public String cardArtCopy() {
        return Bloodletting.ID;
    }
}