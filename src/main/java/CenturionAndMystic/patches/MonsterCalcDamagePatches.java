package CenturionAndMystic.patches;

import CenturionAndMystic.powers.interfaces.MonsterCalcDamagePower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MonsterCalcDamagePatches {
    @SpirePatch2(clz = AbstractMonster.class, method = "calculateDamage")
    public static class CalcPatch {
        @SpirePostfixPatch
        public static void postCalc(AbstractMonster __instance, int ___intentDmg, boolean ___isMultiDmg, int ___intentMultiAmt) {
            for (AbstractPower p : __instance.powers) {
                if (p instanceof MonsterCalcDamagePower) {
                    ((MonsterCalcDamagePower) p).onCalculateDamage(___intentDmg, ___isMultiDmg, ___intentMultiAmt);
                }
            }
        }
    }
}
