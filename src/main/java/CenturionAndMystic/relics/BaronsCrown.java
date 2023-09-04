package CenturionAndMystic.relics;

import CenturionAndMystic.CenturionAndMystic;
import CenturionAndMystic.cardmods.UnlockedMod;
import CenturionAndMystic.patches.ForcedUpgradesPatches;
import CenturionAndMystic.util.FormatHelper;
import CenturionAndMystic.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;
import java.util.HashMap;

import static CenturionAndMystic.MainModfile.makeID;

public class BaronsCrown extends AbstractEasyRelic {
    public static final String ID = makeID(BaronsCrown.class.getSimpleName());
    private boolean cardsSelected = true;
    private int selection = 1;
    HashMap<String, String> stats = new HashMap<>();
    private final String CARD_PICKED_STAT = DESCRIPTIONS[2];

    public BaronsCrown() {
        super(ID, RelicTier.RARE, LandingSound.CLINK, CenturionAndMystic.Enums.SHADOW_BLUE_COLOR);
    }

    public void onEquip() {
        this.cardsSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (!CardModifierManager.hasModifier(c, UnlockedMod.ID)) {
                tmp.addToTop(c);
            }
        }
        if (tmp.group.isEmpty()) {
            this.cardsSelected = true;
        } else {
            ForcedUpgradesPatches.previewMultipleUpgrade = true;
            ForcedUpgradesPatches.upgradeTimes = 1;
            AbstractDungeon.gridSelectScreen.open(tmp, selection, DESCRIPTIONS[1], true, false, false, false);
        }
    }

    public void update() {
        super.update();
        if (!this.cardsSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == selection) {
            ForcedUpgradesPatches.previewMultipleUpgrade = false;
            ForcedUpgradesPatches.upgradeTimes = 0;
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            CardModifierManager.addModifier(c, new UnlockedMod());
            c.upgrade();
            Wiz.adp().bottledCardUpgradeCheck(c);
            String name = c.name;
            if (c.rarity == AbstractCard.CardRarity.UNCOMMON) {
                name = FormatHelper.prefixWords(name, "#b");
            } else if (c.rarity == AbstractCard.CardRarity.RARE) {
                name = FormatHelper.prefixWords(name, "#y");
            }
            stats.put(CARD_PICKED_STAT, stats.get(CARD_PICKED_STAT) + " NL " + name);
            AbstractDungeon.topLevelEffectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), Settings.WIDTH/2f, Settings.HEIGHT/2f));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.cardsSelected = true;
            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    public String getStatsDescription() {
        return CARD_PICKED_STAT + stats.get(CARD_PICKED_STAT);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        return getStatsDescription();
    }

    public void resetStats() {
        stats.put(CARD_PICKED_STAT, "");
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<String> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(CARD_PICKED_STAT));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(CARD_PICKED_STAT, jsonArray.get(0).getAsString());
        } else {
            resetStats();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        // Relic Stats will always query the stats from the instance passed to BaseMod.addRelic()
        // Therefore, we make sure all copies share the same stats by copying the HashMap.
        BaronsCrown newRelic = new BaronsCrown();
        newRelic.stats = this.stats;
        return newRelic;
    }
}
