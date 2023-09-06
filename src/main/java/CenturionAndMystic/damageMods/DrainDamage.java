package CenturionAndMystic.damageMods;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DrainDamage extends AbstractDamageModifier {
    private static final float PER_STACK = 0.25f;
    int amount;

    public DrainDamage() {
        this((int) (1/PER_STACK));
    }

    public DrainDamage(int amount) {
        this.amount = amount;
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
    public AbstractDamageModifier makeCopy() {
        return new DrainDamage(amount);
    }
}
