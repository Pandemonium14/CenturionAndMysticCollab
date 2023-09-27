package CenturionAndMystic.damageMods;

import CenturionAndMystic.patches.DelayedExhaustPatches;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class FragileDamage extends AbstractDamageModifier {

    @Override
    public void onDamageModifiedByBlock(DamageInfo info, int unblockedAmount, int blockedAmount, AbstractCreature target) {
        if (blockedAmount > 0 && DamageModifierManager.getInstigator(info) instanceof AbstractCard) {
            DelayedExhaustPatches.DelayedExhaustField.delayedExhaust.set(DamageModifierManager.getInstigator(info), true);
        }
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new FragileDamage();
    }
}
