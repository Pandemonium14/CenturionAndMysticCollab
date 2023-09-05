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
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static CenturionAndMystic.MainModfile.makeID;

public class LostBook extends AbstractEasyRelic implements OnLoseTempHpRelic {
    public static final String ID = makeID(LostBook.class.getSimpleName());
    public static final int AMOUNT = 5;
    HashMap<String, Integer> stats = new HashMap<>();
    private final String STAT = DESCRIPTIONS[2];
    private final String PER_TURN = DESCRIPTIONS[3];
    private final String PER_COMBAT = DESCRIPTIONS[4];
    private int amountGained;
    private int amountTriggered;

    public LostBook() {
        super(ID, RelicTier.BOSS, LandingSound.MAGICAL, CenturionAndMystic.Enums.SHADOW_BLUE_COLOR);
        resetStats();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        amountGained = amountTriggered = 0;
    }

    @Override
    public void wasHPLost(int damageAmount) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && damageAmount > 0) {
            flash();
            amountGained += AMOUNT;
            addToTop(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, AMOUNT));
            if (AbstractDungeon.player instanceof CenturionAndMystic) {
                addToTop(new AbstractGameAction() {
                    @Override
                    public void update() {
                        CenturionAndMystic cnm = (CenturionAndMystic) AbstractDungeon.player;
                        cnm.mysticHealAnimation();
                        this.isDone = true;
                    }
                });
            }
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int i) {
        int trigger = Math.min(i, amountGained - amountTriggered);
        incrementStat(trigger);
        return i;
    }

    @Override //Should replace default relic.
    public void obtain() {
        //Grab the player
        AbstractPlayer p = AbstractDungeon.player;
        //If we have the starter relic...
        if (p.hasRelic(SnakeBook.ID)) {
            //Grab its data for relic stats if you want to carry the stats over to the boss relic
            SnakeBook mb = (SnakeBook) p.getRelic(SnakeBook.ID);
            stats.put(STAT, mb.getHealthStat());
            //Find it...
            for (int i = 0; i < p.relics.size(); ++i) {
                if (p.relics.get(i).relicId.equals(SnakeBook.ID)) {
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
        return AbstractDungeon.player.hasRelic(SnakeBook.ID);
    }

    public void incrementStat(int amount) {
        amountTriggered += amount;
        stats.put(STAT, stats.get(STAT) + amount);
    }

    public String getStatsDescription() {
        return STAT + stats.get(STAT);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        // You would just return getStatsDescription() if you don't want to display per-combat and per-turn stats
        StringBuilder builder = new StringBuilder();
        builder.append(getStatsDescription());
        float stat = (float) stats.get(STAT);
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
        LostBook newRelic = new LostBook();
        newRelic.stats = this.stats;
        return newRelic;
    }
}
