package CenturionAndMystic.cardmods;

import CenturionAndMystic.MainModfile;
import CenturionAndMystic.actions.InfusionTriggerAction;
import CenturionAndMystic.cards.cardvars.DynvarInterfaceManager;
import CenturionAndMystic.powers.UnstablePower;
import CenturionAndMystic.util.TexLoader;
import CenturionAndMystic.util.TextureScaler;
import CenturionAndMystic.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class ApplyDestabilizedMod extends AbstractInfusion {
    public static final String ID = MainModfile.makeID(ApplyDestabilizedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    public static final Texture ICON = TextureScaler.rescale(TexLoader.getTexture(MainModfile.makeImagePath("items/MosaicHope.png")), 80, 80);

    static {
        DynvarInterfaceManager.registerDynvarCarrier(ID);
    }

    public ApplyDestabilizedMod(int baseAmount) {
        super(ID, InfusionType.MAGIC, baseAmount, TEXT[0], ICON);
    }

    public ApplyDestabilizedMod(int baseAmount, int relicStatsVal) {
        super(ID, InfusionType.MAGIC, baseAmount, TEXT[0], ICON);
        this.relicStatsVal = relicStatsVal;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        if (card.target == AbstractCard.CardTarget.ALL_ENEMY || card.target == AbstractCard.CardTarget.NONE) {
            card.target = AbstractCard.CardTarget.ENEMY;
        }
        if (card.target == AbstractCard.CardTarget.SELF || card.target == AbstractCard.CardTarget.ALL) {
            card.target = AbstractCard.CardTarget.SELF_AND_ENEMY;
        }
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        Wiz.atb(new InfusionTriggerAction(this, val, relicStatsVal));
        Wiz.atb(new ApplyPowerAction(target, Wiz.adp(), new UnstablePower(target, val)));
    }


    @Override
    public boolean shouldApply(AbstractCard card) {
        if (!usesVanillaTargeting(card)) {
            return false;
        }
        return super.shouldApply(card);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ApplyDestabilizedMod(baseVal, relicStatsVal);
    }
}
