package com.github.lukasgail.saveAndLoad;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "gate")
public class Gate {
    @DatabaseField(id = true)
    private String name;

    @DatabaseField()
    private String world;

    @DatabaseField()
    private boolean onlyOpensWithPermission;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public boolean isOnlyOpensWithPermission() {
        return onlyOpensWithPermission;
    }

    public void setOnlyOpensWithPermission(boolean onlyOpensWithPermission) {
        this.onlyOpensWithPermission = onlyOpensWithPermission;
    }
}
