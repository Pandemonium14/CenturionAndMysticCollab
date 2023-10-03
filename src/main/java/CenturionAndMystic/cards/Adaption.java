package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.purple.EmptyBody;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;

import static CenturionAndMystic.MainModfile.makeID;

public class Adaption extends AbstractMysticCard {
    public final static String ID = makeID(Adaption.class.getSimpleName());

    public Adaption() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new FreeAttackPower(p, 1));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(1);
        //upgradeBaseCost(0);
        exhaust = false;
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return EmptyBody.ID;
    }
}