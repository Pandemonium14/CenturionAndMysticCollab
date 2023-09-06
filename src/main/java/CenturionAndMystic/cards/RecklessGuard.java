package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.blue.AutoShields;
import com.megacrit.cardcrawl.cards.red.PowerThrough;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

import static CenturionAndMystic.MainModfile.makeID;

public class RecklessGuard extends AbstractCenturionCard {
    public final static String ID = makeID(RecklessGuard.class.getSimpleName());

    public RecklessGuard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = 12;
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, magicNumber));
        blck();
    }

    @Override
    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(-1);
    }

    @Override
    public String cardArtCopy() {
        return PowerThrough.ID;
    }

}