package CenturionAndMystic.cards;

import CenturionAndMystic.actions.ShedAction;
import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.GhostlyArmor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Spectre extends AbstractCenturionCard {
    public final static String ID = makeID(Spectre.class.getSimpleName());

    public Spectre() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseBlock = block = 5;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ShedAction(p.hand.size(), l -> {
            for (AbstractCard ignored : l) {
                addToTop(new GainBlockAction(p, p, block, true));
            }
        }));
    }

    @Override
    public void upp() {
        upgradeBlock(2);
    }

    @Override
    public String cardArtCopy() {
        return GhostlyArmor.ID;
    }
}