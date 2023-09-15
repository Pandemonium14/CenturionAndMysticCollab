package CenturionAndMystic.powers;

import CenturionAndMystic.MainModfile;
import CenturionAndMystic.damageMods.AbstractDamageType;
import CenturionAndMystic.damageMods.MightDamage;
import CenturionAndMystic.vfx.ColoredWrathParticleEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class InfuseMightPower extends AbstractInfusionPower {
    public static final String POWER_ID = MainModfile.makeID(InfuseMightPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public InfuseMightPower(AbstractCreature owner, int amount) {
        super(POWER_ID, NAME, owner, amount);
        this.loadRegion("mastery");
        updateDescription();
    }

    @Override
    AbstractDamageType getDamageType() {
        return new MightDamage(false, amount);
    }

    @Override
    void doVFX() {
        AbstractDungeon.effectsQueue.add(new ColoredWrathParticleEffect(Color.GOLD));
    }

    @Override
    public void onAddedDamageModsToDamageInfo(DamageInfo info, Object instigator) {
        super.onAddedDamageModsToDamageInfo(info, instigator);
        if (instigator instanceof AbstractCard) {
            addToBot(new ModifyDamageAction(((AbstractCard) instigator).uuid, amount));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new InfuseMightPower(owner, amount);
    }
}