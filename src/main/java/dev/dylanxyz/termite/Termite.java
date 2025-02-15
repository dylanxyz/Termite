package dev.dylanxyz.termite;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Termite.MODID)
public class Termite {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "termite";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Termite() {}

    static public Logger getLogger() {
        return LOGGER;
    }
}
