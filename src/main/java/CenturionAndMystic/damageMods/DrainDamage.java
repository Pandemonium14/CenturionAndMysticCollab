package CenturionAndMystic.damageMods;

import CenturionAndMystic.actions.FastAddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DrainDamage extends AbstractDamageType {

    public DrainDamage(boolean inherent) {
        super(inherent);
    }

    public DrainDamage(boolean inherent, int amount) {
        super(inherent, amount);
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
