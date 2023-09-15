package CenturionAndMystic.cutStuff;

import CenturionAndMystic.actions.StarterAction;
import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.patches.CustomTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.red.SecondWind;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class InstigatingBlow extends AbstractCenturionCard {
    public final static String ID = makeID(InstigatingBlow.class.getSimpleName());

    public InstigatingBlow() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 8;
        tags.add(CustomTags.CAM_BASH_EFFECT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY, true);
        addToBot(new StarterAction(this));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public String cardArtCopy() {
        return SecondWind.ID;
    }

}
