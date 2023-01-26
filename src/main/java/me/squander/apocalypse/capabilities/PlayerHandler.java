package me.squander.apocalypse.capabilities;

import me.squander.apocalypse.helper.Helper;
import me.squander.apocalypse.skill.Skill;
import me.squander.apocalypse.skill.SkillsInit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.FireworkRocketItem;
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
    private int currentExp;
    private int level;
    private int lastLevel;
    private int skillPoints;

    public PlayerHandler() {
        this.skills.addAll(SkillsInit.SKILLS_REGISTRY.get().getValues());
        this.level = 1;
    }

    public void tick(Player player){
        if(this.lastLevel != this.level && !(this.level < this.lastLevel)){
            this.applause(player);
        }

        this.lastLevel = this.level;
    }

    public void applause(Player player){
        Component levelTxt = Component.literal("" + this.level).withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD);
        Component playerTxt = Component.literal("" + player.getDisplayName()).withStyle(ChatFormatting.BOLD, ChatFormatting.RED);

        for(ServerPlayer p : player.getServer().getPlayerList().getPlayers()){
            if(p == player) continue;
            p.sendSystemMessage(Component.translatable("player_handler.applause", playerTxt, levelTxt).withStyle(ChatFormatting.DARK_GREEN));
        }

        player.displayClientMessage(Component.translatable("player_handler.applause.single", levelTxt).withStyle(ChatFormatting.DARK_GREEN), false);
        Helper.createFirework(player.getLevel(), player.getOnPos().above(), 1, FireworkRocketItem.Shape.LARGE_BALL, DyeColor.PURPLE);
    }

    public List<Skill> getSkills() {
        return this.skills;
    }

    public void setSkills(List<Skill> skills){
        this.skills = skills;
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
        this.addExp(amount, false);
    }

    public void addExp(int amount, boolean ignoreExpSkill){
        Skill expSkill = this.getSkill(SkillsInit.EXPERIENCE_SKILL.get()).get();

        if(expSkill.canBeUsed() && !ignoreExpSkill){
            double i = expSkill.getLevel() * 10;
            double percent = amount + ((i / 100) * amount);
            this.currentExp += percent;
        }else{
            this.currentExp += amount;
        }

        while(this.currentExp >= this.getExpRequiredToNextLevel()) {
            this.currentExp = this.currentExp - this.getExpRequiredToNextLevel();
            this.addLevel(1);
            this.addSkillPoints(1);
        }
    }

    public void addLevel(int amount){
        this.setLevel(this.getLevel() + amount);
    }

    public void addSkillPoints(int amount){
        this.setSkillPoints(this.getSkillPoints() + amount);
    }

    public int getCurrentExp() {
        return this.currentExp;
    }

    public int getLevel() {
        return this.level;
    }

    public void setCurrentExp(int currentExp) {
        this.currentExp = currentExp;
    }

    public void setLevel(int level) {
        this.level = Mth.clamp(level, 1, Integer.MAX_VALUE);

        if(this.level < 1){
            this.level = 1;
            this.currentExp = 0;
            this.skillPoints = 0;
        }
    }

    public int getLastLevel() {
        return this.lastLevel;
    }

    public int getSkillPoints() {
        return this.skillPoints;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = Mth.clamp(skillPoints, 0, Integer.MAX_VALUE);
    }

    public int getExpRequiredToNextLevel(){
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
        this.skills.forEach(skill -> skill.reset(player));
        this.skillPoints = 0;
    }

    public void copy(PlayerHandler handler){
        this.level = handler.level;
        this.currentExp = handler.currentExp;
        this.skills = handler.skills;
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
        this.lastLevel = tag.getInt("LastLevel");
        this.currentExp = tag.getInt("CurrentExp");
        this.skillPoints = tag.getInt("SkillPoints");
    }
}
