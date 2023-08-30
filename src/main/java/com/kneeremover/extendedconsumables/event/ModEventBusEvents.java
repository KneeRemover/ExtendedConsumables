package com.kneeremover.extendedconsumables.event;

import com.kneeremover.extendedconsumables.capabilities.PlayerSaturationOverload;
import com.kneeremover.extendedconsumables.capabilities.PlayerTruces;
import com.kneeremover.extendedconsumables.capabilities.WorldTargetTime;
import com.kneeremover.extendedconsumables.particle.ModParticles;
import com.kneeremover.extendedconsumables.particle.custom.RadiantFriendlyDamageLinkParticles;
import com.kneeremover.extendedconsumables.particle.custom.RadiantRegenParticles;
import com.kneeremover.extendedconsumables.particle.custom.RadiantSlownessParticles;
import com.kneeremover.extendedconsumables.particle.custom.RadiantVirusParticles;
import com.kneeremover.extendedconsumables.recipe.ConsumableTableRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEventBusEvents {
	@SubscribeEvent
	public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
		Registry.register(Registry.RECIPE_TYPE, ConsumableTableRecipe.Type.ID, ConsumableTableRecipe.Type.INSTANCE);
	}

	@SubscribeEvent
	public static void registerParticleFactories(final ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particleEngine.register(ModParticles.RADIANT_REGEN_PARTICLES.get(), RadiantRegenParticles.Provider::new);
		Minecraft.getInstance().particleEngine.register(ModParticles.RADIANT_SLOWNESS_PARTICLES.get(), RadiantSlownessParticles.Provider::new);
		Minecraft.getInstance().particleEngine.register(ModParticles.RADIANT_FRIENDLY_DAMAGE_LINK_PARTICLES.get(), RadiantFriendlyDamageLinkParticles.Provider::new);
		Minecraft.getInstance().particleEngine.register(ModParticles.RADIANT_VIRUS_PARTICLES.get(), RadiantVirusParticles.Provider::new);
	}

	@SubscribeEvent
	public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
		event.register(PlayerSaturationOverload.class);
		event.register(PlayerTruces.class);
		event.register(WorldTargetTime.class);
	}
}
