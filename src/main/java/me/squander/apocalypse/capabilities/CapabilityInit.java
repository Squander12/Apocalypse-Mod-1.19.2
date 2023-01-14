package me.squander.apocalypse.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class CapabilityInit {
    public static final Capability<WeaponHandler> WEAPON_HANDLER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    public static final Capability<PlayerHandler> PLAYER_DATA = CapabilityManager.get(new CapabilityToken<>(){});
}
