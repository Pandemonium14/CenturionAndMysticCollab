package CenturionAndMystic.damageMods;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class PoisonDamage extends AbstractDamageType {

    public PoisonDamage(boolean inherent) {
        super(inherent);
    }

    public PoisonDamage(boolean inherent, int amount) {
        super(inherent, amount);
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
    public AbstractDamageModifier makeCopy() {
        return new PoisonDamage(inherent, amount);
    }
}
