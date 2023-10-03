package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.powers.IntimidatedPower;
import CenturionAndMystic.util.Wiz;
import CenturionAndMystic.vfx.CrackGroundEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.red.ThunderClap;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class CrackGround extends AbstractCenturionCard {
    public final static String ID = makeID(CrackGround.class.getSimpleName());

    public CrackGround() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = damage = 6;
        baseMagicNumber = magicNumber = 4;
        //tags.add(CustomTags.CAM_BASH_EFFECT);
        isMultiDamage = true;
        //CardModifierManager.addModifier(this, new PoisedMod(true));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            {
                this.duration = this.startDuration = 0.7f;
            }
            @Override
            public void update() {
                if (duration == startDuration) {
                    p.useJumpAnimation();
                }
                tickDuration();
            }
        });
        addToBot(new VFXAction(new CrackGroundEffect(p.hb.cX, p.hb.cY - p.hb_h), 0.4f));
        allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemy(mon, new IntimidatedPower(mon, magicNumber)));
        //addToBot(new DoIfAction(Wiz::hasInfusion, () -> addToBot(new GainEnergyAction(magicNumber))));
    }

    /*public void triggerOnGlowCheck() {
        if (Wiz.hasInfusion()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }*/

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return ThunderClap.ID;
    }
}