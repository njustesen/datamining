package shopping.apriori;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import shopping.models.AssRule;
import shopping.models.ItemSet;


public class Apriori {
	/***
	 * The TRANSACTIONS 2-dimensional array holds the full data set for the lab
	 */
    static final int[][] TRANSACTIONS = new int[][] { 
    	{ 1, 2, 3, 4, 5 }, 
    	{ 1, 3, 5 },
    	{ 2, 3, 5 }, 
    	{ 1, 5 }, 
    	{ 1, 3, 4 }, 
    	{ 2, 3, 5 }, 
    	{ 2, 3, 5 },
        { 3, 4, 5 }, 
        { 4, 5 }, 
        { 2 }, 
        { 2, 3 }, 
        { 2, 3, 4 }, 
        { 3, 4, 5 } };

    public static void main( String[] args ) {
        // TODO: Select a reasonable support threshold via trial-and-error. Can either be percentage or absolute value.
        final int supportThreshold = 38;
        List<ItemSet> frequent = apriori( TRANSACTIONS, supportThreshold );
        System.out.println("Frequent patterns:");
        for(ItemSet set : frequent)
        	System.out.println(set);
        
        System.out.println("");
        
        // Create association rules from the frequent itemsets
        List<AssRule> assRules = generateAssRules(frequent, TRANSACTIONS, supportThreshold);
        
        System.out.println("Ass. rules: ");
        for (AssRule rule : assRules){
        	
        	System.out.print(rule.getA() + " -> " + rule.getB() + "\n");
        	
        }
    }

    public static List<ItemSet> apriori( int[][] transactions, int supportThreshold ) {
    	List<ItemSet> frequentSets = new ArrayList<ItemSet>();
    	int k;
        Hashtable<ItemSet, Integer> frequentItemSets = generateFrequentItemSetsLevel1( transactions, supportThreshold );
        for (k = 1; frequentItemSets.size() > 0; k++) {
            System.out.print( "Finding frequent itemsets of length " + (k + 1) );
            frequentItemSets = generateFrequentItemSets( supportThreshold, transactions, frequentItemSets );
            
            for(ItemSet set : frequentItemSets.keySet())
            	frequentSets.add(set);
            
            System.out.println( " found " + frequentSets.size() );
        }
        
        // TODO: return something useful
        return frequentSets;
    }

    private static List<AssRule> generateAssRules(List<ItemSet> frequentSets, int[][] transactions, int supportThreshold) {
		
    	List<AssRule> rules = new ArrayList<AssRule>();
    	
    	int[][] setArr = new int[frequentSets.size()][];
    	for(int i = 0; i < frequentSets.size(); i++)
    		setArr[i] = frequentSets.get(i).getSet();
    	
    	for(ItemSet set : frequentSets){
    		List<ItemSet> subsets = subsets(set);
    		for(ItemSet subset : subsets){
    			
    			float support = (float)countSupport(set.getSet(), setArr) / (float)countSupport(subset.getSet(), setArr);
    			if (support * 100f >= supportThreshold){
    				List<Integer> b = new ArrayList<Integer>();
    				for(int i : set.getSet())
    					b.add(i);
    				
    				for(int i : subset.getSet())
    					b.remove(new Integer(i));
    				
    				int[] arr = new int[b.size()];
    				for(int i = 0; i < b.size(); i++)
    					arr[i] = b.get(i);
    				
    				ItemSet l = new ItemSet(arr);
        			rules.add(new AssRule(subset, l));
    			}
    		}
    	}
    	
		return rules;
	}

	private static Hashtable<ItemSet, Integer> generateFrequentItemSets( int supportThreshold, int[][] transactions,
                    Hashtable<ItemSet, Integer> lowerLevelItemSets ) {
        
    	Hashtable<ItemSet, Integer> itemSets = new Hashtable<ItemSet, Integer>();
    	
    	// TODO: first generate candidate itemsets from the lower level itemsets
    	List<ItemSet> candidates = generateCandidates(lowerLevelItemSets);
    	
    	/*
         * TODO: now check the support for all candidates and add only those
         * that have enough support to the set
         */
    	for(ItemSet candidate : candidates){
    		
    		int support = countSupport(candidate.getSet(), transactions);
    		if (support >= supportThreshold)
    			itemSets.put(candidate, support);
    		
    	}
        
        // TODO: return something useful
        return itemSets;
    }
    
    private static List<ItemSet> generateCandidates(Hashtable<ItemSet, Integer> itemSets) {
		
    	List<ItemSet> candidates = new ArrayList<ItemSet>();
    	List<ItemSet> prunedCandidates = new ArrayList<ItemSet>();
    	Object[] setArr = itemSets.keySet().toArray();
    	
    	// Generate
    	for(int a = 0; a < setArr.length; a++){
    		
    		for(int b = a+1; b < setArr.length; b++){
    	
    			if (isJoinable((ItemSet)setArr[a], (ItemSet)setArr[b])){
    				ItemSet candidate = joinSets((ItemSet)setArr[a], (ItemSet)setArr[b]);
    				candidates.add(candidate);
    				prunedCandidates.add(candidate);
    			}
    			
    		}
        		
    	}
    	
    	// Prune
    	for(ItemSet candidate : candidates){
    		
    		for(ItemSet subset : subsets(candidate)){
        		
        		if (!itemSets.containsKey(subset)){
        			prunedCandidates.remove(candidate);
        		}
        		
        	}
    		
    	}
    	
		return prunedCandidates;
	}

	private static List<ItemSet> subsets(ItemSet candidate) {
		
		List<ItemSet> subsets = new ArrayList<ItemSet>();
		
		for(int x = 0; x < candidate.getSet().length; x++){
			int[] subset = new int[candidate.getSet().length-1];
			int b = 0;
			for(int i = 0; i < candidate.getSet().length; i++){
				
				if (i == x)
					b--;
				else
					subset[i+b] = candidate.getSet()[i];
				
			}
			
			subsets.add(new ItemSet(subset));
			
		}
		
		return subsets;
		
	}

	private static boolean isJoinable(ItemSet first, ItemSet second) {
		
		for(int i = 0; i < first.getSet().length - 1; i++){
		
			if (first.getSet()[i] != second.getSet()[i])
				return false;
			
		}
		
		return true;
	}

	private static Hashtable<ItemSet, Integer> generateFrequentItemSetsLevel1( int[][] transactions, int supportThreshold ) {
        
    	Hashtable<ItemSet, Integer> itemSets = new Hashtable<ItemSet, Integer>();
    	
    	for(int[] itemSet : transactions){
    		
    		for(int i : itemSet){
    			int[] subset = {i};
    			ItemSet subItemSet = new ItemSet(subset);
    			if (itemSets.containsKey(subItemSet))
    				continue;
    			
	    		int support = countSupport(subset, transactions);
	    		if (support >= supportThreshold)
	    			itemSets.put(subItemSet, support);
    		}
    		
    	}
    	
        return itemSets;
    }

    private static ItemSet joinSets( ItemSet first, ItemSet second ) {
        
    	int[] joinSet = Arrays.copyOf(first.getSet(), first.getSet().length+1);
    	
    	joinSet[joinSet.length-2] = Math.min(first.getSet()[first.getSet().length-1], 
    												second.getSet()[second.getSet().length-1]);
    	
    	joinSet[joinSet.length-1] = Math.max(first.getSet()[first.getSet().length-1], 
													second.getSet()[second.getSet().length-1]);
    	
        return new ItemSet(joinSet);
    }

    private static int countSupport( int[] itemSet, int[][] transactions ) {
        // Assumes that items in ItemSets and transactions are both unique
    	
    	int count = 0;
    	for(int[] transaction : transactions){
    		boolean itemsInTransaction = true;
    		for(int i : itemSet){
    			boolean inT = false;
    			for(int t : transaction){
        			if (i == t){
        				inT = true;
        				break;
        			}
        		}
    			if (!inT){
    				itemsInTransaction = false;
    				break;
    			}
    		}
    		if (itemsInTransaction)
    			count++;
    	}
    	
        // TODO: return something useful
        return (int) (((float)count / (float)transactions.length) * 100);
    }

}
