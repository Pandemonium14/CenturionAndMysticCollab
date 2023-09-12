package CenturionAndMystic.cardmods;

import CenturionAndMystic.patches.CustomTags;
import CenturionAndMystic.util.FormatHelper;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static CenturionAndMystic.MainModfile.makeID;

public class PoisedMod extends AbstractCardModifier {
    public static String ID = makeID(PoisedMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    public boolean inherent;

    public PoisedMod(boolean inherent) {
        this.inherent = inherent;
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    public void onInitialApplication(AbstractCard card) {
        card.tags.add(CustomTags.CAM_POISED);
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return FormatHelper.insertBeforeText(rawDescription , TEXT[0]);
    }

    public AbstractCardModifier makeCopy() {
        return new PoisedMod(inherent);
    }

    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return inherent;
    }
}
