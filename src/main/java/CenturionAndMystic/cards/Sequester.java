package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractMysticCard;
import CenturionAndMystic.powers.SequesterPower;
import CenturionAndMystic.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.SecretTechnique;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CenturionAndMystic.MainModfile.makeID;

public class Sequester extends AbstractMysticCard {
    public final static String ID = makeID(Sequester.class.getSimpleName());

    public Sequester() {
        super(ID, 1, AbstractCard.CardType.SKILL, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(1, ExhaustAction.TEXT[0], l -> {
            for (AbstractCard c : l) {
                Wiz.applyToSelfTop(new SequesterPower(p, c.makeSameInstanceOf(), magicNumber));
                Wiz.att(new ExhaustSpecificCardAction(c, Wiz.adp().hand));
            }
        }));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return SecretTechnique.ID;
    }
}