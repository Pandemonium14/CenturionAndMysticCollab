package CenturionAndMystic.cards;

import CenturionAndMystic.actions.InfuseCardsInHandAction;
import CenturionAndMystic.cardmods.EtherealMod;
import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.powers.ImmaterialPower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.cards.green.WraithForm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class TapTheImmaterial extends AbstractMysticCard {
    public final static String ID = makeID(TapTheImmaterial.class.getSimpleName());

    public TapTheImmaterial() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new InfuseCardsInHandAction(p.hand.size(), new EtherealMod()));
        Wiz.applyToSelf(new ImmaterialPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return WraithForm.ID;
    }
}