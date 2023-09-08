package CenturionAndMystic.damageMods;

import CenturionAndMystic.actions.FastAddTemporaryHPAction;
import CenturionAndMystic.ui.PlayPreviewManager;
import CenturionAndMystic.util.TextureScaler;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DrainDamage extends AbstractDamageType {

    public static final Texture ICON = TextureScaler.rescale(AbstractPower.atlas.findRegion("128/brutality"), 64, 64);


    public DrainDamage(boolean inherent) {
        super(inherent);
    }

    public DrainDamage(boolean inherent, int amount) {
        super(inherent, amount);
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
        if (type == DamageInfo.DamageType.NORMAL && target != null) {
            float actualDamage = damage;
            actualDamage = actualDamage - target.currentBlock;
            actualDamage = Math.min(actualDamage, target.currentHealth);
            if (actualDamage >= 0) {
                new PlayPreviewManager.PlayPreviewIcon("DrainMod", ICON)
                        .queueRender((int) (actualDamage * PER_STACK * amount), AbstractDungeon.player);
            }
        }
        return super.atDamageFinalGive(damage, type, target, card);
    }

    @Override
    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature target) {
        if (info.type == DamageInfo.DamageType.NORMAL && info.owner != target) {
            int value = (int) (lastDamageTaken * amount * PER_STACK);
            if (lastDamageTaken > 0) {
                addToTop(new FastAddTemporaryHPAction(info.owner, info.owner, value));
            }
        }
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new DrainDamage(inherent, amount);
    }
}
