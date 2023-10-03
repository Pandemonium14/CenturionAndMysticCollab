package CenturionAndMystic.powers.interfaces;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public interface PostReceivePowerPower {
    void onReceive(AbstractPower power, AbstractCreature source);
}
