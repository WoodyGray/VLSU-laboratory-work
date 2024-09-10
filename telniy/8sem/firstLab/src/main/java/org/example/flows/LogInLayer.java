package org.example.flows;

import org.example.flowMachine.EnterLayer;
import org.example.flowMachine.Layer;
import org.example.flowMachine.LayerMachine;
import org.example.util.ConsoleDisplayManager;

public class LogInLayer implements EnterLayer {

    public LogInLayer(LayerMachine layerMachine) {
        this.layerMachine = layerMachine;
    }

    private final LayerMachine layerMachine;

    @Override
    public void enter() {
        ConsoleDisplayManager.printHeader();
        choseCommand();
    }
}
