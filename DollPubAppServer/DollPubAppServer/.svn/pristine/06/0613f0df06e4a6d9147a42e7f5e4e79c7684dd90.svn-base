package com.imlianai.dollpub.app.modules.publics.kws.mr.ac;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.imlianai.dollpub.app.modules.publics.kws.mr.AbstractMatcher;
import com.imlianai.dollpub.app.modules.publics.kws.mr.Result;
public class ACMatcher extends AbstractMatcher {

	public State zeroState;

	public ACMatcher(Collection<String> keywords) {
		this.zeroState = genStates(keywords);
	}

	private State genStates(Collection<String> keywords) {
		ZeroState zeroState = new ZeroState();
		for (String keyword : keywords) {
			enter(zeroState, keyword);
		}
		zeroState.shift();
		process(zeroState);
		return zeroState;
	}

	private void enter(State zeroState, String keyword) {
		int i = 0;
		State state = zeroState, nextState;
		for (; null != (nextState = state.go(keyword.charAt(i))); i++) {
			if (i == keyword.length() - 1) {
				nextState.addResult(keyword);
				return;
			}
			state = nextState;
		}
		State newState;
		for (int p = i; p < keyword.length(); p++) {
			newState = new NormalState();
			state.setNextState(keyword.charAt(p), newState);
			state = newState;
		}
		state.addResult(keyword);
	}

	private void process(State zeroState) {
		Queue<State> stateQueue = new LinkedList<State>();
		for (State state : zeroState.getNextStates()) {
			stateQueue.add(state);
			state.setFailState(zeroState);
		}
		State s, r, n, state;
		while (!stateQueue.isEmpty()) {
			r = stateQueue.poll();
			for (char a : r.getNextInputs()) {
				s = r.go(a);
				stateQueue.add(s);
				state = r.fail();
				while (null == (n = state.go(a))) {
					state = state.fail();
				}
				s.setFailState(n);
				s.addAllResults(n.getResults());
			}
		}
	}

	@Override
	public List<Result> match(String target) {
		List<Result> ret = new ArrayList<Result>();
		State state = zeroState, n;
		char a;
		for (int i = 0; i < target.length(); i++) {
			a = target.charAt(i);
			while (null == (n = state.go(a))) {
				state = state.fail();
			}
			state = n;
			if (!state.isEmpty()) {
				for (String result : state.getResults()) {
					ret.add(new Result(i - result.length() + 1, result));
				}
			}
		}
		return ret;
	}

	@Override
	public Result matchOnce(String target) {
		State state = zeroState, n;
		char a;
		for (int i = 0; i < target.length(); i++) {
			a = target.charAt(i);
			while (null == (n = state.go(a))) {
				state = state.fail();
			}
			state = n;
			if (!state.isEmpty()) {
				String result = state.getLongestResult();
				return new Result(i - result.length() + 1, result);
			}
		}
		return null;
	}

	@Override
	public String cover(String target, char cover) {
		StringBuilder ret = new StringBuilder(target);
		State state = zeroState, n;
		char a;
		for (int i = 0; i < target.length(); i++) {
			a = target.charAt(i);
			while (null == (n = state.go(a))) {
				state = state.fail();
			}
			state = n;
			if (!state.isEmpty()) {
				String result = state.getLongestResult();
				for (int index = i - result.length() + 1; index <= i; index++) {
					ret.setCharAt(index, cover);
				}
			}
		}
		return ret.toString();
	}

	@Override
	public boolean test(String target) {
		State state = zeroState, n;
		char a;
		for (int i = 0; i < target.length(); i++) {
			a = target.charAt(i);
			while (null == (n = state.go(a))) {
				state = state.fail();
			}
			state = n;
			if (!state.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void init() {
		Collection<String> keywords = loadKeywords();
		this.zeroState = genStates(keywords);
	}

}
