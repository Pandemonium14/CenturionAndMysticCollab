package CenturionAndMystic.patches;

import CenturionAndMystic.MainModfile;
import CenturionAndMystic.CenturionAndMystic;
import CenturionAndMystic.cards.abstracts.AbstractEasyCard;
import CenturionAndMystic.ui.CenturionEnergyPanel;
import CenturionAndMystic.ui.MysticEnergyPanel;
import CenturionAndMystic.util.TexLoader;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.RenderFixSwitches;
import basemod.patches.com.megacrit.cardcrawl.screens.SingleCardViewPopup.BackgroundFix;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CannotCompileException;
import javassist.CtBehavior;
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

    public static boolean nonDraftCard(AbstractCard c) {
        return AbstractDungeon.player instanceof CenturionAndMystic && !(c instanceof AbstractEasyCard);
    }

    public static boolean isMysticCard(AbstractCard c) {
        if (c.hasTag(CustomTags.CAM_MYSTIC_CARD)) {
            return true;
        }
        if (nonDraftCard(c)) {
            return !isCenturionCard(c);
        }
        return false;
    }

    public static boolean isCenturionCard(AbstractCard c) {
        if (c.hasTag(CustomTags.CAM_CENTURION_CARD)) {
            return true;
        }
        if (nonDraftCard(c)) {
            return c.type == AbstractCard.CardType.ATTACK || c.baseBlock >= 0;
        }
        return false;
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
            if (isMysticCard(c)) {
                return ExtraPanelFields.mysticEnergyPanel.get(AbstractDungeon.player).energy + panelAmount;
            } else if (isCenturionCard(c)) {
                return ExtraPanelFields.centurionEnergyPanel.get(AbstractDungeon.player).energy + panelAmount;
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
            if (isMysticCard(c) || isCenturionCard(c)) {
                return true;
            }
        }
        return false;
    }

    public static void spendCost(AbstractCard c) {
        if (isMysticCard(c)) {
            int overflow = c.costForTurn - ExtraPanelFields.mysticEnergyPanel.get(AbstractDungeon.player).energy;
            if (overflow > 0) {
                ExtraPanelFields.mysticEnergyPanel.get(AbstractDungeon.player).energy = 0;
                AbstractDungeon.player.energy.use(overflow);
            } else {
                ExtraPanelFields.mysticEnergyPanel.get(AbstractDungeon.player).energy -= c.costForTurn;
            }
        } else if (isCenturionCard(c)) {
            int overflow = c.costForTurn - ExtraPanelFields.centurionEnergyPanel.get(AbstractDungeon.player).energy;
            if (overflow > 0) {
                ExtraPanelFields.centurionEnergyPanel.get(AbstractDungeon.player).energy = 0;
                AbstractDungeon.player.energy.use(overflow);
            } else {
                ExtraPanelFields.centurionEnergyPanel.get(AbstractDungeon.player).energy -= c.costForTurn;
            }
        }
    }

    @SpirePatch2(clz = RenderFixSwitches.RenderEnergySwitch.class, method = "getEnergyOrb")
    public static class ChangeOrb {
        @SpirePrefixPatch
        public static SpireReturn<?> patch(AbstractCard card) {
            if (nonDraftCard(card)) {
                if (isMysticCard(card)) {
                    Texture t = TexLoader.getTexture(MainModfile.CARD_ENERGY_S_MYSTIC);
                    return SpireReturn.Return(new TextureAtlas.AtlasRegion(t, 0, 0, t.getWidth(), t.getHeight()));
                } else if (isCenturionCard(card)) {
                    Texture t = TexLoader.getTexture(MainModfile.CARD_ENERGY_S_CENTURION);
                    return SpireReturn.Return(new TextureAtlas.AtlasRegion(t, 0, 0, t.getWidth(), t.getHeight()));
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "renderCost")
    public static class ChangeOrbSCV {
        @SpireInsertPatch(locator = Locator.class, localvars = {"tmpImg"})
        public static void patch(SingleCardViewPopup __instance, AbstractCard ___card, @ByRef TextureAtlas.AtlasRegion[] tmpImg) {
            if (nonDraftCard(___card)) {
                if (isMysticCard(___card)) {
                    Texture t = TexLoader.getTexture(MainModfile.CARD_ENERGY_L_MYSTIC);
                    tmpImg[0] = new TextureAtlas.AtlasRegion(t, 0, 0, t.getWidth(), t.getHeight());
                } else if (isCenturionCard(___card)) {
                    Texture t = TexLoader.getTexture(MainModfile.CARD_ENERGY_L_CENTURION);
                    tmpImg[0] = new TextureAtlas.AtlasRegion(t, 0, 0, t.getWidth(), t.getHeight());
                }
            }
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(SingleCardViewPopup.class, "renderHelper");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }
}
