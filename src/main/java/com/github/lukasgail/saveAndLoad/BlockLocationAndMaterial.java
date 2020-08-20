package com.github.lukasgail.saveAndLoad;

import java.io.Serializable;

public class BlockLocationAndMaterial implements Serializable {

    private String material;
    private double x;
    private double y;
    private double z;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
