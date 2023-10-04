package CenturionAndMystic.powers;

import CenturionAndMystic.MainModfile;
import CenturionAndMystic.powers.interfaces.MonsterCalcDamagePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.lang.reflect.Field;

public class IntimidatedPower extends AbstractPower implements MonsterCalcDamagePower {
    public static final String POWER_ID = MainModfile.makeID(IntimidatedPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final EnemyMoveInfo stunMove = new EnemyMoveInfo((byte) -1, AbstractMonster.Intent.DEFEND, -1, 0, false);
    private static final Field moveField;
    public int blockAmount;

    static {
        try {
            moveField = AbstractMonster.class.getDeclaredField("move");
            moveField.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public IntimidatedPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.loadRegion("phantasmal");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void duringTurn() {
        if (isTriggered()) {
            addToBot(new AnimateShakeAction(owner, 1.0f, 0.3f));
            addToBot(new GainBlockAction(owner, blockAmount));
            addToBot(new ReducePowerAction(owner, owner, this, blockAmount));
            //addToBot(new DoAction(() -> triggered = false));
            addToBot(new RollMoveAction((AbstractMonster) owner));
        }
    }

    @Override
    public void onCalculateDamage(int intentDamage, boolean isMulti, int multiAmount) {
        if (!isTriggered() && ((AbstractMonster) owner).getIntentBaseDmg() >= 0 && !owner.isDeadOrEscaped()) {
            int dmg = intentDamage;
            if (isMulti) {
                dmg *= multiAmount;
            }
            if (amount >= dmg) {
                blockAmount = dmg;
                flash();
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        setMove();
                        this.isDone = true;
                    }
                });
            }
        }
    }

    public boolean isTriggered() {
        return owner instanceof AbstractMonster && getMove() == stunMove;
    }

    private EnemyMoveInfo getMove() {
        if (owner instanceof AbstractMonster) {
            try {
                return (EnemyMoveInfo) moveField.get(owner);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private void setMove() {
        if (owner instanceof AbstractMonster) {
            byte moveByte = ((AbstractMonster)owner).nextMove;
            try {
                stunMove.nextMove = moveByte;
                moveField.set(owner, stunMove);
                ((AbstractMonster)owner).createIntent();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}