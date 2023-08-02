package org.gggsd.plugin;

public class tpPoints {


   private int x,y,z;
   private String name;
   private String PlayerOwner;

    public tpPoints (int x, int y, int z, String name, String playerOwner) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
        this.PlayerOwner = playerOwner;
    }

    public String getName() {
        return name;
    }
    public String getPlayerOwner() {
        return PlayerOwner;
    }
    public void setXYZ (int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
