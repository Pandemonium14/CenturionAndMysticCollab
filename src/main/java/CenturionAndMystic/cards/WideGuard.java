package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.red.Sentinel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import static CenturionAndMystic.MainModfile.makeID;

public class WideGuard extends AbstractCenturionCard {
    public final static String ID = makeID(WideGuard.class.getSimpleName());

    public WideGuard() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (block >= 1) {
            Wiz.applyToSelf(new NextTurnBlockPower(p, block));
        }
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }

    @Override
    public String cardArtCopy() {
        return Sentinel.ID;
    }

}