package net.caffeinemc.mods.sodium.client.compatibility.workarounds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This build only targets aarch64 Android, where there are no desktop graphics vendors to probe for. The set of active
 * workarounds is therefore fixed instead of being discovered from the installed graphics adapters.
 */
public class Workarounds {
    private static final Logger LOGGER = LoggerFactory.getLogger("Sodium-Workarounds");

    private static final AtomicReference<Set<Reference>> ACTIVE_WORKAROUNDS = new AtomicReference<>(EnumSet.noneOf(Reference.class));

    public static void init() {
        var workarounds = EnumSet.of(Reference.NO_ERROR_CONTEXT_UNSUPPORTED);

        LOGGER.info("Active workarounds: {}", workarounds);

        ACTIVE_WORKAROUNDS.set(Collections.unmodifiableSet(workarounds));
    }

    public static boolean isWorkaroundEnabled(Reference id) {
        return ACTIVE_WORKAROUNDS.get()
                .contains(id);
    }

    public enum Reference {
        /**
         * Requesting a No Error Context is not supported on Android launcher GL layers, so the option is kept
         * disabled.
         */
        NO_ERROR_CONTEXT_UNSUPPORTED
    }
}
