package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.red.Armaments;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class ShieldBoomerang extends AbstractCenturionCard {
    public final static String ID = makeID(ShieldBoomerang.class.getSimpleName());

    public ShieldBoomerang() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL);
        baseDamage = damage = 7;
        baseBlock = block = 7;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }

    @Override
    public String cardArtCopy() {
        return Armaments.ID;
    }
}