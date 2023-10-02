package CenturionAndMystic.patches;

import CenturionAndMystic.powers.IntimidatedPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class IntimidatedPatches {
    public static boolean checkIntimidated(AbstractMonster m) {
        return m.hasPower(IntimidatedPower.POWER_ID) && ((IntimidatedPower)m.getPower(IntimidatedPower.POWER_ID)).triggered;
    }

    /*@SpirePatch(clz = AbstractMonster.class, method = "rollMove")
    public static class RollMove {
        @SpirePrefixPatch
        public static SpireReturn<?> dontRoll(AbstractMonster __instance) {
            return checkIntimidated(__instance) ? SpireReturn.Return() : SpireReturn.Continue();
        }
    }*/

    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
    public static class GetNextAction {
        @SpireInstrumentPatch
        public static ExprEditor skipTurn() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(AbstractMonster.class.getName()) && m.getMethodName().equals("takeTurn")) {
                        m.replace("if (!"+IntimidatedPatches.class.getName()+".checkIntimidated(m)) {$_ = $proceed($$);}");
                    }
                }
            };
        }
    }
}
