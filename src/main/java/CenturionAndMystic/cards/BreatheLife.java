package CenturionAndMystic.cards;

import CenturionAndMystic.actions.DoAction;
import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.patches.CustomTags;
import CenturionAndMystic.powers.InfuseMightPower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.DeepBreath;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class BreatheLife extends AbstractMysticCard {
    public final static String ID = makeID(BreatheLife.class.getSimpleName());

    public BreatheLife() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        exhaust = true;
        tags.add(CustomTags.CAM_MAGIC_EFFECT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DoAction(() -> {
            int count = 0;
            for (AbstractCard c : p.hand.group) {
                if (c.type != CardType.ATTACK) {
                    addToTop(new DiscardSpecificCardAction(c));
                    count++;
                }
            }
            if (count > 0) {
                Wiz.applyToSelfTop(new InfuseMightPower(p, magicNumber * count));
            }
        }));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public String cardArtCopy() {
        return DeepBreath.ID;
    }
}