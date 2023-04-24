package org.localmc.tools.hardcodepatcher;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface HardcodePatcherConfigRegistrationCallback {
    Event<HardcodePatcherConfigRegistrationCallback> EVENT = EventFactory.createArrayBacked(HardcodePatcherConfigRegistrationCallback.class, (callbacks) -> () -> {
        for (HardcodePatcherConfigRegistrationCallback callback : callbacks) {
            callback.loadConfig();
        }
    });
    void loadConfig();
}
