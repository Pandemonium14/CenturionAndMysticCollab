package CenturionAndMystic.cards;

import CenturionAndMystic.actions.DoIfAction;
import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.patches.CustomTags;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.ThunderClap;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.IronWaveEffect;

import static CenturionAndMystic.MainModfile.makeID;

public class CrackGround extends AbstractCenturionCard {
    public final static String ID = makeID(CrackGround.class.getSimpleName());

    public CrackGround() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = damage = 7;
        baseMagicNumber = magicNumber = 1;
        tags.add(CustomTags.CAM_BASH_EFFECT);
        isMultiDamage = true;
        //CardModifierManager.addModifier(this, new PoisedMod(true));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.forAllMonstersLiving(mon -> addToBot(new VFXAction(new IronWaveEffect(p.hb.cX, p.hb.cY, mon.hb.cX), 0.15F)));
        allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        addToBot(new DoIfAction(Wiz::hasInfusion, () -> addToBot(new GainEnergyAction(magicNumber))));
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
        upgradeDamage(3);
    }

    @Override
    public String cardArtCopy() {
        return ThunderClap.ID;
    }
}