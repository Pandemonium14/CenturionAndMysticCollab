package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.red.Entrench;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;

import static CenturionAndMystic.MainModfile.makeID;

public class PileUp extends AbstractCenturionCard {
    public final static String ID = makeID(PileUp.class.getSimpleName());

    public PileUp() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = 12;
        baseMagicNumber = magicNumber = 2;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        //addToBot(new GainEnergyAction(magicNumber));
        Wiz.applyToSelf(new EnergizedBluePower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBlock(4);
        //upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Entrench.ID;
    }

}