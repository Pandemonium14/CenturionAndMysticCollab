package CenturionAndMystic.cards;

import CenturionAndMystic.actions.ShedAction;
import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.red.SecondWind;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class PurestSteel extends AbstractCenturionCard {
    public final static String ID = makeID(PurestSteel.class.getSimpleName());

    public PurestSteel() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 16;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        addToBot(new ShedAction(Wiz.adp().hand.size(), c -> c.type == CardType.ATTACK));
    }

    @Override
    public void upp() {
        upgradeDamage(6);
    }

    @Override
    public String cardArtCopy() {
        return SecondWind.ID;
    }
}