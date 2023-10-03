package CenturionAndMystic.cards;

import CenturionAndMystic.actions.DamageFollowupAction;
import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.HeelHook;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class CloseCombat extends AbstractMysticCard {
    public final static String ID = makeID(CloseCombat.class.getSimpleName());

    public CloseCombat() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageFollowupAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY, mon -> {
            if (mon.lastDamageTaken > 0) {
                addToTop(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(mon.lastDamageTaken, true), damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
            }
        }));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }

    @Override
    public String cardArtCopy() {
        return HeelHook.ID;
    }
}