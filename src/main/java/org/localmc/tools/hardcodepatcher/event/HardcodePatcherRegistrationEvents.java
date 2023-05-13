package org.localmc.tools.hardcodepatcher.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface HardcodePatcherRegistrationEvents {
    Event<HardcodePatcherRegistrationEvents> EVENT = EventFactory.createArrayBacked(HardcodePatcherRegistrationEvents.class, (events) -> () -> {
        for (HardcodePatcherRegistrationEvents event : events) {
            event.loadConfig();
        }
    });

    void loadConfig();
}
