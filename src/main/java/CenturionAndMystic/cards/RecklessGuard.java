package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.red.PowerThrough;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class RecklessGuard extends AbstractCenturionCard {
    public final static String ID = makeID(RecklessGuard.class.getSimpleName());

    public RecklessGuard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = 11;
        //baseMagicNumber = magicNumber = 3;
        cardsToPreview = new Wound();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //addToBot(new LoseHPAction(p, p, magicNumber));
        blck();
        addToBot(new MakeTempCardInDrawPileAction(new Wound(), 1, true, true));
    }

    @Override
    public void upp() {
        upgradeBlock(4);
        //upgradeMagicNumber(-1);
    }

    @Override
    public String cardArtCopy() {
        return PowerThrough.ID;
    }

}