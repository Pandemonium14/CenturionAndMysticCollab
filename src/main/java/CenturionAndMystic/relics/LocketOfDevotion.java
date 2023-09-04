package CenturionAndMystic.relics;

import CenturionAndMystic.CenturionAndMystic;
import CenturionAndMystic.actions.InfuseRandomCardAction;
import CenturionAndMystic.cardmods.DealDamageMod;
import CenturionAndMystic.util.Wiz;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static CenturionAndMystic.MainModfile.makeID;

public class LocketOfDevotion extends AbstractEasyRelic {
    public static final String ID = makeID(LocketOfDevotion.class.getSimpleName());
    HashMap<String, Integer> stats = new HashMap<>();
    private final String STAT = DESCRIPTIONS[1];
    private final String PER_TURN = DESCRIPTIONS[2];
    private final String PER_COMBAT = DESCRIPTIONS[3];

    public LocketOfDevotion() {
        super(ID, RelicTier.BOSS, LandingSound.MAGICAL, CenturionAndMystic.Enums.SHADOW_BLUE_COLOR);
        resetStats();
    }

    @Override
    public void atTurnStartPostDraw() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new InfuseRandomCardAction(1, new DealDamageMod(5, 5)));
    }

    @Override //Should replace default relic.
    public void obtain() {
        //Grab the player
        AbstractPlayer p = AbstractDungeon.player;
        //If we have the starter relic...
        if (p.hasRelic(MemoriaBracelet.ID)) {
            //Grab its data for relic stats if you want to carry the stats over to the boss relic
            MemoriaBracelet mb = (MemoriaBracelet) p.getRelic(MemoriaBracelet.ID);
            stats.put(STAT, mb.getDamageStat());
            //Find it...
            for (int i = 0; i < p.relics.size(); ++i) {
                if (p.relics.get(i).relicId.equals(MemoriaBracelet.ID)) {
                    //Replace it
                    instantObtain(p, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    //Only spawn if we have the starter relic
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(MemoriaBracelet.ID);
    }

    public void incrementStat(int amount) {
        stats.put(STAT, stats.get(STAT) + amount);
    }

    public static void onInfusionTrigger(int amount) {
        if (CardCrawlGame.isInARun() && Wiz.adp() != null && Wiz.adp().hasRelic(LocketOfDevotion.ID)) {
            ((LocketOfDevotion) Wiz.adp().getRelic(LocketOfDevotion.ID)).incrementStat(amount);
        }
    }

    public String getStatsDescription() {
        return STAT + stats.get(STAT);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        // You would just return getStatsDescription() if you don't want to display per-combat and per-turn stats
        StringBuilder builder = new StringBuilder();
        builder.append(getStatsDescription());
        float stat = (float)stats.get(STAT);
        // Relic Stats truncates these extended stats to 3 decimal places, so we do the same
        DecimalFormat perTurnFormat = new DecimalFormat("#.###");
        builder.append(PER_TURN);
        builder.append(perTurnFormat.format(stat / Math.max(totalTurns, 1)));
        builder.append(PER_COMBAT);
        builder.append(perTurnFormat.format(stat / Math.max(totalCombats, 1)));
        return builder.toString();
    }

    public void resetStats() {
        stats.put(STAT, 0);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(STAT));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(STAT, jsonArray.get(0).getAsInt());
        } else {
            resetStats();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        // Relic Stats will always query the stats from the instance passed to BaseMod.addRelic()
        // Therefore, we make sure all copies share the same stats by copying the HashMap.
        LocketOfDevotion newRelic = new LocketOfDevotion();
        newRelic.stats = this.stats;
        return newRelic;
    }
}
