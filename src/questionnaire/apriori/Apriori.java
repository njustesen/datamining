package questionnaire.apriori;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import questionnaire.cleaners.Cleaner;
import questionnaire.models.Answer;
import questionnaire.models.ProgrammingLanguage;
import questionnaire.readers.Reader;

public class Apriori {

    public static void main( String[] args ) throws IOException, ParseException {
    	
    	// Read
		List<List<String>> data = new Reader().read("Data_Mining_Student_DataSet_Spring_2013.csv");
		Date dateOfData = new SimpleDateFormat("yyyy-MM-dd").parse("2013-02-06");
		
		// Parse
		int rows = new Reader().rowsInHeader("Data_Mining_Student_DataSet_Spring_2013.csv");
		List<Answer> answers = new Cleaner().clean(data, rows, dateOfData);
		
		// Extract to item sets
		List<ItemSet> itemSets = extractItemSets(answers);
		
        final int supportThreshold = 20;
        List<ItemSet> frequent = apriori( itemSets, supportThreshold );
        System.out.println("Frequent patterns:");
        for(ItemSet set : frequent)
        	System.out.println(set);
        
        System.out.println("");
        
        // Create association rules from the frequent itemsets
        List<AssRule> assRules = generateAssRules(frequent, itemSets, supportThreshold);
        
        System.out.println("Ass. rules: ");
        for (AssRule rule : assRules){
        	
        	System.out.print(rule.getA() + " -> " + rule.getB() + "\n");
        	
        }
    }

    private static List<ItemSet> extractItemSets(List<Answer> answers) {
		
    	List<ItemSet> sets = new ArrayList<ItemSet>();
    	
    	for(Answer answer : answers){
    		
    		List<String> set = new ArrayList<String>();
    		
    		if (answer.getAnimal() != null){
    			set.add(answer.getAnimal().name);

    			for(ProgrammingLanguage lang : answer.getProLanParsed())
    				set.add(lang.getName());
    			
    		}
    		
    		sets.add(new ItemSet(set));
    		
    	}
    	
		return sets;
	}

	public static List<ItemSet> apriori( List<ItemSet> itemSets, int supportThreshold ) {
    	List<ItemSet> frequentSets = new ArrayList<ItemSet>();
    	int k;
        Hashtable<ItemSet, Integer> frequentItemSets = generateFrequentItemSetsLevel1( itemSets, supportThreshold );
        for (k = 1; frequentItemSets.size() > 0; k++) {
            System.out.print( "Finding frequent itemsets of length " + (k + 1) );
            frequentItemSets = generateFrequentItemSets( supportThreshold, itemSets, frequentItemSets );
            
            for(ItemSet set : frequentItemSets.keySet())
            	frequentSets.add(set);
            
            System.out.println( " found " + frequentSets.size() );
        }
        
        // TODO: return something useful
        return frequentSets;
    }

    private static List<AssRule> generateAssRules(List<ItemSet> frequentSets, List<ItemSet> dataSets, int supportThreshold) {
		
    	List<AssRule> rules = new ArrayList<AssRule>();
    	
    	for(ItemSet set : frequentSets){
    		List<ItemSet> subsets = subsets(set);
    		for(ItemSet subset : subsets){
    			
    			float support = (float)countSupport(set, frequentSets) / (float)countSupport(subset, frequentSets);
    			if (support * 100f >= supportThreshold){
    				List<String> b = new ArrayList<String>();
    				for(String str : set.getSet())
    					b.add(str);
    				
    				for(String str : subset.getSet())
    					b.remove(str);
    				
    				ItemSet l = new ItemSet(b);
        			rules.add(new AssRule(subset, l));
    			}
    		}
    	}
    	
		return rules;
	}

	private static Hashtable<ItemSet, Integer> generateFrequentItemSets( int supportThreshold, List<ItemSet> data,
                    Hashtable<ItemSet, Integer> lowerLevelItemSets ) {
        
    	Hashtable<ItemSet, Integer> itemSets = new Hashtable<ItemSet, Integer>();
    	
    	// TODO: first generate candidate itemsets from the lower level itemsets
    	List<ItemSet> candidates = generateCandidates(lowerLevelItemSets);

    	for(ItemSet candidate : candidates){
    		
    		int support = countSupport(candidate, data);
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
		
		for(int x = 0; x < candidate.getSet().size(); x++){
			String[] subset = new String[candidate.getSet().size()-1];
			int b = 0;
			for(int i = 0; i < candidate.getSet().size(); i++){
				
				if (i == x)
					b--;
				else
					subset[i+b] = candidate.getSet().get(i);
				
			}
			
			List<String> strSet = Arrays.asList(subset);
			Collections.sort(strSet);
			subsets.add(new ItemSet(strSet));
			
		}
		
		return subsets;
		
	}

	private static boolean isJoinable(ItemSet first, ItemSet second) {
		
		for(int i = 0; i < first.getSet().size() - 1; i++){
		
			if (first.getSet().get(i) != second.getSet().get(i))
				return false;
			
		}
		
		return true;
	}

	private static Hashtable<ItemSet, Integer> generateFrequentItemSetsLevel1( List<ItemSet> data, int supportThreshold ) {
        
    	Hashtable<ItemSet, Integer> itemSets = new Hashtable<ItemSet, Integer>();
    	
    	for(ItemSet itemSet : data){
    		
    		for(String str : itemSet.getSet()){
    			List<String> subset = new ArrayList<String>();
    			subset.add(str);
    			ItemSet subItemSet = new ItemSet(subset);
    			if (itemSets.containsKey(subItemSet))
    				continue;
    			
	    		int support = countSupport(subItemSet, data);
	    		if (support >= supportThreshold)
	    			itemSets.put(subItemSet, support);
    		}
    		
    	}
    	
        return itemSets;
    }

    private static ItemSet joinSets( ItemSet first, ItemSet second ) {
        
    	List<String> join = new ArrayList<String>();
    	join.addAll(first.getSet());
    	join.addAll(second.getSet());
    	Collections.sort(join);
    	
        return new ItemSet(join);
    }

    private static int countSupport( ItemSet itemSset, List<ItemSet> data ) {
      
    	int count = 0;
    	for(ItemSet dataSet : data){
    		boolean itemsInData = true;
    		for(String setStr : itemSset.getSet()){
    			boolean inT = false;
    			for(String dataStr : dataSet.getSet()){
        			if (dataStr.equals(setStr)){
        				inT = true;
        				break;
        			}
        		}
    			if (!inT){
    				itemsInData = false;
    				break;
    			}
    		}
    		if (itemsInData)
    			count++;
    	}
    	
        return (int) (((float)count / (float)data.size()) * 100);
    }

}
