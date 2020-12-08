package mods.botaniatweakers;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BotaniaTweakers implements ModInitializer {
    public final static String MODID = "botaniatweakers";
    public final static Logger L = LogManager.getLogger(MODID);

    @Override
    public void onInitialize() {
        FabricLoader fml = FabricLoader.getInstance();
        if (fml.isModLoaded("libdp")) L.info("[BotaniaTweakers] LibDP is present!");
        if (fml.isModLoaded("kubejs")) L.info("[BotaniaTweakers] KubeJS is present!");
    }

    public static Identifier getId(String id) {
        return new Identifier(MODID, id);
    }
}
