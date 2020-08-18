package com.github.lukasgail.simplegates;


import org.bukkit.block.Block;

import java.io.Serializable;

public class Gate implements Serializable {

    private String name;
    private GateBlock[] gateBlocks;
    private Block[] activatorButtons;
    private boolean onlyOpensWithPermission;


}
