package CenturionAndMystic.powers;

import CenturionAndMystic.MainModfile;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.lang.reflect.Field;

public class IntimidatedPower extends AbstractPower {
    public static final String POWER_ID = MainModfile.makeID(IntimidatedPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private byte moveByte;
    //private AbstractMonster.Intent moveIntent;
    //private EnemyMoveInfo move;
    public boolean triggered;
    public int blockAmount;

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

    /*public void onRemove() {
        if (this.owner instanceof AbstractMonster) {// 96
            AbstractMonster m = (AbstractMonster)this.owner;// 97
            if (this.move != null) {// 98
                m.setMove(this.moveByte, this.moveIntent, this.move.baseDamage, this.move.multiplier, this.move.isMultiDamage);// 99
            } else {
                m.setMove(this.moveByte, this.moveIntent);// 101
            }
        }
    }*/

    @Override
    public void duringTurn() {
        if (triggered) {
            addToBot(new AnimateShakeAction(owner, 1.0f, 0.3f));
            addToBot(new GainBlockAction(owner, blockAmount));
            addToBot(new RollMoveAction((AbstractMonster) owner));
        }
    }

    @Override
    public void atEndOfRound() {
        if (triggered) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    public void onInitialApplication() {
        checkForTrigger();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        checkForTrigger();
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            checkForTrigger();
        }
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        checkForTrigger();
        return super.atDamageFinalGive(damage, type);
    }

    public void checkForTrigger() {
        if (!triggered && owner instanceof AbstractMonster && ((AbstractMonster) owner).getIntentBaseDmg() >= 0 && !owner.isDeadOrEscaped()) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    checkDamage();
                    this.isDone = true;
                }
            });
        }
    }

    private void checkDamage() {
        int dmg = ReflectionHacks.<Integer>getPrivate(owner, AbstractMonster.class, "intentDmg");
        if (ReflectionHacks.<Boolean>getPrivate(owner, AbstractMonster.class, "isMultiDmg"))
        {
            dmg *= ReflectionHacks.<Integer>getPrivate(owner, AbstractMonster.class, "intentMultiAmt");
        }

        if (amount >= dmg) {
            triggered = true;
            blockAmount = dmg;
            flash();
            changeIntent();
        }
    }

    private void changeIntent() {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                if (IntimidatedPower.this.owner instanceof AbstractMonster) {
                    IntimidatedPower.this.moveByte = ((AbstractMonster)IntimidatedPower.this.owner).nextMove;
                    //IntimidatedPower.this.moveIntent = ((AbstractMonster)IntimidatedPower.this.owner).intent;

                    try {
                        Field f = AbstractMonster.class.getDeclaredField("move");
                        f.setAccessible(true);
                        //IntimidatedPower.this.move = (EnemyMoveInfo)f.get(IntimidatedPower.this.owner);
                        EnemyMoveInfo stunMove = new EnemyMoveInfo(IntimidatedPower.this.moveByte, AbstractMonster.Intent.DEFEND, -1, 0, false);
                        f.set(IntimidatedPower.this.owner, stunMove);
                        ((AbstractMonster)IntimidatedPower.this.owner).createIntent();
                    } catch (NoSuchFieldException | IllegalAccessException var3) {
                        var3.printStackTrace();
                    }
                }
                this.isDone = true;
            }
        });
    }
}