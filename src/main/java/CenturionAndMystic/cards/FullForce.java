package CenturionAndMystic.cards;

import CenturionAndMystic.cardmods.PoisedMod;
import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.patches.CustomTags;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.red.Headbutt;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class FullForce extends AbstractCenturionCard {
    public final static String ID = makeID(FullForce.class.getSimpleName());

    public FullForce() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 8;
        baseBlock = block = 8;
        tags.add(CustomTags.CAM_BASH_EFFECT);
        CardModifierManager.addModifier(this, new PoisedMod(true));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }

    @Override
    public String cardArtCopy() {
        return Headbutt.ID;
    }
}