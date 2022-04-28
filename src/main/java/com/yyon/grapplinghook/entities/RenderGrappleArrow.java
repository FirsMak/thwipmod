package com.yyon.grapplinghook.entities;

import com.yyon.grapplinghook.items.ParticleWeb;
import com.yyon.grapplinghook.items.SpawnParticles;
import com.yyon.grapplinghook.vec;
import com.yyon.grapplinghook.controllers.SegmentHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.yyon.grapplinghook.entities.grappleArrow.worldIn;


/*
 * This file is part of GrappleMod.

    GrappleMod is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    GrappleMod is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with GrappleMod.  If not, see <http://www.gnu.org/licenses/>.
 */

@SideOnly(Side.CLIENT)
public class RenderGrappleArrow<T extends Entity> extends Render<T>
{
    private static final ResourceLocation LEASH_KNOT_TEXTURES = new ResourceLocation("thwip", "textures/entity/rope.png");
    int indexk = 0;
    boolean first = true;
    public static boolean startweb = false;
    public RenderGrappleArrow(RenderManager renderManagerIn, RenderItem itemRendererIn)
    {
        super(renderManagerIn);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity>) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doe
     */
    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        grappleArrow arrow = (grappleArrow) entity;
        if (arrow == null || arrow.isDead) {
        	return;
        }
        
        SegmentHandler segmenthandler = arrow.segmenthandler;
        
        EntityLivingBase e = (EntityLivingBase) arrow.shootingEntity;
        
        if (e == null || e.isDead) {
        	return;
        }
        int primaryhand = e.getPrimaryHand() == EnumHandSide.RIGHT ? 1 : -1;
        if (!arrow.righthand){
            primaryhand = e.getPrimaryHand() == EnumHandSide.LEFT ? 1 : -1;
        }

    	Vec3d offset = new Vec3d(0,0,0);
    	if (!arrow.attached) {
	    	if (arrow.righthand) {
	            offset = new Vec3d((double)primaryhand * -0.36D, -0.175D, 0.45D); // hand relative to person
	    	} else {
	            offset = new Vec3d((double)primaryhand * 0.36D, -0.175D, 0.45D); // hand relative to person
	    	}
	        offset = offset.rotatePitch(-(e.prevRotationPitch + (e.rotationPitch - e.prevRotationPitch) * partialTicks) * 0.017453292F);
	        offset = offset.rotateYaw(-(e.prevRotationYaw + (e.rotationYaw - e.prevRotationYaw) * partialTicks) * 0.017453292F);
	        
	        double dist = e.getDistance(arrow);
	        double mult = 1 - (dist / 10.0);
	        if (mult <= 0) {
	        	offset = new Vec3d(0,0,0);
	        } else {
	        	offset = new Vec3d(offset.x * mult, offset.y * mult, offset.z * mult);

		        x += offset.x;
		        y += offset.y;
		        z += offset.z;
	        }
    	}
    	
    	
        /*
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        this.bindEntityTexture(entity);
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        vertexbuffer.pos(-0.5D, -0.5D, 0.0D).tex(0.0625D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(0.5D, -0.5D, 0.0D).tex(0.125D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(0.5D, 0.5D, 0.0D).tex(0.125D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(-0.5D, 0.5D, 0.0D).tex(0.0625D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        */

        
        int k = e.getPrimaryHand() == EnumHandSide.RIGHT ? 1 : -1;
        if (!arrow.righthand){
            k = e.getPrimaryHand() == EnumHandSide.LEFT ? 1 : -1;
        }
        float f7 = e.getSwingProgress(partialTicks);
        float f8 = MathHelper.sin(MathHelper.sqrt(f7) * (float)Math.PI);
        float f9 = (e.prevRenderYawOffset + (e.renderYawOffset - e.prevRenderYawOffset) * partialTicks) * 0.017453292F;
        double d0 = (double)MathHelper.sin(f9);
        double d1 = (double)MathHelper.cos(f9);
        double d2 = (double)(k) * 0.35D;
        double d4;
        double d5;
        double d6;
        double d7;

        if ((this.renderManager.options == null || this.renderManager.options.thirdPersonView <= 0) && e == Minecraft.getMinecraft().player)
        {
        	Vec3d vec3d;
        	if (arrow.righthand) {
                vec3d = new Vec3d((double)k * -0.36D, -0.175D, 0.45D); // hand relative to person
        	}
            else {
                vec3d = new Vec3d((double)k * -0.36D, -0.175D, 0.45D); // hand relative to person
        	}
            vec3d = vec3d.rotatePitch(-(e.prevRotationPitch + (e.rotationPitch - e.prevRotationPitch) * partialTicks) * 0.017453292F);
            vec3d = vec3d.rotateYaw(-(e.prevRotationYaw + (e.rotationYaw - e.prevRotationYaw) * partialTicks) * 0.017453292F);
            vec3d = vec3d.rotateYaw(f8 * 0.5F);
            vec3d = vec3d.rotatePitch(-f8 * 0.7F);
            d4 = e.prevPosX + (e.posX - e.prevPosX) * (double)partialTicks + vec3d.x;
            d5 = e.prevPosY + (e.posY - e.prevPosY) * (double)partialTicks + vec3d.y;
            d6 = e.prevPosZ + (e.posZ - e.prevPosZ) * (double)partialTicks + vec3d.z;
            d7 = (double)e.getEyeHeight();
        }
        else
        {
            d4 = e.prevPosX + (e.posX - e.prevPosX) * (double)partialTicks - d1 * d2 - d0 * 0.8D;
            d5 = e.prevPosY + (double)e.getEyeHeight() + (e.posY - e.prevPosY) * (double)partialTicks - 0.45D;
            d6 = e.prevPosZ + (e.posZ - e.prevPosZ) * (double)partialTicks - d0 * (d2) + d1 * 0.8D;
            d7 = e.isSneaking() ? -0.1875D : 0.0D;
        }

        double d13 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)partialTicks;
        double d8 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)partialTicks;
        double d9 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)partialTicks;
        
        // hand position
        double d10 = (double)((float)(d4 - d13)) - offset.x;
        double d11 = (double)((float)(d5 - d8)) + d7 - offset.y;
        double d12 = (double)((float)(d6 - d9)) - offset.z;
        
//        double X;
//        double Y;
//        double Z;
//        
        vec thispos = new vec(x, y, z);

        vec handpos = new vec(d10+x, d11+y, d12+z);
        vec somethingpos = new vec(d13, d8, d9).sub(thispos);

        //vec vecLook = new vec (e.getLookVec().x, e.getLookVec().y,e.getLookVec().z);
        //vecLook.inverse();
        //vecLook = vecLook.normalize();
        //vecLook.changelen(-5);

        vec vecHelp = new vec (1,0,1);
        //vecHelp = vecHelp.mult(e.);
        vecHelp = vecHelp.normalize();
        vecHelp = vecHelp.mult(3);
        vecHelp.inverse();
        //handpos = handpos.add(vecHelp);
        //thispos = thispos.add(vecHelp);


//        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        this.bindTexture(this.getEntityTexture(entity));
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();

        if (segmenthandler == null) {
            this.drawSegment(thispos, handpos, arrow.taut, tessellator, vertexbuffer);
        } else {
        	for (int i = 0; i < segmenthandler.segments.size() - 1; i++) {
        		vec from = segmenthandler.segments.get(i).sub(somethingpos);
        		vec to = segmenthandler.segments.get(i+1).sub(somethingpos);

        		if (i == 0) {
        			from = thispos;
        		}
        		if (i + 2 == segmenthandler.segments.size()) {
        			to = handpos;
        		}

        		double taut = 1;
        		if (i == segmenthandler.segments.size() - 2) {
//        			taut = arrow.taut;
        		}

                this.drawSegment(from, to, taut, tessellator, vertexbuffer);
        	}
        }

        GlStateManager.enableLighting();
//        GlStateManager.enableTexture2D();
        GlStateManager.enableCull();

        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();

        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);


        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        indexk +=1;

        if (indexk > 3) {
            if (entity != null && e != null){
                try {
                    if (!((grappleArrow) entity).attached){
                        vec speed = new vec (+ (e.posX-e.prevPosX),(e.posY-e.prevPosY), (e.posZ-e.prevPosZ));
                        SpawnParticles spawnParticles = new SpawnParticles(e.world, e.posX + handpos.x, e.posY+handpos.y, e.posZ + handpos.z, entity.posX, entity.posY, entity.posZ, speed,true, (int) (arrow.customization.throwspeed/7));
                    }
                }
                catch (NullPointerException reportedException){

                }
                indexk = 0;
            }
        }
        else {
            if (first){
                try {
                    if (!((grappleArrow) entity).attached){
                        vec speed = new vec (+ (e.posX-e.prevPosX),(e.posY-e.prevPosY), (e.posZ-e.prevPosZ));
                        SpawnParticles spawnParticles = new SpawnParticles(e.world, e.posX + handpos.x, e.posY+handpos.y, e.posZ + handpos.z, entity.posX, entity.posY, entity.posZ, speed,true, (int) (arrow.customization.throwspeed/7));                    }
                }
                catch (NullPointerException reportedException){

                }
                indexk = 0;
                first = false;
            }
            if (startweb){
                try {
                    vec speed = new vec (+ (e.posX-e.prevPosX),(e.posY-e.prevPosY), (e.posZ-e.prevPosZ));
                    SpawnParticles spawnParticles = new SpawnParticles(e.world, e.posX + handpos.x, e.posY+handpos.y, e.posZ + handpos.z, entity.posX, entity.posY, entity.posZ, speed,false, (int) (arrow.customization.throwspeed/7));
                    startweb = false;
                }
                catch (NullPointerException reportedException){

                }
            }
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        
        
    }
    
    public void drawDebug(vec point, Tessellator tessellator, BufferBuilder vertexbuffer, float R, float G, float B) {
        double X = point.x;
        double Y = point.y;
        double Z = point.z;

        vertexbuffer.begin(5, DefaultVertexFormats.POSITION_COLOR);

        vertexbuffer.pos(X, Y + 0.025D, Z).color(R, G, B, 1.0F).endVertex();
        vertexbuffer.pos(X - 0.025D, Y, Z - 0.025D).color(R, G, B, 1.0F).endVertex();
        vertexbuffer.pos(X, Y + 2 + 0.025D, Z).color(R, G, B, 1.0F).endVertex();
        vertexbuffer.pos(X - 0.025D, Y + 2, Z - 0.025D).color(R, G, B, 1.0F).endVertex();

        tessellator.draw();
        vertexbuffer.begin(5, DefaultVertexFormats.POSITION_COLOR);

        vertexbuffer.pos(X + 0.025D, Y, Z - 0.025D).color(R, G, B, 1.0F).endVertex();
        vertexbuffer.pos(X, Y + 0.025D, Z).color(R, G, B, 1.0F).endVertex();
        vertexbuffer.pos(X + 0.025D, Y+2, Z - 0.025D).color(R, G, B, 1.0F).endVertex();
        vertexbuffer.pos(X, Y+2 + 0.025D, Z).color(R, G, B, 1.0F).endVertex();

        tessellator.draw();
        vertexbuffer.begin(5, DefaultVertexFormats.POSITION_COLOR);

        vertexbuffer.pos(X, Y - 0.025D, Z).color(R, G, B, 1.0F).endVertex();
        vertexbuffer.pos(X + 0.025D, Y, Z - 0.025D).color(R, G, B, 1.0F).endVertex();
        vertexbuffer.pos(X, Y+2 - 0.025D, Z).color(R, G, B, 1.0F).endVertex();
        vertexbuffer.pos(X + 0.025D, Y+2, Z - 0.025D).color(R, G, B, 1.0F).endVertex();

        tessellator.draw();
        vertexbuffer.begin(5, DefaultVertexFormats.POSITION_COLOR);

        vertexbuffer.pos(X - 0.025D, Y, Z - 0.025D).color(R, G, B, 1.0F).endVertex();
        vertexbuffer.pos(X, Y - 0.025D, Z).color(R, G, B, 1.0F).endVertex();
        vertexbuffer.pos(X - 0.025D, Y+2, Z - 0.025D).color(R, G, B, 1.0F).endVertex();
        vertexbuffer.pos(X, Y+2 - 0.025D, Z).color(R, G, B, 1.0F).endVertex();

        tessellator.draw();
    }
    
    public void drawSegment(vec start, vec finish, double taut, Tessellator tessellator, BufferBuilder vertexbuffer) {
    	if (start.sub(finish).length() < 0.05) {
    		return;
    	}

        double X;
        double Y;
        double Z;

        double x = start.x;
        double y = start.y;
        double z = start.z;
        double d10 = finish.x - x;
        double d11 = finish.y - y;
        double d12 = finish.z - z;

        vec forward = finish.sub(start).changelen(1);
        vec up = forward.cross(new vec(1, 0, 0));
        if (up.length() == 0) {
        	up = new vec(1, 0, 0);
        }
        up.changelen_ip(0.025);
        vec side = forward.cross(up);
        side.changelen_ip(0.025);

        vec[] corners = new vec[] {up.mult(-1).add(side.mult(-1)), up.add(side.mult(-1)), up.add(side), up.mult(-1).add(side)};

        for (int corner = 0; corner < 4; corner++) {
            vertexbuffer.begin(5, DefaultVertexFormats.POSITION_TEX);

            vec corner1 = corners[corner];
            vec corner2 = corners[(corner + 1) % 4];

            for (int i1 = 0; i1 <= 16; ++i1)
            {
//                float R = 0.5F;
//                float G = 0.4F;
//                float B = 0.3F;
//
//                if (i1 % 2 == 0)
//                {
//                    R *= 0.7F;
//                    G *= 0.7F;
//                    B *= 0.7F;
//                }

                float f10 = (float)i1 / 16.0F;

            	X = x + d10 * (double)f10;
            	Z = z + d12 * (double)f10;
            	Y = y + d11 * (double)f10 - (1 - taut) * (0.25 - Math.pow((f10 - 0.5), 2)) * 1.5;

                vertexbuffer.pos(X + corner1.x, Y + corner1.y, Z + corner1.z).tex(0, i1/15.0F).endVertex();
                vertexbuffer.pos(X + corner2.x, Y + corner2.y, Z + corner2.z).tex(1, i1/15.0F).endVertex();
            }

            tessellator.draw();
        }

    }

    @Override
	public boolean shouldRender(T livingEntity, ICamera camera, double camX,
			double camY, double camZ) {
		return true;
	}



    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return LEASH_KNOT_TEXTURES;
    }
}
