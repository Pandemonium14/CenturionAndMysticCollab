package CenturionAndMystic.cardmods;

import CenturionAndMystic.util.FormatHelper;
import CenturionAndMystic.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.RemoveAllTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.ModifyBlockAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class ChargedMod extends AbstractCardModifier {
    public static String ID = makeID(ChargedMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    public boolean inherent;

    public ChargedMod(boolean inherent) {
        this.inherent = inherent;
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (damage >= 0) {
            damage += TempHPField.tempHp.get(AbstractDungeon.player);
        }
        return damage;
    }

    @Override
    public float modifyBlock(float block, AbstractCard card) {
        if (block >= 0) {
            block += TempHPField.tempHp.get(AbstractDungeon.player);
        }
        return block;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (card.baseDamage >= 0) {
            Wiz.atb(new ModifyDamageAction(card.uuid, TempHPField.tempHp.get(Wiz.adp())));
        }
        if (card.baseBlock >= 0 && !(card instanceof RitualDagger)) {
            Wiz.atb(new ModifyBlockAction(card.uuid, TempHPField.tempHp.get(Wiz.adp())));
        }
        Wiz.atb(new RemoveAllTemporaryHPAction(Wiz.adp(), Wiz.adp()));
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return FormatHelper.insertBeforeText(rawDescription , TEXT[0]);
    }

    public AbstractCardModifier makeCopy() {
        return new ChargedMod(inherent);
    }

    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return inherent;
    }
}
