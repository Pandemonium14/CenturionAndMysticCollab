package CenturionAndMystic.patches;

import CenturionAndMystic.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public class EnterCardGroupPatches {
    @SpirePatch2(clz = CardGroup.class, method = "addToTop")
    @SpirePatch2(clz = CardGroup.class, method = "addToBottom")
    @SpirePatch2(clz = CardGroup.class, method = "addToRandomSpot")
    @SpirePatch2(clz = CardGroup.class, method = "addToHand")
    public static class CardAddedToGroup {
        @SpirePostfixPatch
        public static void checkCard(CardGroup __instance, AbstractCard c) {
            if (Wiz.adp() != null) {
/*                if (__instance == Wiz.adp().hand) {
                    CardCounterPatches.cardsDrawnThisCombat.add(c);
                    CardCounterPatches.cardsDrawnThisTurn.add(c);
                    if (CardCounterPatches.isInitialDraw) {
                        CardCounterPatches.initialHand.add(c);
                    }
                }*/
                if (c instanceof OnEnterCardGroupCard) {
                    ((OnEnterCardGroupCard) c).onEnter(__instance);
                }
            }
        }
    }

    public interface OnEnterCardGroupCard {
        void onEnter(CardGroup g);
    }
}
