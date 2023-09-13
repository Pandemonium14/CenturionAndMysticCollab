package CenturionAndMystic.powers;

import CenturionAndMystic.patches.CustomTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static CenturionAndMystic.MainModfile.makeID;

public class ComboPower extends AbstractEasyPower {

    public static String POWER_ID = makeID(AftermathPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = strings.NAME;
    public static final String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public AbstractCard.CardTags lastPlayedType;

    public ComboPower(AbstractCreature owner, int amount, AbstractCard starter) {
        super(NAME, PowerType.BUFF, false, owner, amount);
        this.ID = POWER_ID;
        this.loadRegion("swivel");
        if (starter.hasTag(CustomTags.CAM_MYSTIC_CARD)) {
            lastPlayedType = CustomTags.CAM_MYSTIC_CARD;
        } else if (starter.hasTag(CustomTags.CAM_CENTURION_CARD)) {
            lastPlayedType = CustomTags.CAM_CENTURION_CARD;
        }
    }

    //did it like this so that it's easy to change behavior
    private int getIncrementValue(AbstractCard cardPlayed) {
        if (cardPlayed.hasTag(CustomTags.CAM_CENTURION_CARD)) {
            if (lastPlayedType == CustomTags.CAM_MYSTIC_CARD) {
                return 1;
            } else {
                return -1;
            }
        } else if (cardPlayed.hasTag(CustomTags.CAM_MYSTIC_CARD)) {
            if (lastPlayedType == CustomTags.CAM_CENTURION_CARD) {
                return 1;
            } else {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        int increment = getIncrementValue(card);
        if (increment == -1) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        } else if (increment > 0) {
            addToBot(new ApplyPowerAction(owner, owner, new VigorPower(owner, amount)));
            addToBot(new ApplyPowerAction(owner, owner, new ComboPower(owner, increment, card)));

            if (card.hasTag(CustomTags.CAM_CENTURION_CARD)) {
                lastPlayedType = CustomTags.CAM_CENTURION_CARD;
            } else if (card.hasTag(CustomTags.CAM_MYSTIC_CARD)) {
                lastPlayedType = CustomTags.CAM_MYSTIC_CARD;
            }

        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
