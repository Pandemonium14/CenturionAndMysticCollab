package CenturionAndMystic.patches;

import CenturionAndMystic.MainModfile;
import CenturionAndMystic.cardmods.UnlockedMod;
import CenturionAndMystic.powers.interfaces.OnUpgradePower;
import CenturionAndMystic.util.ChimeraHelper;
import CenturionAndMystic.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveFile.ModSaves;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.SearingBlow;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import javassist.*;
import org.clapper.util.classutil.*;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ForcedUpgradesPatches {
    public static boolean previewMultipleUpgrade = false;
    public static int upgradeTimes = 0;

    public static void applyUnlockIfNeeded(AbstractCard card) {
        if (!(card instanceof SearingBlow) && !CardModifierManager.hasModifier(card, UnlockedMod.ID)) {
            if (!(Loader.isModLoaded("CardAugments") && ChimeraHelper.hasSearing(card))) {
                CardModifierManager.addModifier(card, new UnlockedMod());
            }
        }
    }

    @SpirePatch2(clz = HandCardSelectScreen.class, method = "selectHoveredCard")
    @SpirePatch2(clz = HandCardSelectScreen.class, method = "updateMessage")
    public static class ShowMultipleUpgrades {
        @SpireInsertPatch(locator = Locator.class)
        public static void plz(HandCardSelectScreen __instance) {
            if (previewMultipleUpgrade) {
                applyUnlockIfNeeded(__instance.upgradePreviewCard);
                for (int i = 0 ; i < upgradeTimes-1 ; i++) {
                    __instance.upgradePreviewCard.upgrade();
                }
            }
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(AbstractCard.class, "upgrade");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = GridCardSelectScreen.class, method = "update")
    public static class ShowMultipleUpgrades2 {
        @SpireInsertPatch(locator = Locator.class)
        public static void plz(GridCardSelectScreen __instance) {
            if (previewMultipleUpgrade) {
                applyUnlockIfNeeded(__instance.upgradePreviewCard);
                for (int i = 0 ; i < upgradeTimes-1 ; i++) {
                    __instance.upgradePreviewCard.upgrade();
                }
            }
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(AbstractCard.class, "upgrade");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class ForcedUpgradeField {
        public static SpireField<Boolean> inf = new SpireField<>(() -> false);
        public static SpireField<Boolean> looping =  new SpireField<>(() -> false);
    }

    public static void infCheck(AbstractCard card) {
        if (ForcedUpgradeField.inf.get(card)) {
            card.upgraded = false;
        }
        if (Wiz.adp() != null && Wiz.isInCombat() && Wiz.isCombatCard(card)) {
            for (AbstractPower p : Wiz.adp().powers) {
                if (p instanceof OnUpgradePower && ((OnUpgradePower) p).allowUpgrade(card)) {
                    card.upgraded = false;
                }
            }
        }
        MainModfile.onUpgradeTrigger(card);
    }

    @SpirePatch2(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopy {
        @SpireInsertPatch(locator = Locator.class, localvars = {"card"})
        public static void copyField(AbstractCard __instance, AbstractCard card) {
            if (ForcedUpgradeField.inf.get(__instance)) {
                ForcedUpgradeField.inf.set(card, true);
            }
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.FieldAccessMatcher(AbstractCard.class, "timesUpgraded");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = "canUpgrade")
    public static class BypassUpgradeLimit {
        @SpirePrefixPatch
        public static SpireReturn<?> plz(AbstractCard __instance) {
            if (ForcedUpgradeField.inf.get(__instance)) {
                return SpireReturn.Return(true);
            }
            if (Wiz.adp() != null && Wiz.isInCombat()) {
                for (AbstractPower p : Wiz.adp().powers) {
                    if (p instanceof OnUpgradePower && ((OnUpgradePower) p).allowUpgrade(__instance)) {
                        return SpireReturn.Return(true);
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }

    //Parse the cardmod gson (which has not yet loaded) to see if the card we are creating would have the Searing Mod
    @SpirePatch2(clz = CardLibrary.class, method = "getCopy", paramtypez = {String.class, int.class, int.class})
    public static class FixSaveAndLoadIssues {
        @SpireInsertPatch(locator = Locator.class, localvars = "retVal")
        public static void forceUpgrade(AbstractCard retVal) {
            GsonBuilder builder = new GsonBuilder();
            if (CardModifierPatches.modifierAdapter == null) {
                CardModifierPatches.initializeAdapterFactory();
            }

            if (ModSaves.cardModifierSaves != null && CardCrawlGame.saveFile != null) {
                builder.registerTypeAdapterFactory(CardModifierPatches.modifierAdapter);
                Gson gson = builder.create();
                ModSaves.ArrayListOfJsonElement cardModifierSaves = ModSaves.cardModifierSaves.get(CardCrawlGame.saveFile);
                int i = AbstractDungeon.player.masterDeck.size();
                if (cardModifierSaves != null) {
                    JsonElement loaded = i >= cardModifierSaves.size() ? null : cardModifierSaves.get(i);
                    if (loaded != null && loaded.isJsonArray()) {
                        JsonArray array = loaded.getAsJsonArray();
                        for (JsonElement element : array) {
                            AbstractCardModifier cardModifier = null;
                            try {
                                cardModifier = gson.fromJson(element, new TypeToken<AbstractCardModifier>() {}.getType());
                            } catch (Exception ignored) {}
                            if (cardModifier instanceof UnlockedMod) {
                                ForcedUpgradeField.inf.set(retVal, true);
                            }
                        }
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "upgrade");
                return new int[]{LineFinder.findInOrder(ctMethodToPatch, finalMatcher)[0]-1};
            }
        }
    }

    @SpirePatch(clz = CardCrawlGame.class, method = SpirePatch.CONSTRUCTOR)
    public static class AbstractCardDynamicPatch {
        @SpireRawPatch
        public static void patch(CtBehavior ctBehavior) throws NotFoundException {
            ClassFinder finder = new ClassFinder();
            finder.add(new File(Loader.STS_JAR));

            for (ModInfo modInfo : Loader.MODINFOS) {
                if (modInfo.jarURL != null) {
                    try {
                        finder.add(new File(modInfo.jarURL.toURI()));
                    } catch (URISyntaxException ignored) {}
                }
            }

            ClassFilter filter = new AndClassFilter(
                    new NotClassFilter(new InterfaceOnlyClassFilter()),
                    new ClassModifiersClassFilter(Modifier.PUBLIC),
                    new OrClassFilter(
                            new SubclassClassFilter(AbstractCard.class),
                            (classInfo, classFinder) -> classInfo.getClassName().equals(AbstractCard.class.getName())
                    )
            );

            ArrayList<ClassInfo> foundClasses = new ArrayList<>();
            finder.findClasses(foundClasses, filter);

            for (ClassInfo classInfo : foundClasses) {
                CtClass ctClass = ctBehavior.getDeclaringClass().getClassPool().get(classInfo.getClassName());
                try {
                    CtMethod[] methods = ctClass.getDeclaredMethods();
                    for (CtMethod m : methods) {
                        if (m.getName().equals("upgrade")) {
                            m.insertBefore(ForcedUpgradesPatches.class.getName() + ".infCheck($0);");
                        }
                    }
                } catch (CannotCompileException ignored) {}
            }
        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = "upgradeName")
    public static class FixStackOfPlusSymbols {
        @SpireInsertPatch(locator = Locator.class)
        public static void plz(AbstractCard __instance) {
            if (ForcedUpgradeField.inf.get(__instance)) {
                __instance.name = __instance.originalName + "+" + __instance.timesUpgraded;
            }
        }
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "initializeTitle");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
