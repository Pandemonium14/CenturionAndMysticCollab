package CenturionAndMystic.cards;

import CenturionAndMystic.actions.CallCardAction;
import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.vfx.ColoredThrowDaggerEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Finesse;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;

import static CenturionAndMystic.MainModfile.makeID;

public class CallingCard extends AbstractMysticCard implements CallCardAction.OnCallOtherCard {
    public final static String ID = makeID(CallingCard.class.getSimpleName());

    public CallingCard() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 6;
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new ColoredThrowDaggerEffect(m.hb.cX, m.hb.cY, Color.RED)));
        }
        dmg(m, AbstractGameAction.AttackEffect.NONE);
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Finesse.ID;
    }

    @Override
    public void onCall(List<AbstractCard> calledCards) {
        superFlash();
        addToBot(new ModifyDamageAction(uuid, magicNumber));
    }
}