package me.squander.apocalypse.skill;

import me.squander.apocalypse.ApocalypseMod;
import me.squander.apocalypse.helper.Helper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SkillsInit {
    public static final DeferredRegister<Skill> SKILLS = DeferredRegister.create(Helper.getRc("skill"), ApocalypseMod.MODID);
    public static final Supplier<IForgeRegistry<Skill>> SKILLS_REGISTRY = SKILLS.makeRegistry(RegistryBuilder::new);

    public static RegistryObject<Skill> HEALTH_SKILL = SKILLS.register("health_skill", HealthSkill::new);
    public static RegistryObject<Skill> STRONG_SKILL = SKILLS.register("strong_skill", StrongSkill::new);
    public static RegistryObject<Skill> OXYGEN_SKILL = SKILLS.register("oxygen_skill", Skill::new);
    public static RegistryObject<Skill> EXPERIENCE_SKILL = SKILLS.register("experience_skill", Skill::new);
}
