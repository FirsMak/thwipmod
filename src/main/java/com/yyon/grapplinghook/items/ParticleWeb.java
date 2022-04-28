package com.yyon.grapplinghook.items;

import net.minecraft.client.particle.ParticlePortal;
import net.minecraft.client.particle.ParticleSnowShovel;
import net.minecraft.world.World;

public class ParticleWeb extends ParticleSnowShovel {
    public ParticleWeb(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
    }
}
