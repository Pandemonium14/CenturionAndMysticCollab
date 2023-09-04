package CenturionAndMystic.relics;

import CenturionAndMystic.CenturionAndMystic;
import CenturionAndMystic.util.Wiz;
import CenturionAndMystic.vfx.BarbExplodeEffect;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.BindingHelper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static CenturionAndMystic.MainModfile.makeID;

public class UniCharm extends AbstractEasyRelic {
    public static final String ID = makeID(UniCharm.class.getSimpleName());
    private static final int AMOUNT = 8;
    HashMap<String, Integer> stats = new HashMap<>();
    private final String DAMAGE_STAT = DESCRIPTIONS[1];
    private final String DAMAGE_PER_COMBAT = DESCRIPTIONS[2];
    private static final List<AbstractDamageModifier> uniDamage = Collections.singletonList(new UniDamage());

    public UniCharm() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT, CenturionAndMystic.Enums.SHADOW_BLUE_COLOR);
        resetStats();
    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != Wiz.adp() && info.type == DamageInfo.DamageType.NORMAL && !grayscale) {
            flash();
            addToTop(BindingHelper.makeAction(uniDamage, new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(AMOUNT, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE)));
            addToTop(new VFXAction(new BarbExplodeEffect(Color.BROWN), 0.2f));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            grayscale = true;
        }
        return damageAmount;
    }

    @Override
    public void playLandingSFX() {
        CardCrawlGame.sound.play("ATTACK_FAST");
    }

    public void updateDamage(int damage) {
        stats.put(DAMAGE_STAT, stats.get(DAMAGE_STAT) + damage);
    }

    public String getStatsDescription() {
        return DAMAGE_STAT + stats.get(DAMAGE_STAT);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        // You would just return getStatsDescription() if you don't want to display per-combat and per-turn stats
        StringBuilder builder = new StringBuilder();
        builder.append(getStatsDescription());
        float stat = stats.get(DAMAGE_STAT);
        // Relic Stats truncates these extended stats to 3 decimal places, so we do the same
        DecimalFormat perTurnFormat = new DecimalFormat("#.###");
        builder.append(DAMAGE_PER_COMBAT);
        builder.append(perTurnFormat.format(stat / Math.max(totalCombats, 1)));
        return builder.toString();
    }

    public void resetStats() {
        stats.put(DAMAGE_STAT, 0);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(DAMAGE_STAT));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(DAMAGE_STAT, jsonArray.get(0).getAsInt());
        } else {
            resetStats();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        // Relic Stats will always query the stats from the instance passed to BaseMod.addRelic()
        // Therefore, we make sure all copies share the same stats by copying the HashMap.
        UniCharm newRelic = new UniCharm();
        newRelic.stats = this.stats;
        return newRelic;
    }

    private static class UniDamage extends AbstractDamageModifier {
        @Override
        public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature target) {
            if (Wiz.adp().hasRelic(UniCharm.ID)) {
                UniCharm uc = (UniCharm) Wiz.adp().getRelic(UniCharm.ID);
                uc.updateDamage(lastDamageTaken);
            }
        }

        @Override
        public AbstractDamageModifier makeCopy() {
            return new UniDamage();
        }
    }
}
