package CenturionAndMystic.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch2(clz = AbstractPlayer.class, method = "renderHand")
public class PlayPreviewIconsPatch {


    @SpireInsertPatch(loc = 2260)
    public static void playPreviewIconAoEHook(AbstractPlayer __instance) {
        AbstractCard card = __instance.hoveredCard;
        if (card != null) {
            if (card.baseDamage >= 0 && card.target != AbstractCard.CardTarget.ENEMY) card.calculateCardDamage(null);

            /*if (card.multiDamage != null && card.multiDamage.length > 1) {
                int i = 0;
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        if (p instanceof PlayPreviewIconPower) {
                            ((PlayPreviewIconPower) p).queueIconForAoE(card.multiDamage[i], m);
                        }
                    }
                    i++;
                }
            }*/
        }

    }

}
