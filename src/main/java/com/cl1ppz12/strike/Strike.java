package com.cl1ppz12.strike;


import com.cl1ppz12.strike.item.ModItems;
import com.cl1ppz12.strike.block.ModBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Strike implements ModInitializer {

	public static final String MOD_ID = "strike";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final SimpleParticleType BLOOD_BUBBLE_PARTICLE = FabricParticleTypes.simple();



	@Override
	public void onInitialize() {

		ModItems.initialize();
		ModBlocks.initialize();

		Registry.register(Registries.PARTICLE_TYPE, Identifier.of("strike", "blood_bubble_particle"), BLOOD_BUBBLE_PARTICLE);


		Strike.LOGGER.info(Strike.MOD_ID + " say's Hello Fabric World");
	}
}