package com.kneeremover.extendedconsumables.entity;

import com.kneeremover.extendedconsumables.ExtendedConsumables;
import com.kneeremover.extendedconsumables.entity.custom.Potionoliger;
import com.kneeremover.extendedconsumables.entity.custom.projectile.SplashPotion;
import com.kneeremover.extendedconsumables.entity.custom.projectile.TippedBolt;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ExtendedConsumables.MOD_ID);

	public static final RegistryObject<EntityType<SplashPotion>> SPLASH_POTION = ENTITIES.register("splash_potion", () ->
			EntityType.Builder.<SplashPotion>of(SplashPotion::new, MobCategory.MISC)
					.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
					.build(new ResourceLocation(ExtendedConsumables.MOD_ID, "splash_potion").toString()));

	public static final RegistryObject<EntityType<TippedBolt>> TIPPED_BOLT = ENTITIES.register("tipped_bolt", () ->
			EntityType.Builder.<TippedBolt>of(TippedBolt::new, MobCategory.MISC)
					.sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
					.build(new ResourceLocation(ExtendedConsumables.MOD_ID, "tipped_bolt").toString()));

//	public static final RegistryObject<EntityType<Potionoliger>> POTIONOLIGER = ENTITIES.register("potionoliger", () ->
//			EntityType.Builder.<Potionoliger>of(Potionoliger::new, MobCategory.MISC).sized(0.6F, 1.95F).clientTrackingRange(8)
//					.build(new ResourceLocation(ExtendedConsumables.MOD_ID, "potionoliger").toString()));

	public static void register(IEventBus eventBus) {
		ENTITIES.register(eventBus);
	}
}
