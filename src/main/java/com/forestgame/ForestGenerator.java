package com.forestgame;

import org.lwjgl.opengl.GL11;
import java.util.Random;

public class ForestGenerator {
    private static final int FOREST_RADIUS = 40;
    private static final int CLEARING_RADIUS = 6;
    private static final int TREE_COUNT = 120;
    private static final int STUMP_COUNT = 7;
    private static final int ROCK_COUNT = 8;
    private static final int BUSH_COUNT = 18;
    private static final int FERN_COUNT = 22;
    private static final Random rand = new Random();

    public static void renderForest() {
        // Деревья по кругу
        for (int i = 0; i < TREE_COUNT; i++) {
            float angle = (float) (rand.nextFloat() * 2 * Math.PI);
            float dist = CLEARING_RADIUS + 3 + rand.nextFloat() * (FOREST_RADIUS - CLEARING_RADIUS - 3);
            float x = (float) Math.cos(angle) * dist;
            float z = (float) Math.sin(angle) * dist;
            renderTree(x, 0, z, rand.nextBoolean());
        }
        // Пни и валуны на поляне
        for (int i = 0; i < STUMP_COUNT; i++) {
            float angle = (float) (rand.nextFloat() * 2 * Math.PI);
            float dist = 2.5f + rand.nextFloat() * (CLEARING_RADIUS - 2.5f);
            float x = (float) Math.cos(angle) * dist;
            float z = (float) Math.sin(angle) * dist;
            renderStump(x, 0, z);
        }
        for (int i = 0; i < ROCK_COUNT; i++) {
            float angle = (float) (rand.nextFloat() * 2 * Math.PI);
            float dist = 2.5f + rand.nextFloat() * (CLEARING_RADIUS - 2.5f);
            float x = (float) Math.cos(angle) * dist;
            float z = (float) Math.sin(angle) * dist;
            renderRock(x, 0, z);
        }
        // Кусты и папоротники
        for (int i = 0; i < BUSH_COUNT; i++) {
            float angle = (float) (rand.nextFloat() * 2 * Math.PI);
            float dist = CLEARING_RADIUS + 1 + rand.nextFloat() * (FOREST_RADIUS - CLEARING_RADIUS - 2);
            float x = (float) Math.cos(angle) * dist;
            float z = (float) Math.sin(angle) * dist;
            renderBush(x, 0, z);
        }
        for (int i = 0; i < FERN_COUNT; i++) {
            float angle = (float) (rand.nextFloat() * 2 * Math.PI);
            float dist = CLEARING_RADIUS + 0.5f + rand.nextFloat() * (FOREST_RADIUS - CLEARING_RADIUS - 1);
            float x = (float) Math.cos(angle) * dist;
            float z = (float) Math.sin(angle) * dist;
            renderFern(x, 0, z);
        }
    }
    private static void renderTree(float x, float y, float z, boolean isOak) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, z);
        // Ствол
        GL11.glColor3f(0.4f, 0.25f, 0.1f);
        drawCylinder(0.4f, 0.7f, isOak ? 7f : 11f);
        // Крона
        if (isOak) {
            GL11.glColor3f(0.18f, 0.32f, 0.13f);
            drawSphere(2.5f, 2);
        } else {
            GL11.glColor3f(0.13f, 0.22f, 0.11f);
            drawCone(1.5f, 7f);
        }
        GL11.glPopMatrix();
    }
    private static void renderStump(float x, float y, float z) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, z);
        GL11.glColor3f(0.5f, 0.3f, 0.15f);
        drawCylinder(0.5f, 0.7f, 0.7f);
        GL11.glPopMatrix();
    }
    private static void renderRock(float x, float y, float z) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, z);
        GL11.glColor3f(0.5f, 0.5f, 0.5f);
        drawSphere(0.7f, 1);
        GL11.glPopMatrix();
    }
    private static void renderBush(float x, float y, float z) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, z);
        GL11.glColor3f(0.18f, 0.32f, 0.13f);
        drawSphere(0.8f, 1);
        GL11.glPopMatrix();
    }
    private static void renderFern(float x, float y, float z) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, z);
        GL11.glColor3f(0.2f, 0.4f, 0.18f);
        drawCone(0.2f, 0.7f);
        GL11.glPopMatrix();
    }
    // Примитивы
    private static void drawCylinder(float radius, float topRadius, float height) {
        GL11.glBegin(GL11.GL_QUAD_STRIP);
        for (int i = 0; i <= 16; i++) {
            double angle = 2 * Math.PI * i / 16;
            float x = (float) Math.cos(angle);
            float z = (float) Math.sin(angle);
            GL11.glVertex3f(x * radius, 0, z * radius);
            GL11.glVertex3f(x * topRadius, height, z * topRadius);
        }
        GL11.glEnd();
    }
    private static void drawSphere(float radius, int stacks) {
        for (int i = 0; i < stacks; i++) {
            float lat0 = (float) Math.PI * (-0.5f + (float) (i) / stacks);
            float z0  = (float) Math.sin(lat0);
            float zr0 = (float) Math.cos(lat0);
            float lat1 = (float) Math.PI * (-0.5f + (float) (i+1) / stacks);
            float z1 = (float) Math.sin(lat1);
            float zr1 = (float) Math.cos(lat1);
            GL11.glBegin(GL11.GL_QUAD_STRIP);
            for (int j = 0; j <= 16; j++) {
                float lng = 2 * (float) Math.PI * (float) (j - 1) / 16;
                float x = (float) Math.cos(lng);
                float y = (float) Math.sin(lng);
                GL11.glVertex3f(radius * x * zr0, radius * y * zr0, radius * z0);
                GL11.glVertex3f(radius * x * zr1, radius * y * zr1, radius * z1);
            }
            GL11.glEnd();
        }
    }
    private static void drawCone(float radius, float height) {
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glVertex3f(0, height, 0);
        for (int i = 0; i <= 16; i++) {
            double angle = 2 * Math.PI * i / 16;
            float x = (float) Math.cos(angle);
            float z = (float) Math.sin(angle);
            GL11.glVertex3f(x * radius, 0, z * radius);
        }
        GL11.glEnd();
    }
}
