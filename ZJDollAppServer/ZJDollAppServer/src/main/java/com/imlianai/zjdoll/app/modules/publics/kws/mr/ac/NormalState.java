package com.imlianai.zjdoll.app.modules.publics.kws.mr.ac;

public class NormalState extends AbsState {

    @Override
    public State go(char input) {
        return getNextState(input);
    }

}
