package net.caffeinemc.mods.sodium.client.compatibility.environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This build of Sodium only supports aarch64 devices running Minecraft through an Android launcher. The detection is
 * intentionally cheap (a few system property and environment lookups) and has no dependency on Minecraft classes, so
 * that it can run during pre-launch.
 */
public final class AndroidPlatform {
    /**
     * Environment variables which Android launchers set for the game process. Add the variable of your launcher here
     * if it is not detected through its file paths.
     */
    private static final String[] LAUNCHER_ENV_VARS = {
            "POJAV_RENDERER"
    };

    /**
     * Android application data directories. Launchers keep their runtime and library files below one of these.
     */
    private static final String[] ANDROID_PATH_PREFIXES = {
            "/data/user/",
            "/data/data/"
    };

    private static final String[] PATH_PROPERTIES = {
            "java.home",
            "user.home",
            "user.dir"
    };

    private static final String ARCH = System.getProperty("os.arch", "unknown");
    private static final List<String> ANDROID_SIGNALS = findAndroidSignals();
    private static final boolean AARCH64 = isAarch64(ARCH);

    private AndroidPlatform() {
    }

    public static boolean isSupported() {
        return AARCH64 && !ANDROID_SIGNALS.isEmpty();
    }

    /**
     * @throws RuntimeException if the game is not running on aarch64 Android
     */
    public static void requireSupported() {
        if (isSupported()) {
            return;
        }

        throw new RuntimeException("This build of Sodium only supports aarch64 Android (launchers such as PojavLauncher " +
                "or FCL). Detected os.arch='" + ARCH + "', os.name='" + System.getProperty("os.name") + "', " +
                "android signals=" + ANDROID_SIGNALS + ". Remove Sodium from your mods folder or use the official build.");
    }

    private static boolean isAarch64(String arch) {
        var normalized = arch.toLowerCase(Locale.ROOT);

        return normalized.equals("aarch64") || normalized.equals("arm64");
    }

    private static List<String> findAndroidSignals() {
        var signals = new ArrayList<String>();

        for (var name : LAUNCHER_ENV_VARS) {
            if (System.getenv(name) != null) {
                signals.add("env:" + name);
            }
        }

        for (var property : PATH_PROPERTIES) {
            var value = System.getProperty(property);

            if (value != null && hasAndroidPrefix(value)) {
                signals.add(property);
            }
        }

        var libraryPath = System.getProperty("java.library.path");

        if (libraryPath != null) {
            for (var path : libraryPath.split(File.pathSeparator)) {
                if (hasAndroidPrefix(path)) {
                    signals.add("java.library.path");
                    break;
                }
            }
        }

        return List.copyOf(signals);
    }

    private static boolean hasAndroidPrefix(String path) {
        for (var prefix : ANDROID_PATH_PREFIXES) {
            if (path.startsWith(prefix)) {
                return true;
            }
        }

        return false;
    }
}
