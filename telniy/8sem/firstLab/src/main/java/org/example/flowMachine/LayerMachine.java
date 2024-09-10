package org.example.flowMachine;

import java.util.HashMap;
import java.util.Map;

public class LayerMachine {
    private final Map<Class<?>, Layer> flows = new HashMap<Class<?>, Layer>();
    private Layer currentFlow;

    public void addFlow(Layer flow){
        Class<?> type = flow.getClass();
        if (flows.containsKey(type)){
            return;
        }
        this.flows.put(type, flow);
    }

    public void changeFlow(Class<? extends Layer> flowClass){
        if (!isFlowExist(flowClass)){
            return;
        }

        this.currentFlow = this.flows.get(flowClass);

        if (this.currentFlow instanceof ExitLayer){
            ((ExitLayer) this.currentFlow).exit();
        }

        if (this.currentFlow instanceof EnterLayer){
            ((EnterLayer) this.currentFlow).enter();
        }
    }

    private boolean isFlowExist(Class<?> type){
        return this.flows.containsKey(type);
    }
}
