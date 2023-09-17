package CenturionAndMystic.cards;

import CenturionAndMystic.actions.CallCardAction;
import CenturionAndMystic.cardmods.PoisedMod;
import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.red.Disarm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class FangStrike extends AbstractCenturionCard implements CallCardAction.OnCallThisCard {
    public final static String ID = makeID(FangStrike.class.getSimpleName());

    public FangStrike() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 7;
        baseMagicNumber = magicNumber = 1;
        CardModifierManager.addModifier(this, new PoisedMod(true));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Disarm.ID;
    }

    @Override
    public void onCalled() {
        superFlash();
        addToBot(new CallCardAction(magicNumber, Wiz::isCenturionCard));
    }
}