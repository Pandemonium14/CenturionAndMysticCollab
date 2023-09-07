package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.powers.VenomPower;
import CenturionAndMystic.util.Wiz;
import CenturionAndMystic.vfx.ColoredSmokeBombEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.green.CripplingPoison;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static CenturionAndMystic.MainModfile.makeID;

public class BlackMist extends AbstractMysticCard {
    public final static String ID = makeID(BlackMist.class.getSimpleName());
    private static final Color c = darken(DARK_GRAY);

    public BlackMist() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 5;
        baseSecondMagic = secondMagic = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.forAllMonstersLiving(mon -> {
            addToBot(new VFXAction(new ColoredSmokeBombEffect(mon.hb.cX, mon.hb.cY, c)));
            Wiz.applyToEnemy(mon, new VenomPower(mon, magicNumber));
            Wiz.applyToEnemy(mon, new WeakPower(mon, secondMagic, false));
        });
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
        upgradeSecondDamage(1);
    }

    @Override
    public String cardArtCopy() {
        return CripplingPoison.ID;
    }

}