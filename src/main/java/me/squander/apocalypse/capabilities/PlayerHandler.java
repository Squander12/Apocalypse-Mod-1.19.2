package me.squander.apocalypse.capabilities;

import me.squander.apocalypse.helper.Helper;
import me.squander.apocalypse.skill.Skill;
import me.squander.apocalypse.skill.SkillsInit;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AutoRegisterCapability
public class PlayerHandler implements ICapabilitySerializable<CompoundTag> {
    public static final Component SKILL_MENU_NAME = Helper.makeGuiTranslationName("skill_menu");
    private final LazyOptional<PlayerHandler> lazyOptional = LazyOptional.of(() -> this);
    private List<Skill> skills = new ArrayList<>();
    private int maxExp;
    private int currentExp;
    private int level;
    private int lastLevel;
    private int skillPoints;

    public PlayerHandler() {
        this.skills.addAll(SkillsInit.SKILLS_REGISTRY.get().getValues());
        this.maxExp = this.checkLevelToExp();
        this.level = 1;
        this.lastLevel = level;
    }

    public void tick(){
        if(this.currentExp >= this.maxExp){
            this.currentExp = 0;
            this.level += 1;
            this.maxExp = this.checkLevelToExp();
        }

        if(this.lastLevel != this.level){
            this.skillPoints += 1;
        }

        this.lastLevel = this.level;
    }

    public List<Skill> getSkills() {
        return this.skills;
    }

    public Optional<Skill> getSkill(Skill skill){
        return this.getSkill(SkillsInit.SKILLS_REGISTRY.get().getKey(skill));
    }

    public Optional<Skill> getSkill(ResourceLocation key){
        for(Skill s : this.getSkills()){
            if(SkillsInit.SKILLS_REGISTRY.get().getKey(s).equals(key)){
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    public void addExp(int amount){
        this.currentExp += amount;
    }

    public int getMaxExp() {
        return this.maxExp;
    }

    public int getCurrentExp() {
        return this.currentExp;
    }

    public int getLevel() {
        return this.level;
    }

    public void setMaxExp(int maxExp) {
        this.maxExp = maxExp;
    }

    public void setCurrentExp(int currentExp) {
        this.currentExp = currentExp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLastLevel() {
        return this.lastLevel;
    }

    public void setLastLevel(int lastLevel) {
        this.lastLevel = lastLevel;
    }

    public int getSkillPoints() {
        return this.skillPoints;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    public int checkLevelToExp(){
        if(this.level >= 25){
            return 3000;
        }else if(this.level >= 20){
            return 2500;
        }else if(this.level >= 15){
            return 2000;
        }else if(this.level >= 10){
            return 1500;
        }else{
            return 1000;
        }
    }

    public void reset(Player player){
        this.level = 1;
        this.currentExp = 0;
        this.maxExp = this.checkLevelToExp();
        this.skills.forEach(x -> x.reset(player));
        this.lastLevel = 1;
        this.skillPoints = 0;
    }

    public void copy(PlayerHandler handler){
        this.level = handler.level;
        this.currentExp = handler.currentExp;
        this.maxExp = handler.maxExp;
        this.skills = handler.skills;
        this.lastLevel = handler.lastLevel;
        this.skillPoints = handler.skillPoints;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CapabilityInit.PLAYER_DATA.orEmpty(cap, this.lazyOptional);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        ListTag listTag = new ListTag();
        for(Skill s : this.getSkills()){
            CompoundTag tag1 = s.serializeNBT();
            tag1.putString("Name", SkillsInit.SKILLS_REGISTRY.get().getKey(s).toString());
            listTag.add(tag1);
        }

        tag.put("Skills", listTag);
        tag.putInt("MaxExp", this.maxExp);
        tag.putInt("CurrentExp", this.currentExp);
        tag.putInt("Level", this.level);
        tag.putInt("LastLevel", this.lastLevel);
        tag.putInt("SkillPoints", this.skillPoints);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        ListTag listTag = tag.getList("Skills", Tag.TAG_COMPOUND);

        for(Tag tag1 : listTag){
            CompoundTag compoundTag = (CompoundTag) tag1;
            ResourceLocation resourceLocation = new ResourceLocation(compoundTag.getString("Name"));
            this.getSkill(resourceLocation).ifPresent(s -> s.deserializeNBT(compoundTag));
        }

        this.level = tag.getInt("Level");
        this.currentExp = tag.getInt("CurrentExp");
        this.maxExp = tag.getInt("MaxExp");
        this.lastLevel = tag.getInt("LastLevel");
        this.skillPoints = tag.getInt("SkillPoints");
    }
}
