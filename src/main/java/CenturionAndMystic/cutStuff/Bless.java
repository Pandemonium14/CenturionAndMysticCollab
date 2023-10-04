package CenturionAndMystic.cutStuff;

import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.patches.CustomTags;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.cards.colorless.Enlightenment;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Bless extends AbstractMysticCard {
    public final static String ID = makeID(Bless.class.getSimpleName());

    public Bless() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 8;
        tags.add(CustomTags.CAM_MAGIC_EFFECT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AddTemporaryHPAction(p, p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(3);
    }

    @Override
    public String cardArtCopy() {
        return Enlightenment.ID;
    }
}