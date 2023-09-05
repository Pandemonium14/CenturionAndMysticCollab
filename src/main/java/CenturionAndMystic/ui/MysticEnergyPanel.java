package CenturionAndMystic.ui;

import CenturionAndMystic.MainModfile;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.IceCream;

public class MysticEnergyPanel implements CustomSavable<Integer> {

    public int energy = 0;
    public static int baseEnergy = 0;

    public Hitbox hb;

    private static final Texture LAYER_0 = ImageMaster.loadImage(MainModfile.makeImagePath("energyOrbs/mystic/layer0.png"), true);
    private static final Texture LAYER_1 = ImageMaster.loadImage(MainModfile.makeImagePath("energyOrbs/mystic/layer1.png"));
    private static final Texture LAYER_2 = ImageMaster.loadImage(MainModfile.makeImagePath("energyOrbs/mystic/layer2.png"));
    private static final Texture LAYER_3 = ImageMaster.loadImage(MainModfile.makeImagePath("energyOrbs/mystic/layer3.png"));
    private static final Texture LAYER_4 = ImageMaster.loadImage(MainModfile.makeImagePath("energyOrbs/mystic/layer4.png"));
    private static final Texture LAYER_5 = ImageMaster.loadImage(MainModfile.makeImagePath("energyOrbs/mystic/layer5.png"), true);
    private static final Texture FLASH_TEXTURE = ImageMaster.loadImage(MainModfile.makeImagePath("energyOrbs/mystic/flash.png"));

    private static final BitmapFont font = FontHelper.energyNumFontBlue;

    private float angle0 = 0;
    private float angle1 = 0;
    private float angle2 = 0;
    private float angle3 = 0;
    private float angle4 = 0;
    private float flashTimer = 0;
    private boolean flashing = false;


    public MysticEnergyPanel(int baseEnergy) {
        MysticEnergyPanel.baseEnergy = baseEnergy;
        //hitbox
    }

    public void atStartOfBattle() {
        trueResetEnergy();
    }

    public void gainEnergy(int e) {
        energy += e;
        flash();
    }

    public void resetEnergy() {
        if (AbstractDungeon.player.hasRelic(IceCream.ID)) {
            energy += baseEnergy;
        } else {
            energy = baseEnergy;
        }
        flash();
    }

    public void trueResetEnergy() {
        energy = baseEnergy;
        flash();
    }

    public void atStartOfTurn() {
        resetEnergy();
    }

    public void flash() {
        flashTimer = 0;
        flashing = true;
    }



    public void render(SpriteBatch sb, float x, float y) {
        float drawX = x - 64.0F + 64.0F * Settings.scale;
        float drawY = y - 64.0F;
        sb.setColor(Color.WHITE);// 34
        sb.draw(LAYER_0, drawX, drawY, 64.0F, 64.0F, 128.0F, 128.0F, 1.15F * Settings.scale, 1.15F * Settings.scale, this.angle0, 0, 0, 128, 128, false, false);
        sb.draw(LAYER_1, drawX, drawY, 64.0F, 64.0F, 128.0F, 128.0F, 1.15F * Settings.scale, 1.15F * Settings.scale, this.angle1, 0, 0, 128, 128, false, false);
        sb.draw(LAYER_2, drawX, drawY, 64.0F, 64.0F, 128.0F, 128.0F, 1.15F * Settings.scale, 1.15F * Settings.scale, this.angle2, 0, 0, 128, 128, false, false);
        sb.draw(LAYER_3, drawX, drawY, 64.0F, 64.0F, 128.0F, 128.0F, 1.15F * Settings.scale, 1.15F * Settings.scale, this.angle3, 0, 0, 128, 128, false, false);
        sb.draw(LAYER_4, drawX, drawY, 64.0F, 64.0F, 128.0F, 128.0F, 1.15F * Settings.scale, 1.15F * Settings.scale, this.angle4, 0, 0, 128, 128, false, false);
        sb.draw(LAYER_5, drawX, drawY, 64.0F, 64.0F, 128.0F, 128.0F, 1.15F * Settings.scale, 1.15F * Settings.scale, this.angle0, 0, 0, 128, 128, false, false);
        angle1 += Gdx.graphics.getDeltaTime() * -25.0F;
        angle2 += Gdx.graphics.getDeltaTime() * 20.0F;
        angle3 += Gdx.graphics.getDeltaTime() * -17.0F;
        angle4 += Gdx.graphics.getDeltaTime() * 30.0F;

        if (flashing) {
            sb.setColor(new Color(1f,1f,1f,1/((1 + flashTimer*10f) * (1 + flashTimer*10f))));
            sb.draw(FLASH_TEXTURE, drawX, drawY, 64.0F, 64.0F, 128.0F, 128.0F, flashTimer * Settings.scale + 1.15F * Settings.scale, flashTimer * Settings.scale + Settings.scale * 1.15f, this.angle0, 0, 0, 128, 128, false, false);
            flashTimer += 0.01f;
            if(flashTimer >= 1.2f) {
                flashing = false;
                flashTimer = 0;
            }
        }

        FontHelper.renderFontCentered(sb, font, energy+"/"+baseEnergy, drawX +64f, drawY+64f, Color.WHITE);
    }


    @Override
    public Integer onSave() {
        return baseEnergy;
    }

    @Override
    public void onLoad(Integer i) {
        if (i != null) baseEnergy = i;
    }


}