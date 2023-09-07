package CenturionAndMystic.icons;

import CenturionAndMystic.MainModfile;
import CenturionAndMystic.util.KeywordManager;
import CenturionAndMystic.util.TexLoader;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;

import java.util.Collections;
import java.util.List;

public class IconContainer {
    public static class CenturionEnergyIcon extends AbstractCustomIcon {
        static AbstractCustomIcon singleton;

        public CenturionEnergyIcon() {
            super(MainModfile.makeID("Centurion"), TexLoader.getTexture(MainModfile.TEXT_ENERGY_CENTURION));
        }

        public static AbstractCustomIcon get() {
            if (singleton == null) {
                singleton = new CenturionEnergyIcon();
            }
            return singleton;
        }

        @Override
        public List<TooltipInfo> getCustomTooltips() {
            return Collections.singletonList(new TooltipInfo(BaseMod.getKeywordTitle(KeywordManager.CENTURION), BaseMod.getKeywordDescription(KeywordManager.CENTURION)));
        }
    }

    public static class MysticEnergyIcon extends AbstractCustomIcon {
        static AbstractCustomIcon singleton;

        public MysticEnergyIcon() {
            super(MainModfile.makeID("Mystic"), TexLoader.getTexture(MainModfile.TEXT_ENERGY_MYSTIC));
        }

        public static AbstractCustomIcon get() {
            if (singleton == null) {
                singleton = new MysticEnergyIcon();
            }
            return singleton;
        }

        @Override
        public List<TooltipInfo> getCustomTooltips() {
            return Collections.singletonList(new TooltipInfo(BaseMod.getKeywordTitle(KeywordManager.MYSTIC), BaseMod.getKeywordDescription(KeywordManager.MYSTIC)));
        }
    }
}
