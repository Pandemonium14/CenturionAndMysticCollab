package CenturionAndMystic.cards;

import CenturionAndMystic.actions.DoIfAction;
import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.patches.CustomTags;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Bludgeon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class MaceCrash extends AbstractCenturionCard {
    public final static String ID = makeID(MaceCrash.class.getSimpleName());

    public MaceCrash() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 7;
        tags.add(CustomTags.CAM_BASH_EFFECT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        addToBot(new DoIfAction(() -> !Wiz.isAttacking(m), () -> dmgTop(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY)));
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (Wiz.anyMonsterNotAttacking()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public String cardArtCopy() {
        return Bludgeon.ID;
    }
}