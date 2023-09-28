package CenturionAndMystic.util;

import CenturionAndMystic.CenturionAndMystic;
import CenturionAndMystic.actions.TimedVFXAction;
import CenturionAndMystic.patches.CardCounterPatches;
import CenturionAndMystic.patches.CustomTags;
import CenturionAndMystic.patches.EnergyPatches;
import CenturionAndMystic.powers.AbstractInfusionPower;
import CenturionAndMystic.powers.LosePowerPower;
import CenturionAndMystic.powers.NextTurnPowerPower;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Wiz {
    //The wonderful Wizard of Oz allows access to most easy compilations of data, or functions.

    public static AbstractPlayer adp() {
        return AbstractDungeon.player;
    }

    public static void forAllCardsInList(Consumer<AbstractCard> consumer, ArrayList<AbstractCard> cardsList) {
        for (AbstractCard c : cardsList) {
            consumer.accept(c);
        }
    }

    public static ArrayList<AbstractCard> getAllCardsInCardGroups(boolean includeHand, boolean includeExhaust) {
        ArrayList<AbstractCard> masterCardsList = new ArrayList<>();
        masterCardsList.addAll(AbstractDungeon.player.drawPile.group);
        masterCardsList.addAll(AbstractDungeon.player.discardPile.group);
        if (includeHand) {
            masterCardsList.addAll(AbstractDungeon.player.hand.group);
        }
        if (includeExhaust) {
            masterCardsList.addAll(AbstractDungeon.player.exhaustPile.group);
        }
        return masterCardsList;
    }

    public static void forAllMonstersLiving(Consumer<AbstractMonster> consumer) {
        for (AbstractMonster m : getEnemies()) {
            consumer.accept(m);
        }
    }

    public static ArrayList<AbstractMonster> getEnemies() {
        ArrayList<AbstractMonster> monsters = new ArrayList<>(AbstractDungeon.getMonsters().monsters);
        monsters.removeIf(m -> m.isDead || m.isDying);
        return monsters;
    }

    public static ArrayList<AbstractCard> getCardsMatchingPredicate(Predicate<AbstractCard> pred) {
        return getCardsMatchingPredicate(pred, false);
    }

    public static ArrayList<AbstractCard> getCardsMatchingPredicate(Predicate<AbstractCard> pred, boolean allcards) {
        if (allcards) {
            ArrayList<AbstractCard> cardsList = new ArrayList<>();
            for (AbstractCard c : CardLibrary.getAllCards()) {
                if (pred.test(c)) cardsList.add(c.makeStatEquivalentCopy());
            }
            return cardsList;
        } else {
            ArrayList<AbstractCard> cardsList = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.srcCommonCardPool.group) {
                if (pred.test(c)) cardsList.add(c.makeStatEquivalentCopy());
            }
            for (AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
                if (pred.test(c)) cardsList.add(c.makeStatEquivalentCopy());
            }
            for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
                if (pred.test(c)) cardsList.add(c.makeStatEquivalentCopy());
            }
            return cardsList;
        }
    }

    public static AbstractCard returnTrulyRandomPrediCardInCombat(Predicate<AbstractCard> pred, boolean allCards) {
        return getRandomItem(getCardsMatchingPredicate(pred, allCards));
    }


    public static AbstractCard returnTrulyRandomPrediCardInCombat(Predicate<AbstractCard> pred) {
        return returnTrulyRandomPrediCardInCombat(pred, false);
    }

    public static <T> T getRandomItem(ArrayList<T> list, Random rng) {
        return list.isEmpty() ? null : list.get(rng.random(list.size() - 1));
    }

    public static <T> T getRandomItem(ArrayList<T> list) {
        return getRandomItem(list, AbstractDungeon.cardRandomRng);
    }

    private static boolean actuallyHovered(Hitbox hb) {
        return InputHelper.mX > hb.x && InputHelper.mX < hb.x + hb.width && InputHelper.mY > hb.y && InputHelper.mY < hb.y + hb.height;
    }

    public static boolean isInCombat() {
        return CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    public static boolean isCombatCard(AbstractCard card) {
        return !Wiz.adp().masterDeck.contains(card) && !CardCrawlGame.cardPopup.isOpen;
    }

    public static void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static void att(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public static void vfx(AbstractGameEffect gameEffect) {
        atb(new VFXAction(gameEffect));
    }

    public static void vfx(AbstractGameEffect gameEffect, float duration) {
        atb(new VFXAction(gameEffect, duration));
    }

    public static void tfx(AbstractGameEffect gameEffect) {
        atb(new TimedVFXAction(gameEffect));
    }

    public static void makeInHand(AbstractCard c, int i) {
        atb(new MakeTempCardInHandAction(c, i));
    }

    public static void makeInHand(AbstractCard c) {
        makeInHand(c, 1);
    }

    public static void shuffleIn(AbstractCard c, int i) {
        atb(new MakeTempCardInDrawPileAction(c, i, true, true));
    }

    public static void shuffleIn(AbstractCard c) {
        shuffleIn(c, 1);
    }

    public static void topDeck(AbstractCard c, int i) {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, i, false, true));
    }

    public static void topDeck(AbstractCard c) {
        topDeck(c, 1);
    }

    public static void applyToEnemy(AbstractMonster m, AbstractPower po) {
        atb(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    public static void applyToEnemyTop(AbstractMonster m, AbstractPower po) {
        att(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    public static void applyToSelf(AbstractPower po) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    public static void applyToSelfTop(AbstractPower po) {
        att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    public static void applyToSelfTemp(AbstractPower po) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LosePowerPower(AbstractDungeon.player, po, po.amount)));
    }

    public static void applyToSelfNextTurn(AbstractPower po) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new NextTurnPowerPower(AbstractDungeon.player, po)));
    }

    public static boolean isUnaware(AbstractMonster m) {
        return m != null && !m.isDeadOrEscaped() && m.getIntentDmg() < 5;
    }

    public static boolean anyMonsterUnaware() {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (isUnaware(m)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAttacking(AbstractMonster m) {
        return m != null && !m.isDeadOrEscaped() && m.getIntentBaseDmg() >= 0;
    }

    public static boolean anyMonsterAttacking() {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (isAttacking(m)) {
                return true;
            }
        }
        return false;
    }

    public static boolean anyMonsterNotAttacking() {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!isAttacking(m)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isScavenged(AbstractCard c) {
        return !CardCounterPatches.initialHand.contains(c) || CardCounterPatches.cardsDrawnThisTurn.lastIndexOf(c) >= CardCounterPatches.initialHand.size();
    }

    public static List<AbstractCard> cardsPlayedThisCombat() {
        if (isInCombat()) {
            return AbstractDungeon.actionManager.cardsPlayedThisCombat;
        }
        return Collections.emptyList();
    }

    public static List<AbstractCard> cardsPlayedThisTurn() {
        if (isInCombat()) {
            return AbstractDungeon.actionManager.cardsPlayedThisTurn;
        }
        return Collections.emptyList();
    }

    public static AbstractCard lastCardPlayed() {
        if (isInCombat() && !cardsPlayedThisCombat().isEmpty()) {
            return cardsPlayedThisCombat().get(cardsPlayedThisCombat().size()-1);
        }
        return null;
    }

    public static AbstractCard lastCardPlayedThisTurn() {
        if (isInCombat() && !cardsPlayedThisTurn().isEmpty()) {
            return cardsPlayedThisTurn().get(cardsPlayedThisTurn().size()-1);
        }
        return null;
    }

    public static AbstractCard secondLastCardPlayed() {
        if (isInCombat() && cardsPlayedThisCombat().size() >= 2) {
            return cardsPlayedThisCombat().get(cardsPlayedThisCombat().size()-2);
        }
        return null;
    }

    public static void forAdjacentCards(AbstractCard thisCard, Consumer<AbstractCard> consumer) {
        int lastIndex = Wiz.adp().hand.group.indexOf(thisCard);
        if (lastIndex != -1) {
            if (lastIndex > 0) {
                consumer.accept(Wiz.adp().hand.group.get(lastIndex - 1));
            }
            if (lastIndex < Wiz.adp().hand.group.size()-1) {
                consumer.accept(Wiz.adp().hand.group.get(lastIndex + 1));
            }
        }
    }

    public static ArrayList<AbstractCard> getAdjacentCards(AbstractCard thisCard) {
        ArrayList<AbstractCard> ret = new ArrayList<>();
        forAdjacentCards(thisCard, ret::add);
        return ret;
    }

    public static int mysticEnergy() {
        if (AbstractDungeon.player instanceof CenturionAndMystic && EnergyPatches.ExtraPanelFields.mysticEnergyPanel.get(AbstractDungeon.player) != null) {
            return EnergyPatches.ExtraPanelFields.mysticEnergyPanel.get(AbstractDungeon.player).energy;
        }
        return 0;
    }

    public static int centurionEnergy() {
        if (AbstractDungeon.player instanceof CenturionAndMystic && EnergyPatches.ExtraPanelFields.centurionEnergyPanel.get(AbstractDungeon.player) != null) {
            return EnergyPatches.ExtraPanelFields.centurionEnergyPanel.get(AbstractDungeon.player).energy;
        }
        return 0;
    }

    public static boolean hasInfusion() {
        return AbstractDungeon.player.powers.stream().anyMatch(p -> p instanceof AbstractInfusionPower);
    }

    public static int infusionCount() {
        return (int) AbstractDungeon.player.powers.stream().filter(p -> p instanceof AbstractInfusionPower).count();
    }

    public static boolean isMysticCard(AbstractCard c) {
        return EnergyPatches.isMysticCard(c);
    }

    public static boolean isCenturionCard(AbstractCard c) {
        return EnergyPatches.isCenturionCard(c);
    }

    public static int getHitIntents(AbstractMonster m) {
        if (!Wiz.isAttacking(m)) {
            return 0;
        }
        if (ReflectionHacks.getPrivate(m, AbstractMonster.class, "isMultiDmg")) {
            return ReflectionHacks.getPrivate(m, AbstractMonster.class, "intentMultiAmt");
        }
        return 1;
    }
}
