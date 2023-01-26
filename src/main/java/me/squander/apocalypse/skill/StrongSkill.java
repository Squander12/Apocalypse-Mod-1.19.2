package me.squander.apocalypse.skill;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.UUID;

public class StrongSkill extends Skill {
    public static final UUID STRONG_UUID = UUID.fromString("2dbdfec1-970e-4cc9-8b08-e1a1ac292f8b");

    @Override
    public void apply(Player player) {
        double i = this.getLevel() * 1.5;
        AttributeModifier modifier = new AttributeModifier(STRONG_UUID, "Strong Skill Modifier", i, AttributeModifier.Operation.ADDITION);
        AttributeInstance instance = player.getAttribute(Attributes.ATTACK_DAMAGE);

        if(instance.hasModifier(modifier)){
            this.remove(player);
        }

        instance.addPermanentModifier(modifier);
    }

    @Override
    public void getToolTip(List<Component> tip) {
        tip.add(Component.literal("Each next level of this skill,"));
        tip.add(Component.literal("increases your strength level by 50%"));
    }

    @Override
    public void remove(Player player) {
        AttributeInstance instance = player.getAttribute(Attributes.ATTACK_DAMAGE);
        instance.removePermanentModifier(STRONG_UUID);
    }
}
