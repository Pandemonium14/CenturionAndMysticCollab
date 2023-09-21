package CenturionAndMystic.cards;

import CenturionAndMystic.actions.CustomRandomizeHandCostAction;
import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.green.CalculatedGamble;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class VisionsOfSnecko extends AbstractMysticCard {
    public final static String ID = makeID(VisionsOfSnecko.class.getSimpleName());

    public VisionsOfSnecko() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new CustomRandomizeHandCostAction());
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return CalculatedGamble.ID;
    }
}