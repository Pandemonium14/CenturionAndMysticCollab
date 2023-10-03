package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.powers.HeavyPlatingPower;
import CenturionAndMystic.powers.NextTurnPowerPower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.BattleHymn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class HeavyMetal extends AbstractCenturionCard {
    public final static String ID = makeID(HeavyMetal.class.getSimpleName());

    public HeavyMetal() {
        super(ID, 1, AbstractCard.CardType.SKILL, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new HeavyPlatingPower(p, magicNumber));
        Wiz.applyToSelf(new NextTurnPowerPower(p, new HeavyPlatingPower(p, magicNumber)));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public String cardArtCopy() {
        return BattleHymn.ID;
    }
}