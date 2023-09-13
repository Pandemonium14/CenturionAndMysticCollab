package CenturionAndMystic.cards;

import CenturionAndMystic.actions.ModifyCustomEnergyAction;
import CenturionAndMystic.cards.abstracts.AbstractEasyCard;
import CenturionAndMystic.patches.CustomTags;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.green.Doppelganger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Connection extends AbstractEasyCard {
    public final static String ID = makeID(Connection.class.getSimpleName());

    public Connection() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(CustomTags.CAM_MAGIC_EFFECT);
        isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerWhenDrawn() {
        addToTop(new GainEnergyAction(Wiz.mysticEnergy()+Wiz.centurionEnergy()));
        addToTop(new ModifyCustomEnergyAction(-Wiz.mysticEnergy(), ModifyCustomEnergyAction.EnergyType.MYSTIC));
        addToTop(new ModifyCustomEnergyAction(-Wiz.centurionEnergy(), ModifyCustomEnergyAction.EnergyType.CENTURION));
    }

    @Override
    public void upp() {
        isEthereal = false;
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return Doppelganger.ID;
    }
}