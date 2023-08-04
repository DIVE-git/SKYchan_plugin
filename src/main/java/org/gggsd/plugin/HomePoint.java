package org.gggsd.plugin;

public class HomePoint {
    private int homeX,homeY,homeZ;
    public int getHomeX() {
        return homeX;
    }

    public int getHomeY() {
        return homeY;
    }

    public int getHomeZ() {
        return homeZ;
    }

    public void setHomeXYZ(int homeX, int homeY, int homeZ) {
        this.homeX = homeX;
        this.homeY = homeY;
        this.homeZ = homeZ;
    }
}
