package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Outmaneuver;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class PreemptiveStrike extends AbstractMysticCard {
    public final static String ID = makeID(PreemptiveStrike.class.getSimpleName());

    public PreemptiveStrike() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 8;
        tags.add(CardTags.STRIKE);
        exhaust = true;
        isInnate = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int base = this.baseDamage;
        if (!Wiz.isAttacking(mo)) {
            this.baseDamage *= 2;
        }
        super.calculateCardDamage(mo);
        this.baseDamage = base;
        this.isDamageModified = baseDamage != damage;
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (Wiz.anyMonsterNotAttacking()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public String cardArtCopy() {
        return Outmaneuver.ID;
    }
}