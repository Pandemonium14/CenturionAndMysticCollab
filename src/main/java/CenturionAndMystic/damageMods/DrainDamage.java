package CenturionAndMystic.damageMods;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DrainDamage extends AbstractDamageModifier {
    private static final float PER_STACK = 0.25f;
    boolean inherent;
    int amount;

    public DrainDamage(boolean inherent) {
        this(inherent, (int) (1/PER_STACK));
    }

    public DrainDamage(boolean inherent, int amount) {
        this.amount = amount;
        this.inherent = inherent;
    }

    @Override
    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature target) {
        if (info.type == DamageInfo.DamageType.NORMAL && info.owner != target) {
            int value = (int) (lastDamageTaken * amount * PER_STACK);
            if (lastDamageTaken > 0) {
                addToTop(new AddTemporaryHPAction(info.owner, info.owner, value));
            }
        }
    }

    @Override
    public boolean isInherent() {
        return inherent;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new DrainDamage(inherent, amount);
    }
}
