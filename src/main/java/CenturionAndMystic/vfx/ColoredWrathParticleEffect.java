package CenturionAndMystic.vfx;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;

public class ColoredWrathParticleEffect extends WrathParticleEffect {
    public ColoredWrathParticleEffect(Color c) {
        super();
        this.color.set(c.r, c.g, c.b, 0f);
    }
}
