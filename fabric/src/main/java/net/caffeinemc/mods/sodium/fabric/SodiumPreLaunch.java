package net.caffeinemc.mods.sodium.fabric;

import net.caffeinemc.mods.sodium.client.compatibility.environment.AndroidPlatform;
import net.caffeinemc.mods.sodium.client.compatibility.workarounds.Workarounds;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class SodiumPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        AndroidPlatform.requireSupported();
        Workarounds.init();
    }
}
