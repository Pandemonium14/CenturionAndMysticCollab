package CenturionAndMystic.damageMods;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class HexDamage extends AbstractDamageType {

    public HexDamage(boolean inherent) {
        super(inherent);
    }

    public HexDamage(boolean inherent, int amount) {
        super(inherent, amount);
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
        return damage * (1 + amount * PER_STACK);
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new HexDamage(inherent, amount);
    }
}
