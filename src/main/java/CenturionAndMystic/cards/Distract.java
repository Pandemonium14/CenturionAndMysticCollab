package CenturionAndMystic.cards;

import CenturionAndMystic.actions.CallCardAction;
import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.patches.CustomTags;
import com.megacrit.cardcrawl.cards.green.Distraction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Distract extends AbstractCenturionCard {
    public final static String ID = makeID(Distract.class.getSimpleName());

    public Distract() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = 7;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        addToBot(new CallCardAction(magicNumber, c -> c.hasTag(CustomTags.CAM_MYSTIC_CARD)));
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }

    @Override
    public String cardArtCopy() {
        return Distraction.ID;
    }

}