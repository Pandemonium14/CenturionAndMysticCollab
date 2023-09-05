package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.patches.CustomTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Strike extends AbstractCenturionCard {
    public final static String ID = makeID(Strike.class.getSimpleName());

    public Strike() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = damage = 6;
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
        tags.add(CustomTags.CAM_BASH_EFFECT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public String cardArtCopy() {
        return Bash.ID;
    }
}