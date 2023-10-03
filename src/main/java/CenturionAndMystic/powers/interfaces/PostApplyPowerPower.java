package CenturionAndMystic.powers.interfaces;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public interface PostApplyPowerPower {
    void onApply(AbstractPower power, AbstractCreature target);
}
