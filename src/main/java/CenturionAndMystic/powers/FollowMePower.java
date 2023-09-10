package CenturionAndMystic.powers;

import CenturionAndMystic.actions.CallCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import java.util.List;

import static CenturionAndMystic.MainModfile.makeID;

public class FollowMePower extends AbstractEasyPower implements CallCardAction.OnCallPower {
    public static String POWER_ID = makeID(FollowMePower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = strings.NAME;
    public static final String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public FollowMePower(AbstractCreature owner, int amount) {
        super(NAME, PowerType.BUFF, false, owner, amount);
        this.ID = POWER_ID;
        this.loadRegion("juggernaut");
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onCall(List<AbstractCard> calledCards) {
        if (!calledCards.isEmpty()) {
            flash();
            addToTop(new GainBlockAction(owner, amount*calledCards.size()));
        }
    }
}
