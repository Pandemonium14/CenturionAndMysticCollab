package CenturionAndMystic.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.awt.*;
import java.util.ArrayList;

public class PlayPreviewManager {

    public static ArrayList<PlayPreviewIcon> queuedIcons = new ArrayList<>();
    public static BitmapFont font = FontHelper.cardEnergyFont_L;



    public static void queueIcon(PlayPreviewIcon icon) {
        for (PlayPreviewIcon ic : queuedIcons) {
            if (ic.ID.equals(icon.ID) && ic.target == icon.target) {
                ic.value += icon.value;
                return;
            }
        }
        queuedIcons.add(icon);
    }

    public static void renderIcons(SpriteBatch sb) {
        float offset = 0f;
        for (PlayPreviewIcon i : queuedIcons) {
            i.render(sb, offset);
            offset += i.texture.getWidth();
        }
        queuedIcons.clear();
    }


    public static class PlayPreviewIcon {

        public String ID;
        public Texture texture;
        public Integer value;
        public AbstractCreature target;

        public PlayPreviewIcon(String ID, Texture texture) {
            this.ID = ID;
            this.texture = texture;
        }

        public void queueRender(Integer value, AbstractCreature target) {
            this.value = value;
            this.target = target;
            queueRender();
        }

        public void queueRender() {
            queueIcon(this);
        }

        public void render(SpriteBatch sb, float offset) {
            //Get coordinates
            float x = target.hb.cX + offset;
            float y = target.hb.cY + target.hb.height/2f + 20 * Settings.scale;

            //Render Icon
            sb.draw(texture, x, y);
            //Render number
            if (value != null) {
                FontHelper.renderFontCentered(sb, font, String.valueOf(value), x + texture.getWidth() / 2f, y + texture.getHeight() / 2f);
            }
        }
    }
}
