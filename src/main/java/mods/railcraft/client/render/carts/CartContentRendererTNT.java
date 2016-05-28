/* 
 * Copyright (c) CovertJaguar, 2014 http://railcraft.info
 * 
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at http://railcraft.info/wiki/info:license.
 */
package mods.railcraft.client.render.carts;

import mods.railcraft.client.render.OpenGL;
import mods.railcraft.common.carts.CartExplosiveBase;
import net.minecraft.entity.item.EntityMinecart;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class CartContentRendererTNT extends CartContentRenderer {

    @Override
    public void render(RenderCart renderer, EntityMinecart cart, float light, float time) {
        OpenGL.glPushMatrix();
//        OpenGL.glTranslatef(0.0F, 0.3125F, 0.0F);
//        OpenGL.glRotatef(90F, 0.0F, 1.0F, 0.0F);
        CartExplosiveBase tnt = (CartExplosiveBase) cart;
        if (tnt.isPrimed() && ((float) tnt.getFuse() - time) + 1.0F < 10F) {
            float scale = 1.0F - (((float) tnt.getFuse() - time) + 1.0F) / 10F;
            if (scale < 0.0F) {
                scale = 0.0F;
            }
            if (scale > 1.0F) {
                scale = 1.0F;
            }
            scale *= scale;
            scale *= scale;
            scale = 1.0F + scale * 0.3F;
            OpenGL.glScalef(scale, scale, scale);
        }
        super.render(renderer, cart, light, time);
        if (tnt.isPrimed() && (tnt.getFuse() / 5) % 2 == 0) {
            OpenGL.glPushAttrib(GL11.GL_ENABLE_BIT);
            OpenGL.glDisable(GL11.GL_TEXTURE_2D);
            OpenGL.glDisable(GL11.GL_LIGHTING);
            OpenGL.glEnable(GL11.GL_BLEND);
            OpenGL.glBlendFunc(770, 772);
            float alpha = (1.0F - (((float) tnt.getFuse() - time) + 1.0F) / 100F) * 0.8F;
            OpenGL.glColor4f(1.0F, 1.0F, 1.0F, alpha);
            OpenGL.glScalef(1.01f, 1.01f, 1.01f);
            super.render(renderer, cart, 1, time);
            OpenGL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            OpenGL.glPopAttrib();
        }
        OpenGL.glPopMatrix();
    }
}
