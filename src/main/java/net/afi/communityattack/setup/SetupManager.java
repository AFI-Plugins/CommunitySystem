package net.afi.communityattack.setup;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.UUID;

public class SetupManager {

    private HashMap<UUID, String> setupData;

    public SetupManager() {
        setupData = new HashMap<UUID, String>();
    }

    public void putSetupData(UUID player, String data) {
        setupData.put(player, data);
    }

    @Nullable
    public String getSetupData(UUID player) {
        if(!setupData.containsKey(player))return null;
        return setupData.get(player);
    }

    public void removeSetupData(UUID player) {
        if(!setupData.containsKey(player))setupData.remove(player);
    }
}
