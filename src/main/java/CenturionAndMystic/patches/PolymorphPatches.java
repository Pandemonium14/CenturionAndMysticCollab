package CenturionAndMystic.patches;

import CenturionAndMystic.powers.PolymorphPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

public class PolymorphPatches {
    @SpirePatch2(clz = MonsterGroup.class, method = "areMonstersBasicallyDead")
    public static class PolymorphIsntDead {
        @SpirePrefixPatch
        public static SpireReturn<?> check(MonsterGroup __instance) {
            for (AbstractMonster m : __instance.monsters) {
                if (m.hasPower(PolymorphPower.POWER_ID)) {
                    return SpireReturn.Return(false);
                }
            }
            return SpireReturn.Continue();
        }
    }
}
