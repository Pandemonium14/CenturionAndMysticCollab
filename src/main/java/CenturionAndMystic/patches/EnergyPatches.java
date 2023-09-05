package CenturionAndMystic.patches;

import CenturionAndMystic.CenturionAndMystic;
import CenturionAndMystic.cards.abstracts.AbstractEasyCard;
import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.ui.CenturionEnergyPanel;
import CenturionAndMystic.ui.MysticEnergyPanel;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;

public class EnergyPatches {
    @SpirePatch2(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
    public static class ExtraPanelFields {
        public static SpireField<MysticEnergyPanel> mysticEnergyPanel = new SpireField<>(() -> null);
        public static SpireField<CenturionEnergyPanel> centurionEnergyPanel = new SpireField<>(() -> null);
    }

    @SpirePatch2(clz = EnergyPanel.class, method = "render")
    public static class EnergyPanelRenderPatch {
        @SpirePrefixPatch
        public static SpireReturn<?> checkIfRender(EnergyPanel __instance, SpriteBatch sb) {
            if (AbstractDungeon.player.chosenClass == CenturionAndMystic.Enums.CENTURION_AND_MYSTIC) {
                ((CenturionAndMystic)AbstractDungeon.player).renderClassOrbs(sb, __instance.current_x, __instance.current_y);
                __instance.current_y = __instance.current_y + (128.0F * Settings.scale);
                if (EnergyPanel.totalCount == 0) {
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }

        @SpirePostfixPatch
        public static void resetCurrentY(EnergyPanel __instance) {
            if (AbstractDungeon.player.chosenClass == CenturionAndMystic.Enums.CENTURION_AND_MYSTIC) {
                __instance.current_y = __instance.current_y - (128.0F * Settings.scale);
            }
        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = "hasEnoughEnergy")
    public static class CheckCost {
        @SpireInstrumentPatch
        public static ExprEditor patch() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess f) throws CannotCompileException {
                    if (f.getClassName().equals(EnergyPanel.class.getName()) && f.getFieldName().equals("totalCount")) {
                        f.replace("$_ = "+EnergyPatches.class.getName()+".energyCheck(this, $proceed($$));");
                    }
                }
            };
        }
    }

    public static int energyCheck(AbstractCard c, int panelAmount) {
        if (AbstractDungeon.player instanceof CenturionAndMystic) {
            if (c instanceof AbstractMysticCard) {
                return ExtraPanelFields.mysticEnergyPanel.get(AbstractDungeon.player).energy;
            } else if (c instanceof AbstractEasyCard) {
                return ExtraPanelFields.centurionEnergyPanel.get(AbstractDungeon.player).energy;
            }
        }
        return panelAmount;
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
    public static class SpendCost {
        @SpireInstrumentPatch
        public static ExprEditor patch() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(EnergyManager.class.getName()) && m.getMethodName().equals("use")) {
                        m.replace("if ("+EnergyPatches.class.getName()+".shouldDefer(c)) {"+EnergyPatches.class.getName()+".spendCost(c);} else {$proceed($$);}");
                    }
                }
            };
        }
    }

    public static boolean shouldDefer(AbstractCard c) {
        if (AbstractDungeon.player instanceof CenturionAndMystic) {
            if (c instanceof AbstractEasyCard) {
                return true;
            }
        }
        return false;
    }

    public static void spendCost(AbstractCard c) {
        if (c instanceof AbstractMysticCard) {
            ExtraPanelFields.mysticEnergyPanel.get(AbstractDungeon.player).energy -= c.costForTurn;
        } else if (c instanceof AbstractEasyCard) {
            ExtraPanelFields.centurionEnergyPanel.get(AbstractDungeon.player).energy -= c.costForTurn;
        }
    }
}
