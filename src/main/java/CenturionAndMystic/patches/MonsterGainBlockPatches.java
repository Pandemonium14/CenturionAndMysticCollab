package CenturionAndMystic.patches;

import CenturionAndMystic.powers.interfaces.MonsterGainBlockPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MonsterGainBlockPatches {
    @SpirePatch2(clz = AbstractCreature.class, method = "addBlock")
    public static class DoMonsterBlock {
        @SpirePrefixPatch
        public static SpireReturn<?> plz(AbstractCreature __instance, int blockAmount) {
            if (!__instance.isPlayer) {
                float tmp = blockAmount;
                for (AbstractPower p : __instance.powers) {
                    if (p instanceof MonsterGainBlockPower) {
                        tmp = ((MonsterGainBlockPower) p).onGainedBlockMonster(tmp);
                    }
                }
                if (tmp <= 0) {
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }
}
