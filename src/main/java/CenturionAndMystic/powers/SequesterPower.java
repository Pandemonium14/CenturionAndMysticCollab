package CenturionAndMystic.powers;

import CenturionAndMystic.util.FormatHelper;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static CenturionAndMystic.MainModfile.makeID;

public class SequesterPower extends AbstractEasyPower implements NonStackablePower {
    public static String POWER_ID = makeID(SequesterPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = strings.NAME;
    public static final String[] DESCRIPTIONS = strings.DESCRIPTIONS;
    private final AbstractCard card;

    public SequesterPower(AbstractCreature owner, AbstractCard card, int amount) {
        super(NAME, PowerType.BUFF, true, owner, amount);
        this.ID = POWER_ID;
        this.card = card;
        this.loadRegion("rushdown");
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        addToBot(new MakeTempCardInHandAction(this.card, 1));
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    @Override
    public void updateDescription() {
        if (card == null) {
            this.description = "";
            return;
        }
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + FormatHelper.prefixWords(CardModifierManager.onRenderTitle(card, card.name), "#y") + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2] + amount + DESCRIPTIONS[3] + FormatHelper.prefixWords(CardModifierManager.onRenderTitle(card, card.name), "#y") + DESCRIPTIONS[4];
        }
    }
}
