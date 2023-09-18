package CenturionAndMystic.cards;

import CenturionAndMystic.actions.ModifyCustomEnergyAction;
import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.patches.CustomTags;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.purple.ThirdEye;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Connection extends AbstractMysticCard {
    public final static String ID = makeID(Connection.class.getSimpleName());

    public Connection() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(CustomTags.CAM_MAGIC_EFFECT);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ModifyCustomEnergyAction(-Wiz.centurionEnergy(), ModifyCustomEnergyAction.EnergyType.CENTURION));
        addToBot(new ModifyCustomEnergyAction(-Wiz.mysticEnergy(), ModifyCustomEnergyAction.EnergyType.MYSTIC));
        addToBot(new GainEnergyAction(Wiz.mysticEnergy()+Wiz.centurionEnergy()));
    }

    @Override
    public void upp() {
        exhaust = false;
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return ThirdEye.ID;
    }
}