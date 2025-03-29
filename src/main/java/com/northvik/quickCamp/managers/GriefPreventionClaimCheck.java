package com.northvik.quickCamp.managers;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;

public class GriefPreventionClaimCheck {

    public boolean isClaimed(Location location) {
        // Access the DataStore instance from GriefPrevention
        DataStore dataStore = GriefPrevention.instance.dataStore;

        // Check if there is a claim at the specified location
        Claim claim = dataStore.getClaimAt(location, true, null);

        // If claim is not null, the location is claimed
        return claim != null;
    }
}
