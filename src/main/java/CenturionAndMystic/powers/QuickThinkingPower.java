package CenturionAndMystic.powers;

import CenturionAndMystic.MainModfile;
import CenturionAndMystic.actions.SurveyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class QuickThinkingPower extends AbstractPower implements SurveyAction.OnSurveyModifier {
    public static final String POWER_ID = MainModfile.makeID(QuickThinkingPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public QuickThinkingPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("curiosity");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public int bonusOptions() {
        return 0;
    }

    @Override
    public int bonusPicks() {
        return 1;
    }
}