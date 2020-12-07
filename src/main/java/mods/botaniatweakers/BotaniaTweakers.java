package mods.botaniatweakers;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BotaniaTweakers implements ModInitializer {
    public final static String MODID = "botaniatweakers";
    public final static Logger L = LogManager.getLogger(MODID);

    @Override
    public void onInitialize() {

    }

    public static Identifier getId(String id) {
        return new Identifier(MODID, id);
    }
}
