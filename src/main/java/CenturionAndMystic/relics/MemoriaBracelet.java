package CenturionAndMystic.relics;

import CenturionAndMystic.CenturionAndMystic;
import CenturionAndMystic.actions.InfuseRandomCardAction;
import CenturionAndMystic.cardmods.DealDamageMod;
import CenturionAndMystic.util.Wiz;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static CenturionAndMystic.MainModfile.makeID;

public class MemoriaBracelet extends AbstractEasyRelic {
    public static final String ID = makeID(MemoriaBracelet.class.getSimpleName());
    private static final int DAMAGE = 5;
    private static final int BLOCK = 5;
    HashMap<String, Integer> stats = new HashMap<>();
    private final String DAMAGE_STAT = DESCRIPTIONS[1];
    private final String BLOCK_STAT = DESCRIPTIONS[2];
    private final String DPT = DESCRIPTIONS[3];
    private final String DPC = DESCRIPTIONS[4];
    private final String BPT = DESCRIPTIONS[5];
    private final String BPC = DESCRIPTIONS[6];

    public MemoriaBracelet() {
        super(ID, RelicTier.STARTER, LandingSound.MAGICAL, CenturionAndMystic.Enums.SHADOW_BLUE_COLOR);
        resetStats();
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new InfuseRandomCardAction(1, new DealDamageMod(DAMAGE, DAMAGE)));
        //addToBot(new InfuseRandomCardAction(1, new GainBlockMod(BLOCK, BLOCK)));
    }

    public int getDamageStat() {
        return stats.get(DAMAGE_STAT);
    }

    public void incrementDamageStat(int amount) {
        stats.put(DAMAGE_STAT, stats.get(DAMAGE_STAT) + amount);
    }

    public void incrementBlockStat(int amount) {
        stats.put(BLOCK_STAT, stats.get(BLOCK_STAT) + amount);
    }

    public static void onDamageInfusionTrigger(int amount) {
        if (CardCrawlGame.isInARun() && Wiz.adp() != null && Wiz.adp().hasRelic(MemoriaBracelet.ID)) {
            ((MemoriaBracelet) Wiz.adp().getRelic(MemoriaBracelet.ID)).incrementDamageStat(amount);
        }
    }

    public static void onBlockInfusionTrigger(int amount) {
        if (CardCrawlGame.isInARun() && Wiz.adp() != null && Wiz.adp().hasRelic(MemoriaBracelet.ID)) {
            ((MemoriaBracelet) Wiz.adp().getRelic(MemoriaBracelet.ID)).incrementBlockStat(amount);
        }
    }

    public String getStatsDescription() {
        return DAMAGE_STAT + stats.get(DAMAGE_STAT);// + BLOCK_STAT + stats.get(BLOCK_STAT);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        // You would just return getStatsDescription() if you don't want to display per-combat and per-turn stats
        StringBuilder builder = new StringBuilder();
        builder.append(getStatsDescription());

        // Relic Stats truncates these extended stats to 3 decimal places, so we do the same
        DecimalFormat perTurnFormat = new DecimalFormat("#.###");

        float stat = (float)stats.get(DAMAGE_STAT);
        builder.append(DPT);
        builder.append(perTurnFormat.format(stat / Math.max(totalTurns, 1)));
        builder.append(DPC);
        builder.append(perTurnFormat.format(stat / Math.max(totalCombats, 1)));

        /*stat = (float)stats.get(BLOCK_STAT);
        builder.append(BPT);
        builder.append(perTurnFormat.format(stat / Math.max(totalTurns, 1)));
        builder.append(BPC);
        builder.append(perTurnFormat.format(stat / Math.max(totalCombats, 1)));*/
        return builder.toString();
    }

    public void resetStats() {
        stats.put(DAMAGE_STAT, 0);
        stats.put(BLOCK_STAT, 0);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(DAMAGE_STAT));
        statsToSave.add(stats.get(BLOCK_STAT));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(DAMAGE_STAT, jsonArray.get(0).getAsInt());
            stats.put(BLOCK_STAT, jsonArray.get(1).getAsInt());
        } else {
            resetStats();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        // Relic Stats will always query the stats from the instance passed to BaseMod.addRelic()
        // Therefore, we make sure all copies share the same stats by copying the HashMap.
        MemoriaBracelet newRelic = new MemoriaBracelet();
        newRelic.stats = this.stats;
        return newRelic;
    }
}
