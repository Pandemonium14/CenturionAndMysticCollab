package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.patches.CustomTags;
import CenturionAndMystic.powers.IntimidatedPower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.red.Rage;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Fury extends AbstractCenturionCard {
    public final static String ID = makeID(Fury.class.getSimpleName());

    public Fury() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 4;
        baseMagicNumber = magicNumber = 3;
        baseSecondMagic = secondMagic = 9;
        tags.add(CustomTags.CAM_BASH_EFFECT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0 ; i < magicNumber ; i++) {
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY, true);
        }
        Wiz.applyToEnemy(m, new IntimidatedPower(m, secondMagic));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }

    @Override
    public String cardArtCopy() {
        return Rage.ID;
    }
}