package CenturionAndMystic.cards;

import CenturionAndMystic.cardmods.ChargedMod;
import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.patches.CustomTags;
import CenturionAndMystic.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
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
        tags.add(CustomTags.CAM_BASH_EFFECT);
        isMultiDamage = true;
        CardModifierManager.addModifier(this, new ChargedMod(true));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.forAllMonstersLiving(mon -> addToBot(new VFXAction(new IronWaveEffect(p.hb.cX, p.hb.cY, mon.hb.cX), 0.15F)));
        allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
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