package CenturionAndMystic.actions;

import CenturionAndMystic.powers.PolymorphPower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.LouseDefensive;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;

public class PolymorphAction extends AbstractGameAction {
    AbstractMonster replacedMonster;

    public PolymorphAction(AbstractMonster replacedMonster, int turns) {
        this.replacedMonster = replacedMonster;
        this.amount = turns;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().monsters.contains(replacedMonster)) {
            LouseDefensive lad = new LouseDefensive((replacedMonster.drawX - Settings.WIDTH * 0.75F)/Settings.xScale, (replacedMonster.drawY - AbstractDungeon.floorY)/Settings.yScale);
            boolean old = AbstractDungeon.getCurrRoom().cannotLose;
            AbstractDungeon.getCurrRoom().cannotLose = true;
            Wiz.applyToEnemyTop(lad, new PolymorphPower(lad, amount, replacedMonster));
            Wiz.att(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractDungeon.getCurrRoom().cannotLose = old;
                    lad.usePreBattleAction();
                    lad.createIntent();
                    this.isDone = true;
                }
            });
            Wiz.att(new SpawnMonsterAction(lad, false));
            Wiz.att(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractDungeon.getMonsters().monsters.remove(replacedMonster);
                    this.isDone = true;
                }
            });
            Wiz.att(new VFXAction(new SmokeBombEffect(replacedMonster.drawX, replacedMonster.drawY)));
        }
        this.isDone = true;
    }
}
