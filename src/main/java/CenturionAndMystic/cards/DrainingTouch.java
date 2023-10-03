package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.purple.FearNoEvil;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class DrainingTouch extends AbstractMysticCard {
    public final static String ID = makeID(DrainingTouch.class.getSimpleName());

    public DrainingTouch() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.SELF_AND_ENEMY);
        baseMagicNumber = magicNumber = 3;
        baseDamage = damage = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.POISON);
        addToBot(new AddTemporaryHPAction(p, p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
        upgradeDamage(2);
    }

    @Override
    public String cardArtCopy() {
        return FearNoEvil.ID;
    }
}