package CenturionAndMystic.damageMods;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class MightDamage extends AbstractDamageType {

    public MightDamage(boolean inherent, int amount) {
        super(inherent, amount);
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
        return damage + amount;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new MightDamage(inherent, amount);
    }
}
