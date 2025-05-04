package com.cl1ppz12.strike.item.custom;


import com.cl1ppz12.strike.item.ModToolMaterials;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class Scythe extends Item {

    public Scythe(Settings settings) {
        super(settings.sword(ModToolMaterials.SCYTHE, 3.5F, -2.0F).fireproof().enchantable(32));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!user.getItemCooldownManager().isCoolingDown(stack.getItem().getDefaultStack())) {
            if (!world.isClient) {
                spawnXParticlesAndDamage((ServerWorld) world, user);


            }
            user.getItemCooldownManager().set(stack.getItem().getDefaultStack(), 100);
        }
        return ActionResult.SUCCESS;
    }


    private void spawnXParticlesAndDamage(ServerWorld world, PlayerEntity player) {
        Vec3d look = player.getRotationVec(1.0F).normalize();
        Vec3d center = player.getPos().add(0, 1.5, 0).add(look.multiply(2));

        double[][] xArm1 = {
                {-1.0,  1.0}, {-0.8,  0.8}, {-0.6,  0.6}, {-0.4,  0.4}, {-0.2,  0.2},
                { 0.0,  0.0},
                { 0.2, -0.2}, { 0.4, -0.4}, { 0.6, -0.6}, { 0.8, -0.8}, { 1.0, -1.0}
        };

        double[][] xArm2 = {
                {-1.0, -1.0}, {-0.8, -0.8}, {-0.6, -0.6}, {-0.4, -0.4}, {-0.2, -0.2},
                { 0.0,  0.0},
                { 0.2,  0.2}, { 0.4,  0.4}, { 0.6,  0.6}, { 0.8,  0.8}, { 1.0,  1.0}
        };

        spawnLaserAndHitEntities(world, center, xArm1, player);
        spawnLaserAndHitEntities(world, center, xArm2, player);
    }

    private void spawnLaserAndHitEntities(ServerWorld world, Vec3d start, double[][] shapeOffsets, PlayerEntity player) {
        float yaw = (float) Math.toRadians(-player.getYaw());
        float pitch = (float) Math.toRadians(-player.getPitch());

        Vec3d direction = player.getRotationVec(1.0F).normalize();
        int steps = 20;

        for (int i = 0; i < steps; i++) {
            Vec3d basePos = start.add(direction.multiply(i));

            for (double[] offset : shapeOffsets) {
                Vec3d local = new Vec3d(offset[0], offset[1], 0);
                local = local.rotateX(pitch).rotateY(yaw);

                Vec3d particlePos = basePos.add(local);


                world.spawnParticles(
                        ParticleTypes.END_ROD,
                        particlePos.x, particlePos.y, particlePos.z,
                        10, 0, 0, 0, 0
                );


                Box box = new Box(particlePos.subtract(0.25, 0.25, 0.25), particlePos.add(0.25, 0.25, 0.25));
                List<LivingEntity> targets = world.getEntitiesByClass(LivingEntity.class, box, e -> e != player && e.isAlive());

                for (LivingEntity target : targets) {
                    if (!target.equals(player) && target.isAlive() && target instanceof LivingEntity livingTarget) {
                        ServerWorld serverWorld = (ServerWorld) player.getWorld();

                        livingTarget.damage(serverWorld, serverWorld.getDamageSources().magic(), 6.0F);
                        livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 60, 0));
                    }
                }
            }
        }
    }


}
