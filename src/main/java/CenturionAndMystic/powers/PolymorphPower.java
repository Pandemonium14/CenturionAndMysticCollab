package CenturionAndMystic.powers;

import CenturionAndMystic.MainModfile;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PolymorphPower extends AbstractPower {
    public static final String POWER_ID = MainModfile.makeID(PolymorphPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private final AbstractMonster replaced;
    private int damageStored;

    public PolymorphPower(AbstractCreature owner, int amount, AbstractMonster replaced) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.loadRegion("minion");
        this.replaced = replaced;
        updateDescription();
    }

    @Override
    public void onDeath() {
        revert();
    }

    @Override
    public void onRemove() {
        revert();
    }

    @Override
    public void atEndOfRound() {
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + formatName(replaced.name) + DESCRIPTIONS[3];
        } else {
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + formatName(replaced.name) + DESCRIPTIONS[3];
        }
    }

    public String formatName(String name) {
        StringBuilder output = new StringBuilder();
        for (String word : name.split(" ")) {
            output.append("#y").append(word).append(' ');
        }

        return output.toString().trim();
    }

    private void revert() {
        if (damageStored > 0) {
            addToTop(new DamageAction(replaced, new DamageInfo(Wiz.adp(), damageStored, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));
        }
        boolean old = AbstractDungeon.getCurrRoom().cannotLose;
        AbstractDungeon.getCurrRoom().cannotLose = true;
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                int index = AbstractDungeon.getMonsters().monsters.indexOf((AbstractMonster) owner);
                if (index != -1) {
                    AbstractDungeon.getMonsters().monsters.remove(index);
                    AbstractDungeon.getMonsters().monsters.add(replaced);
                }
                AbstractDungeon.getCurrRoom().cannotLose = old;
                this.isDone = true;
            }
        });
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        damageStored += damageAmount;
        return damageAmount;
    }
}
