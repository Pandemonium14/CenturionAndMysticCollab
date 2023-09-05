package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.colorless.DarkShackles;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static CenturionAndMystic.MainModfile.makeID;

public class Rattle extends AbstractMysticCard {
    public final static String ID = makeID(Rattle.class.getSimpleName());

    public Rattle() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = damage = 4;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        Wiz.applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
    }

    @Override
    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return DarkShackles.ID;
    }

}