package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.MeteorStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import static CenturionAndMystic.MainModfile.makeID;

public class FinalImpact extends AbstractCenturionCard {
    public final static String ID = makeID(FinalImpact.class.getSimpleName());

    public FinalImpact() {
        super(ID, 4, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = damage = 30;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        }
        this.addToBot(new WaitAction(0.8F));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        if (cardPlayed.type == CardType.ATTACK) {
            setCostForTurn(this.costForTurn - 1);
        }
    }

    public void triggerWhenDrawn() {
        setCostForTurn((int) (this.cost - AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().filter(c -> c.type == CardType.ATTACK).count()));
    }

    public void atTurnStart() {
        resetAttributes();
        applyPowers();
    }

    @Override
    public AbstractCard makeCopy() {
        FinalImpact fi = new FinalImpact();
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            fi.setCostForTurn((int) (this.cost - AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().filter(c -> c.type == CardType.ATTACK).count()));
        }
        return fi;
    }

    @Override
    public void upp() {
        upgradeDamage(10);
    }

    @Override
    public String cardArtCopy() {
        return MeteorStrike.ID;
    }
}