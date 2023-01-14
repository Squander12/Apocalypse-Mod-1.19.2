package me.squander.apocalypse.skill;

import me.squander.apocalypse.ApocalypseMod;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class HealthSkill extends Skill {
    public static final UUID HEALTH_UUID = UUID.fromString("ef063cbb-47c1-4920-b794-a1206476156f");

    @Override
    public void apply(Player player) {
        double i = this.getLevel() * 4;
        AttributeModifier modifier = new AttributeModifier(HEALTH_UUID, "Health Skill Modifier", i, AttributeModifier.Operation.ADDITION);
        AttributeInstance instance = player.getAttribute(Attributes.MAX_HEALTH);

        if(instance.hasModifier(modifier)){
            this.remove(player);
        }

        instance.addPermanentModifier(modifier);
        ApocalypseMod.LOGGER.info("HEALTH: " + instance.getValue());
    }

    @Override
    public void remove(Player player) {
        AttributeInstance instance = player.getAttribute(Attributes.MAX_HEALTH);
        instance.removePermanentModifier(HEALTH_UUID);
    }
}
