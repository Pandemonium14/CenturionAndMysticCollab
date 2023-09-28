package CenturionAndMystic.cards;

import CenturionAndMystic.cards.abstracts.AbstractCenturionCard;
import CenturionAndMystic.cards.interfaces.InHandCard;
import CenturionAndMystic.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Clash;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

import java.util.ArrayList;

import static CenturionAndMystic.MainModfile.makeID;

public class Parry extends AbstractCenturionCard implements InHandCard {
    public final static String ID = makeID(Parry.class.getSimpleName());
    private final ArrayList<AbstractGameAction> capturedActions = new ArrayList<>();
    private boolean justAttacked;

    public Parry() {
        super(ID, -2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.NONE);
        baseBlock = block = 6;
        baseDamage = damage = 10;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (justAttacked || (purgeOnUse && isInAutoplay)) {
            blck();
            if (m != null) {
                this.addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
            }
            dmg(m, AbstractGameAction.AttackEffect.NONE);
        }
        for (AbstractGameAction a : capturedActions) {
            addToBot(a);
        }
        capturedActions.clear();
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return justAttacked || (purgeOnUse && isInAutoplay);
    }

    @Override
    public void onDamaged(DamageInfo info) {
        if (info.owner != null && info.owner != Wiz.adp() && info.type == DamageInfo.DamageType.NORMAL && !justAttacked) {
            justAttacked = true;
            capturedActions.addAll(AbstractDungeon.actionManager.actions);
            AbstractDungeon.actionManager.actions.clear();
            addToTop(new NewQueueCardAction(this, info.owner, true, true));
        }
    }

    @Override
    public void upp() {
        upgradeBlock(2);
        upgradeDamage(2);
    }

    @Override
    public String cardArtCopy() {
        return Clash.ID;
    }

}