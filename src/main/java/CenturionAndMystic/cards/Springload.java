package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.powers.CoilPower;
import CenturionAndMystic.powers.IntimidatedPower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.green.StormOfSteel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static CenturionAndMystic.MainModfile.makeID;

public class Springload extends AbstractCenturionCard {
    public final static String ID = makeID(Springload.class.getSimpleName());

    public Springload() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        //baseBlock = block = 7;
        baseMagicNumber = magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        p.useShakeAnimation(1.0f);
        addToBot(new VFXAction(p, new InflameEffect(p), 0.7F));
        Wiz.applyToSelf(new CoilPower(p, magicNumber));
        Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemy(mon, new IntimidatedPower(mon, magicNumber)));
        //Wiz.applyToSelf(new NextTurnBlockPower(p, block));
    }

    @Override
    public void upp() {
        //upgradeBlock(2);
        upgradeMagicNumber(2);
    }

    @Override
    public String cardArtCopy() {
        return StormOfSteel.ID;
    }
}