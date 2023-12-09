package fr.univ_lyon1.info.m1.elizagpt.view;

import java.util.ArrayList;



/**
 * A utility class representing a list of search strategies.
 * Is used to avoid having to modify list of Strategies in 
 * use, espeacialy if there were multiple class that needed
 * a list of all the strategies.
 */
public class SearchStrategyList {
    private ArrayList<SearchStrategy> searchOptions;

    /**
     * Initializes the list of search strategies with all Strategies available.
     */
    public SearchStrategyList() {
        searchOptions = new ArrayList<>();
        searchOptions.add(new RegexSearchStrategy());
        // searchOptions.add(new Option2Search());
        searchOptions.add(new FindMessagesByWordStrategy());
        searchOptions.add(new FindMessagesBySubstringStrategy());
    }

    /**
     * Gets the list of search strategies.
     *
     * @return The list of search strategies.
     */
    public ArrayList<SearchStrategy> getList() {
        return searchOptions;
    }
}

