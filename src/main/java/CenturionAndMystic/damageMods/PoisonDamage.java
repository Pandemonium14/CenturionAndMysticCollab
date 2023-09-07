package CenturionAndMystic.damageMods;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class PoisonDamage extends AbstractDamageModifier {
    private static final float PER_STACK = 0.25f;
    boolean inherent;
    int amount;

    public PoisonDamage(boolean inherent) {
        this(inherent, (int) (1/PER_STACK));
    }

    public PoisonDamage(boolean inherent, int amount) {
        this.amount = amount;
        this.inherent = inherent;
    }

    @Override
    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature target) {
        if (info.type == DamageInfo.DamageType.NORMAL && info.owner != target) {
            int value = (int) (lastDamageTaken * amount * PER_STACK);
            if (lastDamageTaken > 0) {
                addToTop(new ApplyPowerAction(target, info.owner, new PoisonPower(target, info.owner, value)));
            }
        }
    }

    @Override
    public boolean isInherent() {
        return inherent;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new PoisonDamage(inherent, amount);
    }
}
