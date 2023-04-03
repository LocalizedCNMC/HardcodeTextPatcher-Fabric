package org.localmc.tools.hardcodepatcher;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import org.localmc.tools.hardcodepatcher.config.HardcodePatcherPatch;

public interface HPConfigRegistrationCallback {
    Event<HPConfigRegistrationCallback> EVENT = EventFactory.createArrayBacked(HPConfigRegistrationCallback.class, (callbacks) -> (vpp, filetype) -> {
        for (HPConfigRegistrationCallback callback : callbacks) {
            callback.loadConfig(vpp, filetype);
        }
    });
    void loadConfig(HardcodePatcherPatch vpp, String filetype);
}
