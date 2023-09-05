package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.cards.interfaces.MagicAnimation;
import CenturionAndMystic.patches.CustomTags;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.cards.colorless.BandageUp;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class PatchUp extends AbstractMysticCard implements MagicAnimation {
    public final static String ID = makeID(PatchUp.class.getSimpleName());

    public PatchUp() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        tags.add(CustomTags.CAM_MAGIC_EFFECT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AddTemporaryHPAction(p, p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public String cardArtCopy() {
        return BandageUp.ID;
    }
}