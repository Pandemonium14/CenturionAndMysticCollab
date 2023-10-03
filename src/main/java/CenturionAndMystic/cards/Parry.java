package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.cards.interfaces.InHandCard;
import CenturionAndMystic.powers.IntimidatedPower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.red.Clash;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

import static CenturionAndMystic.MainModfile.makeID;

public class Parry extends AbstractCenturionCard implements InHandCard {
    public final static String ID = makeID(Parry.class.getSimpleName());

    public Parry() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 10;
        baseMagicNumber = magicNumber = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
        }
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        Wiz.applyToEnemy(m, new IntimidatedPower(m, magicNumber));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return super.canUse(p, m) && ((m == null && Wiz.anyMonsterAttacking()) || Wiz.isAttacking(m));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Clash.ID;
    }

}