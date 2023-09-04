package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.colorless.DarkShackles;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.EntangleEffect;

import static CenturionAndMystic.MainModfile.makeID;

public class Catch extends AbstractMysticCard {
    public final static String ID = makeID(Catch.class.getSimpleName());

    public Catch() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        //baseDamage = damage = 4;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new VFXAction(new EntangleEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY)));
            addToBot(new SFXAction("POWER_ENTANGLED", 0.05f));
        }
        //dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        Wiz.applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
    }

    @Override
    public void upp() {
        //upgradeDamage(1);
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return DarkShackles.ID;
    }

}