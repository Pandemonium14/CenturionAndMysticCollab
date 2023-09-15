package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Impervious;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static CenturionAndMystic.MainModfile.makeID;

public class Indomitable extends AbstractCenturionCard {
    public final static String ID = makeID(Indomitable.class.getSimpleName());

    public Indomitable() {
        super(ID, 2, AbstractCard.CardType.SKILL, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
        baseBlock = block = 18;
        baseMagicNumber = magicNumber = 2;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.applyToSelf(new ArtifactPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBlock(6);
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Impervious.ID;
    }
}