package CenturionAndMystic.cardmods;

import CenturionAndMystic.patches.ForcedUpgradesPatches;
import CenturionAndMystic.util.FormatHelper;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static CenturionAndMystic.MainModfile.makeID;

public class UnlockedMod extends AbstractCardModifier {
    public static String ID = makeID(UnlockedMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    @Override
    public void onInitialApplication(AbstractCard card) {
        ForcedUpgradesPatches.ForcedUpgradeField.inf.set(card, true);
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return FormatHelper.insertBeforeText(rawDescription , TEXT[0]);
    }

    public AbstractCardModifier makeCopy() {
        return new UnlockedMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
