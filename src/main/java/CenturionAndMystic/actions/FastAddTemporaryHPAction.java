package CenturionAndMystic.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class FastAddTemporaryHPAction extends AddTemporaryHPAction {
    public FastAddTemporaryHPAction(AbstractCreature target, AbstractCreature source, int amount) {
        super(target, source, amount);
    }

    @Override
    public void update() {
        super.update();
        this.isDone = true;
    }
}
