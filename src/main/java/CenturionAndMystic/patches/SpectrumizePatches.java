/*
package Professor.patches;

import Professor.MainModfile;
import Professor.util.ImageHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import java.nio.charset.StandardCharsets;

public class SpectrumizePatches {
    private static final ShaderProgram sp = new ShaderProgram(SpriteBatch.createDefaultShader().getVertexShaderSource(), Gdx.files.internal(MainModfile.makePath("shaders/ascii.frag")).readString(String.valueOf(StandardCharsets.UTF_8)));
    private static ShaderProgram oldShader;
    private static final FrameBuffer fbo = ImageHelper.createBuffer();

    @SpirePatch2(clz = AbstractCard.class, method = "render", paramtypez = SpriteBatch.class)
    @SpirePatch2(clz = AbstractCard.class, method = "renderInLibrary", paramtypez = SpriteBatch.class)
    public static class ApplyShaders {
        @SpirePrefixPatch
        public static void Prefix(AbstractCard __instance, SpriteBatch sb) {
            sb.end();
            ImageHelper.beginBuffer(fbo);
            sb.begin();
        }

        @SpirePostfixPatch
        public static void PostFix(AbstractCard __instance, SpriteBatch sb) {
            sb.end();
            fbo.end();
            sb.begin();
            oldShader = sb.getShader();
            sb.setShader(sp);
            sp.setUniformf("x_time", MainModfile.time);
            sp.setUniformf("u_mouse", InputHelper.mX, InputHelper.mY);
            //sp.setUniformf("angleR", (float) Math.toRadians(__instance.angle));
            sb.draw(ImageHelper.getBufferTexture(fbo), -Settings.VERT_LETTERBOX_AMT, -Settings.HORIZ_LETTERBOX_AMT);
            sb.setShader(oldShader);
        }
    }
}
*/
