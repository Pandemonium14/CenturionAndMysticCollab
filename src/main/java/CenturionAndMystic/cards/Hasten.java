package CenturionAndMystic.cards;

import CenturionAndMystic.actions.CallCardAction;
import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.patches.CustomTags;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Blur;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Hasten extends AbstractMysticCard {
    public final static String ID = makeID(Hasten.class.getSimpleName());

    public Hasten() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new CallCardAction(magicNumber, c -> c.hasTag(CustomTags.CAM_CENTURION_CARD), l -> {
            for (AbstractCard c : l) {
                c.setCostForTurn(0);
                if (upgraded && c.canUpgrade()) {
                    c.upgrade();
                }
            }
        }));
    }

    @Override
    public void upp() {
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return Blur.ID;
    }

}