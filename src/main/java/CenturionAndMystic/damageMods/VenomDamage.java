package CenturionAndMystic.damageMods;

import CenturionAndMystic.powers.VenomPower;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class VenomDamage extends AbstractDamageModifier {
    private static final float PER_STACK = 0.25f;
    boolean inherent;
    int amount;

    public VenomDamage(boolean inherent) {
        this(inherent, (int) (1/PER_STACK));
    }

    public VenomDamage(boolean inherent, int amount) {
        this.amount = amount;
        this.inherent = inherent;
    }

    @Override
    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature target) {
        if (info.type == DamageInfo.DamageType.NORMAL && info.owner != target) {
            int value = (int) (lastDamageTaken * amount * PER_STACK);
            if (lastDamageTaken > 0) {
                addToTop(new ApplyPowerAction(target, info.owner, new VenomPower(target, value)));
            }
        }
    }

    @Override
    public boolean isInherent() {
        return inherent;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new VenomDamage(inherent, amount);
    }
}
