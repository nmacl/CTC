package org.macl.ctc;

import org.macl.ctc.game.GameManager;
import org.macl.ctc.game.WorldManager;

public class Default {
    public Main main;
    public GameManager game;
    public WorldManager worldManager;
    public Default(Main main) {
        this.main = main;
        this.game = main.game;
        this.worldManager = main.worldManager;
    }
}
