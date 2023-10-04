package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.powers.IntimidatedPower;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.red.Intimidate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.IntimidateEffect;

import static CenturionAndMystic.MainModfile.makeID;

public class Terrorize extends AbstractCenturionCard {
    public final static String ID = makeID(Terrorize.class.getSimpleName());

    public Terrorize() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("INTIMIDATE"));
        addToBot(new VFXAction(p, new IntimidateEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.3F));
        Wiz.applyToEnemy(m, new IntimidatedPower(m, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(3);
    }

    @Override
    public String cardArtCopy() {
        return Intimidate.ID;
    }

}