package CenturionAndMystic.cards;

import CenturionAndMystic.actions.ApplyPowerActionWithFollowup;
import CenturionAndMystic.cards.abstracts.AbstractEasyCard;
import CenturionAndMystic.patches.EnterCardGroupPatches;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.purple.InnerPeace;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static CenturionAndMystic.MainModfile.makeID;

public class Harmony extends AbstractEasyCard implements EnterCardGroupPatches.OnEnterCardGroupCard {
    public final static String ID = makeID(Harmony.class.getSimpleName());

    public Harmony() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void upp() {
        isEthereal = false;
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return InnerPeace.ID;
    }

    @Override
    public void onEnter(CardGroup g) {
        if (g == Wiz.adp().hand) {
            superFlash();
            Wiz.forAllMonstersLiving(aM -> {
                this.addToBot(new ApplyPowerActionWithFollowup(new ApplyPowerAction(aM, Wiz.adp(), new StrengthPower(aM, -this.magicNumber)), new ApplyPowerAction(aM, Wiz.adp(), new GainStrengthPower(aM, this.magicNumber))));
            });
        }
    }
}