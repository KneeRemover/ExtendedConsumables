package com.kneeremover.extendedconsumables.entity;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.entity.custom.SplashPotion;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ExtendedConsumables.MOD_ID);

	public static final RegistryObject<EntityType<SplashPotion>> SPLASH_POTION = ENTITIES.register("splash_potion_do_not_summon", () -> EntityType.Builder.<SplashPotion>of(SplashPotion::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(new ResourceLocation(ExtendedConsumables.MOD_ID, "splash_potion").toString()));

	public static void register(IEventBus eventBus) {
		ENTITIES.register(eventBus);
	}
}
