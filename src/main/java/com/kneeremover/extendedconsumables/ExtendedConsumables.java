package com.kneeremover.extendedconsumables;

import com.kneeremover.extendedconsumables.block.ModBlocks;
import com.kneeremover.extendedconsumables.block.entity.ModBlockEntities;
import com.kneeremover.extendedconsumables.config.ExtendedConsumablesCommonConfigs;
import com.kneeremover.extendedconsumables.effect.ModEffects;
import com.kneeremover.extendedconsumables.entity.ModEntities;
import com.kneeremover.extendedconsumables.entity.renderer.TippedBoltRenderer;
import com.kneeremover.extendedconsumables.event.ForgeEventBusEvents;
import com.kneeremover.extendedconsumables.event.ModEventBusEvents;
import com.kneeremover.extendedconsumables.item.ModItems;
import com.kneeremover.extendedconsumables.networking.ModMessages;
import com.kneeremover.extendedconsumables.particle.ModParticles;
import com.kneeremover.extendedconsumables.recipe.ModRecipes;
import com.kneeremover.extendedconsumables.screen.ConsumableTableScreen;
import com.kneeremover.extendedconsumables.screen.ModMenuTypes;
import com.kneeremover.extendedconsumables.screen.StackCombinerScreen;
import com.kneeremover.extendedconsumables.util.ModItemProperties;
import com.kneeremover.extendedconsumables.util.TippedUtils;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ExtendedConsumables.MOD_ID)
public class ExtendedConsumables {
    public static final String MOD_ID = "extendedconsumables";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ExtendedConsumables() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.register(ModEventBusEvents.class);

        ModEffects.register(eventBus);
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModEntities.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModMenuTypes.register(eventBus);
        ModParticles.register(eventBus);

        ModRecipes.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::onWorldLoad);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(ForgeEventBusEvents.class);
        GeckoLib.initialize();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ExtendedConsumablesCommonConfigs.SPEC, "extendedconsumables-common.toml");
    }

    @SuppressWarnings("unused")
    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CONSUMABLE_TABLE.get(), RenderType.translucent());
        ModItemProperties.addCustomItemProperties();

        EntityRenderers.register(ModEntities.SPLASH_POTION.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.TIPPED_BOLT.get(), TippedBoltRenderer::new);

        // Register GUIs
        MenuScreens.register(ModMenuTypes.CONSUMABLE_TABLE_MENU.get(), ConsumableTableScreen::new);
        MenuScreens.register(ModMenuTypes.STACK_COMBINER_MENU.get(), StackCombinerScreen::new);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModMessages.register();
        });
    }

    @SuppressWarnings("unused")
    private void onWorldLoad(final FMLLoadCompleteEvent event) {
        TippedUtils.defineEffects();
    }
}
