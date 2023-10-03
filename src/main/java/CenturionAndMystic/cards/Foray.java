package CenturionAndMystic.cards;

import CenturionAndMystic.actions.DamageFollowupAction;
import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.powers.InfuseMightPower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.SecretWeapon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Foray extends AbstractMysticCard {
    public final static String ID = makeID(Foray.class.getSimpleName());

    public Foray() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = damage = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageFollowupAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL, mon -> {
            if (mon.lastDamageTaken > 0) {
                Wiz.applyToSelfTop(new InfuseMightPower(p, mon.lastDamageTaken));
            }
        }));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }

    @Override
    public String cardArtCopy() {
        return SecretWeapon.ID;
    }
}