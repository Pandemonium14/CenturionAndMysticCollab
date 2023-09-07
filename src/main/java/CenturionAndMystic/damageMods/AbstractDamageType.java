package CenturionAndMystic.damageMods;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;

public abstract class AbstractDamageType extends AbstractDamageModifier {
    public static final float PER_STACK = 0.25f;
    boolean inherent;
    int amount;

    public AbstractDamageType(boolean inherent) {
        this(inherent, (int) (1/PER_STACK));
    }

    public AbstractDamageType(boolean inherent, int amount) {
        this.amount = amount;
        this.inherent = inherent;
    }

    @Override
    public boolean isInherent() {
        return inherent;
    }
}
