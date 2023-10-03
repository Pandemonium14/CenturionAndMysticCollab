package CenturionAndMystic.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CrackGroundParticle extends AbstractGameEffect {
    private static final float EFFECT_DUR = 0.5F;
    private final float x;
    private float y;
    private final float startY;
    private final float targetY;
    private static TextureAtlas.AtlasRegion img;
    private boolean impactHook = false;

    public CrackGroundParticle(float x, float y) {
        if (img == null) {// 24
            img = ImageMaster.vfxAtlas.findRegion("combat/weightyImpact");// 25
        }

        this.scale = Settings.scale;// 29
        this.x = x - (float)img.packedWidth / 2.0F;// 30
        this.y = this.startY = AbstractDungeon.floorY - (float)img.packedHeight / 2.0F - 180 * Settings.scale;// 31
        this.duration = EFFECT_DUR;// 32
        this.targetY = y;// 33
        this.rotation = 180f;// 35
        this.color = new Color(1.0F, 1.0F, 0.1F, 0.0F);// 36
    }// 37

    public void update() {
        this.y = Interpolation.fade.apply(startY, this.targetY, 1.0F - this.duration / EFFECT_DUR);// 40
        this.scale += Gdx.graphics.getDeltaTime();// 42
        this.duration -= Gdx.graphics.getDeltaTime();// 43
        if (this.duration < 0.0F) {// 45
            this.isDone = true;// 46
        } else if (this.duration < 0.2F) {// 47
            if (!this.impactHook) {// 48
                this.impactHook = true;// 49
                CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);// 51
            }

            this.color.a = Interpolation.pow2Out.apply(0.0F, 1.0F, this.duration * 5.0F);// 67
        } else {
            this.color.a = Interpolation.fade.apply(1.0F, 0.0F, this.duration / EFFECT_DUR);// 69
        }

    }// 71

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);// 75
        this.color.g = 1.0F;// 76
        sb.setColor(this.color);// 77
        sb.draw(img, this.x, this.y + 140.0F * Settings.scale, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth / 2.0F, (float)img.packedHeight * (this.duration + 0.2F) * 3.0F, this.scale * MathUtils.random(0.99F, 1.01F) * 0.5F, this.scale * MathUtils.random(0.99F, 1.01F) * 2.0F * (this.duration + 0.8F), this.rotation);// 78 86 87
        this.color.g = 0.7F;// 89
        sb.setColor(this.color);// 90
        sb.draw(img, this.x - 50.0F * Settings.scale, this.y + 140.0F * Settings.scale, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth / 2.0F, (float)img.packedHeight * (this.duration + 0.2F) * 2.0F, this.scale * MathUtils.random(0.99F, 1.01F) * 0.6F, this.scale * MathUtils.random(0.99F, 1.01F) * 2.0F * (this.duration + 0.8F), this.rotation);// 91 99 100
        this.color.g = 0.5F;// 102
        sb.setColor(this.color);// 103
        sb.draw(img, this.x - 100.0F * Settings.scale, this.y + 140.0F * Settings.scale, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight * (this.duration + 0.2F) * 1.0F, this.scale * MathUtils.random(0.99F, 1.01F) * 0.75F, this.scale * MathUtils.random(0.99F, 1.01F) * 2.0F * (this.duration + 0.8F), this.rotation);// 104 112 113
        sb.setBlendFunction(770, 771);// 115
    }// 116

    public void dispose() {
    }// 121
}
