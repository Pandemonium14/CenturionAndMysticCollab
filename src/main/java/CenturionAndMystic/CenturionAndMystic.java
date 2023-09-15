package CenturionAndMystic;

import CenturionAndMystic.cards.*;
import CenturionAndMystic.patches.CustomTags;
import CenturionAndMystic.patches.EnergyPatches;
import CenturionAndMystic.relics.SnakeBook;
import CenturionAndMystic.ui.CenturionEnergyPanel;
import CenturionAndMystic.ui.MysticEnergyPanel;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.scenes.TheCityScene;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;
import java.util.List;

import static CenturionAndMystic.MainModfile.*;

public class CenturionAndMystic extends CustomPlayer {
    private static final String[] orbTextures = {
            modID + "Resources/images/char/mainChar/orb/layer1.png",
            modID + "Resources/images/char/mainChar/orb/layer2.png",
            modID + "Resources/images/char/mainChar/orb/layer3.png",
            modID + "Resources/images/char/mainChar/orb/layer4.png",
            modID + "Resources/images/char/mainChar/orb/layer5.png",
            modID + "Resources/images/char/mainChar/orb/layer6.png",
            modID + "Resources/images/char/mainChar/orb/layer1d.png",
            modID + "Resources/images/char/mainChar/orb/layer2d.png",
            modID + "Resources/images/char/mainChar/orb/layer3d.png",
            modID + "Resources/images/char/mainChar/orb/layer4d.png",
            modID + "Resources/images/char/mainChar/orb/layer5d.png",};
    static final String ID = makeID("CenturionAndMystic");
    static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    static final String[] NAMES = characterStrings.NAMES;
    static final String[] TEXT = characterStrings.TEXT;

    private final SpineAnimation mysticSpine = new SpineAnimation("images/monsters/theCity/healer/skeleton.atlas", "images/monsters/theCity/healer/skeleton.json", 1f);
    private final TextureAtlas mysticAtlas = new TextureAtlas(mysticSpine.atlasUrl);
    private final Skeleton mysticSkeleton;
    private final AnimationStateData mysticStateData;
    private final AnimationState mysticState;

    private static final Color LIGHT_COLOR = new Color(1.0F, 1.0F, 0.2F, 1.0F);


    public CenturionAndMystic(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, modID + "Resources/images/char/mainChar/orb/vfx.png", null, new SpineAnimation("images/monsters/theCity/tank/skeleton.atlas", "images/monsters/theCity/tank/skeleton.json", 1.0f));
        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), -14.0F, -20.0F, 250.0F, 330.0F, new EnergyManager(0));

        EnergyPatches.ExtraPanelFields.centurionEnergyPanel.set(this, new CenturionEnergyPanel(2));
        EnergyPatches.ExtraPanelFields.mysticEnergyPanel.set(this, new MysticEnergyPanel(2));


        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);

        state.setTimeScale(0.8f);
        state.setAnimation(0, "Idle", true);

        SkeletonJson json = new SkeletonJson(mysticAtlas);
        json.setScale(Settings.renderScale / mysticSpine.scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(mysticSpine.skeletonUrl));
        mysticSkeleton = new Skeleton(skeletonData);
        mysticStateData = new AnimationStateData(skeletonData);
        mysticState = new AnimationState(mysticStateData);
        mysticState.setAnimation(0, "Idle", true);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                80,
                80,
                0,
                99,
                5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Taunt.ID);
        retVal.add(Strike2.ID);
        retVal.add(Strike2.ID);
        retVal.add(Defend2.ID);
        retVal.add(Defend2.ID);
        retVal.add(Rattle.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(SnakeBook.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("HEAL_1", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "HEAL_1";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.SHADOW_BLUE_COLOR;
    }

    @Override
    public Color getCardTrailColor() {
        return MainModfile.SHADOW_BLUE_COLOR.cpy();
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Taunt();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new CenturionAndMystic(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return MainModfile.SHADOW_BLUE_COLOR.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return MainModfile.SHADOW_BLUE_COLOR.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE};
    }

    @Override
    public Texture getCutsceneBg() {
        return ImageMaster.loadImage("CenturionAndMysticResources/images/panels/bkg.png");
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel("CenturionAndMysticResources/images/panels/HeartPanel.png", "UNLOCK_PING"));
        return panels;
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass CENTURION_AND_MYSTIC;
        @SpireEnum(name = "SHADOW_BLUE_COLOR")
        public static AbstractCard.CardColor SHADOW_BLUE_COLOR;
        @SpireEnum(name = "SHADOW_BLUE_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.output>0) {
            centurionHurtAnimation();
        }
    }

    @Override
    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        super.useCard(c, monster, energyOnUse);
        if (c.hasTag(CustomTags.CAM_MAGIC_EFFECT)) {
            mysticHealAnimation();
        }
        if (c.hasTag(CustomTags.CAM_BASH_EFFECT)) {
            centurionAttackAnimation();
        }
    }

    public void centurionAttackAnimation() {
        state.setAnimation(0, "Attack", false);
        state.addAnimation(0,"Idle", true, 0.0f);
    }

    public void centurionHurtAnimation() {
        state.setAnimation(0, "Hit", false);
        state.addAnimation(0,"Idle", true, 0.0f);
    }

    public void mysticHealAnimation() {
        mysticState.setAnimation(0, "Attack", false);
        mysticState.addAnimation(0,"Idle", true, 0.0f);
    }

    public void mysticHurtAnimation() {
        mysticState.setAnimation(0, "Hit", false);
        mysticState.addAnimation(0,"Idle", true, 0.0f);
    }

    @Override
    public void renderPlayerImage(SpriteBatch sb) {
        flipHorizontal = !flipHorizontal;
        super.renderPlayerImage(sb);
        if (AbstractDungeon.getCurrMapNode() != null && !(AbstractDungeon.getCurrRoom() instanceof RestRoom)) {// 2120
            if (this.atlas != null && !(boolean) ReflectionHacks.getPrivate(this, AbstractPlayer.class, "renderCorpse")) {
                renderMystic(sb);
            }
        }
        flipHorizontal = !flipHorizontal;
    }

    private void renderMystic(SpriteBatch sb) {
        float mX = drawX - 240 * Settings.scale;
        float mY = drawY;
        if (AbstractDungeon.scene instanceof TheCityScene && ReflectionHacks.<Boolean>getPrivate(AbstractDungeon.scene, TheCityScene.class, "renderMg")) {
            mX += 85f * Settings.scale;
            mY -= 15f * Settings.scale;
        }
        mysticState.update(Gdx.graphics.getDeltaTime());// 2157
        mysticState.apply(mysticSkeleton);// 2158
        mysticSkeleton.updateWorldTransform();// 2159
        mysticSkeleton.setPosition(mX + this.animX, mY + this.animY);// 2160
        mysticSkeleton.setColor(this.tint.color);// 2163
        mysticSkeleton.setFlip(this.flipHorizontal, this.flipVertical);// 2164
        sb.end();// 2165
        CardCrawlGame.psb.begin();// 2166
        sr.draw(CardCrawlGame.psb, mysticSkeleton);// 2167
        CardCrawlGame.psb.end();// 2168
        sb.begin();// 2169
    }

    public void renderClassOrbs(SpriteBatch sb, float current_x, float current_y) {
        EnergyPatches.ExtraPanelFields.centurionEnergyPanel.get(this).render(sb, current_x, current_y);
        EnergyPatches.ExtraPanelFields.mysticEnergyPanel.get(this).render(sb, current_x, current_y);
    }

    public float[] _lightsOutGetCharSelectXYRI() {
        return new float[] {
                1524*Settings.scale, 526*Settings.scale, 100f, 1.0f,
                78*Settings.scale, 351*Settings.scale, 400f, 1.2f
        };
    }

    public Color[] _lightsOutGetCharSelectColor() {
        return new Color[] {
                LIGHT_COLOR,
                LIGHT_COLOR
        };
    }
}
