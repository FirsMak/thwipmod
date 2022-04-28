package com.yyon.grapplinghook.items;

import com.yyon.grapplinghook.vec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

import static com.yyon.grapplinghook.entities.grappleArrow.worldIn;

public class SpawnParticles {
    public SpawnParticles(World world, double x, double y, double z, double x2, double y2, double z2, vec speed, boolean more, int range) {
        vec playervec = new vec(x,y,z);
        vec vec = new vec(x2, y2, z2);
        if (range==0){
            range = 1;
        }

        vec = playervec.sub(vec);
        vec = vec.normalize();
        vec.inverse();
        vec = vec.add(speed.mult(range));
        vec = vec.mult(0.5);
        if (vec.length() == 0){
            vec.normalize_ip();
        }
        int i = 0;
        while (i< range){
            i += 1;
            vec vec2rand = vec.mult(Math.random());
            Particle theParticle = new ParticleWeb(worldIn, x, y, z, vec.x,vec.y,vec.z);
            Particle theParticle2 = new ParticleWeb(worldIn, x, y, z, vec2rand.x,vec2rand.y,vec2rand.z);
            Minecraft.getMinecraft().effectRenderer.addEffect(theParticle);
            if (more){
                Minecraft.getMinecraft().effectRenderer.addEffect(theParticle2);
            }
        }
    }
}
