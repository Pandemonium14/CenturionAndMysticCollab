package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.SeverSoul;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static CenturionAndMystic.MainModfile.makeID;

public class CursedBlade extends AbstractMysticCard {
    public final static String ID = makeID(CursedBlade.class.getSimpleName());

    public CursedBlade() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 16;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if (Wiz.hasInfusion()) {
            Wiz.applyToEnemy(m, new WeakPower(m, magicNumber * Wiz.infusionCount(), false));
            Wiz.applyToEnemy(m, new VulnerablePower(m, magicNumber * Wiz.infusionCount(), false));
        }
    }

    public void triggerOnGlowCheck() {
        if (Wiz.hasInfusion()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upp() {
        upgradeDamage(6);
    }

    @Override
    public String cardArtCopy() {
        return SeverSoul.ID;
    }
}