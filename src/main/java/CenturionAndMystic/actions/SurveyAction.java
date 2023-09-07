package CenturionAndMystic.actions;

import CenturionAndMystic.patches.CardCounterPatches;
import CenturionAndMystic.util.Wiz;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

import static CenturionAndMystic.MainModfile.makeID;

public class SurveyAction extends AbstractGameAction {
    public static String ID = makeID(SurveyAction.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    public SurveyAction(int cardsToPickFrom) {
        this.amount = cardsToPickFrom;
    }

    @Override
    public void update() {
        if (amount == 0 || Wiz.adp().drawPile.isEmpty()) {
            this.isDone = true;
            return;
        }
        for (AbstractRelic r : Wiz.adp().relics) {
            if (r instanceof OnSurveyModifier) {
                amount += ((OnSurveyModifier) r).bonusOptions();
            }
        }
        for (AbstractPower p : Wiz.adp().powers) {
            if (p instanceof OnSurveyModifier) {
                amount += ((OnSurveyModifier) p).bonusOptions();
            }
        }
        if (amount > Wiz.adp().drawPile.size()) {
            amount = Wiz.adp().drawPile.size();
        }
        ArrayList<AbstractCard> cardsToPickFrom = new ArrayList<>();
        ArrayList<AbstractCard> validCards = new ArrayList<>(Wiz.adp().drawPile.group);
        for (int i = 0 ; i < amount ; i++) {
            AbstractCard c = Wiz.getRandomItem(validCards);
            if (c != null) {
                validCards.remove(c);
                cardsToPickFrom.add(c);
            }
        }
        int toSelect = 1;
        for (AbstractRelic r : Wiz.adp().relics) {
            if (r instanceof OnSurveyModifier) {
                toSelect += ((OnSurveyModifier) r).bonusPicks();
            }
        }
        for (AbstractPower p : Wiz.adp().powers) {
            if (p instanceof OnSurveyModifier) {
                toSelect += ((OnSurveyModifier) p).bonusPicks();
            }
        }
        addToTop(new BetterSelectCardsCenteredAction(cardsToPickFrom, toSelect, toSelect == 1 ? TEXT[0] : TEXT[1] + toSelect + TEXT[2], true, c -> true, l -> {
            for (AbstractCard c : l) {
                if (Wiz.adp().hand.size() == BaseMod.MAX_HAND_SIZE) {
                    Wiz.adp().drawPile.moveToDiscardPile(c);
                    Wiz.adp().createHandIsFullDialog();
                } else {
                    c.unhover();// 54
                    c.lighten(true);// 55
                    c.setAngle(0.0F);// 56
                    c.drawScale = 0.12F;// 57
                    c.targetDrawScale = 0.75F;// 58
                    c.current_x = CardGroup.DRAW_PILE_X;// 59
                    c.current_y = CardGroup.DRAW_PILE_Y;// 60
                    Wiz.adp().drawPile.removeCard(c);// 61
                    AbstractDungeon.player.hand.addToTop(c);// 62
                    AbstractDungeon.player.hand.refreshHandLayout();// 63
                    AbstractDungeon.player.hand.applyPowers();// 64
                    CardCounterPatches.cardsSurveyedThisCombat++;
                    CardCounterPatches.cardsSurveyedThisTurn++;
                }
            }
        }));
        this.isDone = true;
    }

    public interface OnSurveyModifier {
        int bonusOptions();
        int bonusPicks();
    }
}
