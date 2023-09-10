package CenturionAndMystic.cards;

import CenturionAndMystic.actions.ShedAction;
import CenturionAndMystic.actions.ShedRandomAction;
import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.red.Havoc;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Slither extends AbstractCenturionCard {
    public final static String ID = makeID(Slither.class.getSimpleName());

    public Slither() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 9;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        if (upgraded) {
            addToBot(new ShedAction(magicNumber));
        } else {
            addToBot(new ShedRandomAction(magicNumber));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return Havoc.ID;
    }
}