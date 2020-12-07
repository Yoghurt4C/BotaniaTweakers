package mods.botaniatweakers.libdp;

import io.github.cottonmc.libdp.api.DriverInitializer;
import io.github.cottonmc.libdp.api.driver.DriverManager;

public class LibDPPlugin implements DriverInitializer {
    @Override
    public void init(DriverManager manager) {
        manager.addDriver("botaniatweaker.BotaniaDriver", BotaniaDriver.INSTANCE);
    }
}
