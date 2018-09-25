package com.imlianai.zjdoll.app.modules.core.doll.utils;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class AliasMethod {
	private final double[] probability;
	private final int[] alias;
	private final int length;
	private final Random rand;
	
	public AliasMethod(List<Double> prob){
		this(prob,new Random());
	}
	
	public AliasMethod(List<Double> prob,Random rand){
		/* Begin by doing basic structural checks on the inputs. */
        if (prob == null || rand == null)
            throw new NullPointerException();
        if (prob.size() == 0)
            throw new IllegalArgumentException("Probability vector must be nonempty.");
        
        this.rand = rand;
        this.length = prob.size();
        this.probability = new double[length];
        this.alias = new int[length];       
        
        double[] probtemp = new double[length];
        Deque<Integer> small = new ArrayDeque<Integer>();
        Deque<Integer> large = new ArrayDeque<Integer>();
        
        /* divide elements into 2 groups by probability */
        for(int i=0;i<length;i++){
        	probtemp[i] = prob.get(i)*length;  /* initial probtemp */
        	if(probtemp[i]<1.0)
        		small.add(i);
        	else
        		large.add(i);
        }
        
        while(!small.isEmpty() && !large.isEmpty()){
        	int less = small.pop();
        	int more = large.pop();
        	probability[less] = probtemp[less];
        	alias[less] = more;
        	probtemp[more] = probtemp[more]-(1.0-probability[less]);
        	if(probtemp[more]<1.0)
        		small.add(more);
        	else
        		large.add(more);
        }
        /* At this point, everything is in one list, which means that the
         * remaining probabilities should all be 1/n.  Based on this, set them
         * appropriately.
         */
        while (!small.isEmpty())
            probability[small.pop()] = 1.0;
        while (!large.isEmpty())
            probability[large.pop()] = 1.0;       
	}
	
	/**
     * Samples a value from the underlying distribution.
     *
     */
    public int next() {
        /* Generate a fair die roll to determine which column to inspect. */
        int column = rand.nextInt(length);

        /* Generate a biased coin toss to determine which option to pick. */
        boolean coinToss = rand.nextDouble() < probability[column];

        /* Based on the outcome, return either the column or its alias. */
        return coinToss? column : alias[column];
    }
    
    public static void main(String[] argv){
    	List<Double> prob = new ArrayList<Double>();
    	prob.add(0.071);prob.add(0.03);prob.add(0.01);prob.add(0.1/100);prob.add(0.25);  
    	prob.add(0.1);prob.add(0.03);prob.add(0.01);prob.add(0.25);prob.add(0.1);
    	prob.add(0.03);prob.add(0.01);prob.add(0.05);prob.add(0.02);prob.add(0.01);
    	prob.add(0.5/100);prob.add(0.2/100);prob.add(0.1/100);prob.add(0.02);
    	int[] cnt = new int[prob.size()];
    	AliasMethod am = new AliasMethod(prob);
//    	System.out.println(am.next());
    	for(int i=0;i<100000000;i++){
    		cnt[am.next()]++;
    	}
    	for(int i=0;i<cnt.length;i++)
    		System.out.println(cnt[i]);
    }
}