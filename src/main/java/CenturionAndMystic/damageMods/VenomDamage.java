package CenturionAndMystic.damageMods;

import CenturionAndMystic.powers.VenomPower;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class VenomDamage extends AbstractDamageType {

    public VenomDamage(boolean inherent) {
        super(inherent);
    }

    public VenomDamage(boolean inherent, int amount) {
        super(inherent, amount);
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
    public AbstractDamageModifier makeCopy() {
        return new VenomDamage(inherent, amount);
    }
}
