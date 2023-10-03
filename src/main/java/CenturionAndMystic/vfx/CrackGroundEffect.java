package CenturionAndMystic.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CrackGroundEffect extends AbstractGameEffect {
    private float waveTimer = 0.0F;
    private float x;
    private float y;
    private final float cX;
    private static final float WAVE_INTERVAL = 0.03F;
    private boolean playedSFX;

    public CrackGroundEffect(float cX, float cY) {
        this.y = cY - 20.0F * Settings.scale;
        this.cX = cX;
    }
    public void update() {
        this.waveTimer -= Gdx.graphics.getDeltaTime();
        if (this.waveTimer < WAVE_INTERVAL) {
            this.waveTimer = WAVE_INTERVAL;
            this.x += 160.0F * Settings.scale;
            this.y += 15.0F * Settings.scale;
            AbstractDungeon.effectsQueue.add(new CrackGroundParticle(cX + x, y));
            AbstractDungeon.effectsQueue.add(new CrackGroundParticle(cX - x, y));
            if (!playedSFX) {
                AbstractDungeon.effectsQueue.add(new CrackGroundParticle(cX, y));
                CardCrawlGame.sound.playA("WATCHER_HEART_PUNCH", -0.3F);
                playedSFX = true;
            }
            if (cX + x > Settings.WIDTH) {
                this.isDone = true;
            }
        }
    }

    public void render(SpriteBatch sb) {}

    public void dispose() {}
}
