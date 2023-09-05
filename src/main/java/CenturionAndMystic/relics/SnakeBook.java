package CenturionAndMystic.relics;

import CenturionAndMystic.CenturionAndMystic;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseTempHpRelic;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static CenturionAndMystic.MainModfile.makeID;

public class SnakeBook extends AbstractEasyRelic implements OnLoseTempHpRelic {
    public static final String ID = makeID(SnakeBook.class.getSimpleName());
    public static final int AMOUNT = 5;
    HashMap<String, Integer> stats = new HashMap<>();
    private final String HP_STAT = DESCRIPTIONS[2];
    private final String PER_TURN = DESCRIPTIONS[3];
    private final String PER_COMBAT = DESCRIPTIONS[4];
    public int amountTriggered;

    public SnakeBook() {
        super(ID, RelicTier.STARTER, LandingSound.MAGICAL, CenturionAndMystic.Enums.SHADOW_BLUE_COLOR);
        resetStats();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        amountTriggered = 0;
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        if (AbstractDungeon.player instanceof CenturionAndMystic) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    CenturionAndMystic cnm = (CenturionAndMystic)AbstractDungeon.player;
                    cnm.mysticHealAnimation();
                    this.isDone = true;
                }
            });
        }
        addToBot(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, AMOUNT));
    }

    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int i) {
        int trigger = Math.min(i, AMOUNT - amountTriggered);
        incrementHealthStat(trigger);
        return i;
    }

    public void incrementHealthStat(int amount) {
        amountTriggered += amount;
        stats.put(HP_STAT, stats.get(HP_STAT) + amount);
    }

    public int getHealthStat() {
        return stats.get(HP_STAT);
    }


    public String getStatsDescription() {
        return HP_STAT + stats.get(HP_STAT);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        // You would just return getStatsDescription() if you don't want to display per-combat and per-turn stats
        StringBuilder builder = new StringBuilder();
        builder.append(getStatsDescription());

        // Relic Stats truncates these extended stats to 3 decimal places, so we do the same
        DecimalFormat perTurnFormat = new DecimalFormat("#.###");

        float stat = (float)stats.get(HP_STAT);
        //builder.append(PER_TURN);
        //builder.append(perTurnFormat.format(stat / Math.max(totalTurns, 1)));
        builder.append(PER_COMBAT);
        builder.append(perTurnFormat.format(stat / Math.max(totalCombats, 1)));
        return builder.toString();
    }

    public void resetStats() {
        stats.put(HP_STAT, 0);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(HP_STAT));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(HP_STAT, jsonArray.get(0).getAsInt());
        } else {
            resetStats();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        // Relic Stats will always query the stats from the instance passed to BaseMod.addRelic()
        // Therefore, we make sure all copies share the same stats by copying the HashMap.
        SnakeBook newRelic = new SnakeBook();
        newRelic.stats = this.stats;
        return newRelic;
    }
}
