package org.gggsd.plugin;

public class TpPoints {


   private int x,y,z;


   private String playerOwner, name;

    public TpPoints(int x, int y, int z, String playerOwner, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.playerOwner = playerOwner;
        this.name = name;
    }


    public String getPlayerOwner() {
        return playerOwner;
    }

    public static int getIndex (TpPoints[] tpPoints, String point) {
        for(int i = 0;i < tpPoints.length;i++) {
            if(tpPoints[i].getName().equalsIgnoreCase(point)) return i;
        }
        return -1;
    }

    public String getName() {
        return name;
    }

    public void setPlayerOwner(String owner) { playerOwner = owner;};


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setXYZ (int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


}
