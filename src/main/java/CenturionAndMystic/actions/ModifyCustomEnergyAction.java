package CenturionAndMystic.actions;

import CenturionAndMystic.CenturionAndMystic;
import CenturionAndMystic.patches.EnergyPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ModifyCustomEnergyAction extends AbstractGameAction {
    public enum EnergyType {
        CENTURION,
        MYSTIC,
        COLORLESS
    }
    private final EnergyType type;

    public ModifyCustomEnergyAction(int amount, EnergyType type) {
        setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        duration = Settings.ACTION_DUR_FAST;
        this.type = type;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player instanceof CenturionAndMystic && type != EnergyType.COLORLESS) {
                if (type == EnergyType.MYSTIC) {
                    EnergyPatches.ExtraPanelFields.mysticEnergyPanel.get(AbstractDungeon.player).gainEnergy(amount);
                } else if (type == EnergyType.CENTURION) {
                    EnergyPatches.ExtraPanelFields.centurionEnergyPanel.get(AbstractDungeon.player).gainEnergy(amount);
                }
                AbstractDungeon.actionManager.updateEnergyGain(amount);
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    c.triggerOnGainEnergy(amount, true);
                }
            } else {
                addToTop(new GainEnergyAction(amount));
                this.isDone = true;
            }
        }
        tickDuration();
    }
}
