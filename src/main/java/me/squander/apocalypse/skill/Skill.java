package me.squander.apocalypse.skill;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;
import java.util.Objects;

public class Skill implements INBTSerializable<CompoundTag> {
    private int level;

    public Skill() {
        this.level = 0;
    }

    public ResourceLocation getResourceLocation(){
        return Objects.requireNonNull(SkillsInit.SKILLS_REGISTRY.get().getKey(this));
    }

    public MutableComponent getTranslatableName(){
        return Component.translatable("skill.apocalypse." + this.getResourceLocation().getPath());
    }

    public void reset(Player player){
        this.level = 0;
        this.remove(player);
    }

    public void addLevel(int amount){
        int i = Mth.clamp(amount, 0, this.getMaxLevel());
        if(this.level < this.getMaxLevel()){
            this.level += i;
        }
    }

    public void apply(Player player){
    }

    public void remove(Player player){
    }

    public void getToolTip(List<Component> tip){
    }

    public void copy(Skill skill){
        this.level = skill.level;
    }

    public int getLevel(){
        return this.level;
    }

    public int getMaxLevel(){
        return 5;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean canBeUsed(){
        return this.level > 0;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Level", this.level);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.level = tag.getInt("Level");
    }
}
