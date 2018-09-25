package com.imlianai.dollpub.app.modules.publics.kws.mr.ac;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbsState implements State {

    private Map<Character, State> nextStates = new HashMap<Character, State>();

    private Map<Character, State> retNextStates = Collections
            .unmodifiableMap(nextStates);

    private Set<String> results = new LinkedHashSet<String>();

    private Set<String> retResults = Collections.unmodifiableSet(results);

    private State failState;

    private String longestResult = null;

    private String shortestResult = null;

    protected State getNextState(char input) {
        return nextStates.get(input);
    }

    @Override
    public State fail() {
        return failState;
    }

    @Override
    public boolean isEmpty() {
        return results.isEmpty();
    }

    @Override
    public void setFailState(State failState) {
        this.failState = failState;
    }

    @Override
    public State setNextState(char input, State state) {
        return nextStates.put(input, state);
    }

    @Override
    public Collection<Character> getNextInputs() {
        return retNextStates.keySet();
    }

    @Override
    public Collection<State> getNextStates() {
        return retNextStates.values();
    }

    @Override
    public void addResult(String result) {
        if (null == longestResult || longestResult.length() < result.length()) {
            longestResult = result;
        }
        if (null == shortestResult || shortestResult.length() > result.length()) {
            shortestResult = result;
        }
        results.add(result);
    }

    @Override
    public Collection<String> getResults() {
        return retResults;
    }

    @Override
    public void addAllResults(Collection<String> results) {
        for (String result: results) {
            addResult(result);
        }
    }

    @Override
    public String getLongestResult() {
        return longestResult;
    }

    @Override
    public String getShortestResult() {
        return shortestResult;
    }

}
