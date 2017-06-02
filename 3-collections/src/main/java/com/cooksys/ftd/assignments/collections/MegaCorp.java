package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {
	
	private Set<Capitalist> element = new HashSet<>();
	private Map<Capitalist, FatCat> parent = new HashMap<>();

    /**
     * Adds a given element to the hierarchy.
     * <p>
     * If the given element is already present in the hierarchy,
     * do not add it and return false
     * <p>
     * If the given element has a parent and the parent is not part of the hierarchy,
     * add the parent and then add the given element
     * <p>
     * If the given element has no parent but is a Parent itself,
     * add it to the hierarchy
     * <p>
     * If the given element has no parent and is not a Parent itself,
     * do not add it and return false
     *
     * @param capitalist the element to add to the hierarchy
     * @return true if the element was added successfully, false otherwise
     */
    @Override
    public boolean add(Capitalist capitalist) {
        if (this.has(capitalist) || capitalist == null) return false;
        if (!capitalist.hasParent() && !(capitalist instanceof FatCat)) return false;
        
        if (capitalist.hasParent() && !this.has(capitalist.getParent())) {
        	add(capitalist.getParent());
        	element.add(capitalist);
        	parent.put(capitalist, capitalist.getParent());
        	return true;
        } 
        
        if (capitalist.hasParent() && this.has(capitalist.getParent())) {
        	element.add(capitalist);
        	parent.put(capitalist, capitalist.getParent());
        	return true;
        } 
        
        if (!capitalist.hasParent() && (capitalist instanceof FatCat)) {
        	element.add(capitalist);
        	return true;
        }
       
        return false;
    }

    /**
     * @param capitalist the element to search for
     * @return true if the element has been added to the  , false otherwise
     */
    @Override
    public boolean has(Capitalist capitalist) {
        if (element.contains(capitalist)) {
        	return true;
        }
        else return false;
    }

    /**
     * @return all elements in the hierarchy,
     * or an empty set if no elements have been added to the hierarchy
     */
    @Override
    public Set<Capitalist> getElements() {
    	Set<Capitalist> elements = new HashSet<>();
    	elements.addAll(element);
    	return elements;
    }

    /**
     * @return all parent elements in the hierarchy,
     * or an empty set if no parents have been added to the hierarchy
     */
    @Override
    public Set<FatCat> getParents() {
    	Set<FatCat> fatCat = new HashSet<>();
    	Iterator entries = parent.entrySet().iterator();
    	while (entries.hasNext()) {
    		Map.Entry entry = (Map.Entry) entries.next();
    		fatCat.add((FatCat)entry.getValue()); 	    
    	}
    	
    	return fatCat;
    }

    /**
     * @param fatCat the parent whose children need to be returned
     * @return all elements in the hierarchy that have the given parent as a direct parent,
     * or an empty set if the parent is not present in the hierarchy or if there are no children
     * for the given parent
     */
    @Override
    public Set<Capitalist> getChildren(FatCat fatCat) {
    	Set<Capitalist> children = new HashSet<>();
    	Iterator entries = parent.entrySet().iterator();
    	while (entries.hasNext()) {
    		Map.Entry entry = (Map.Entry) entries.next();
    		if (fatCat == ((FatCat)entry.getValue())) {
    			children.add((Capitalist)entry.getKey());
    		}
    	}
    	return children;
    }

    /**
     * @return a map in which the keys represent the parent elements in the hierarchy,
     * and the each value is a set of the direct children of the associate parent, or an
     * empty map if the hierarchy is empty.
     */
    @Override
    public Map<FatCat, Set<Capitalist>> getHierarchy() {
        throw new NotImplementedException();
    }

    /**
     * @param capitalist
     * @return the parent chain of the given element, starting with its direct parent,
     * then its parent's parent, etc, or an empty list if the given element has no parent
     * or if its parent is not in the hierarchy
     */
    @Override
    public List<FatCat> getParentChain(Capitalist capitalist) {
        List<FatCat> fatCat = new ArrayList<>();
        if (capitalist == null) return fatCat;
        
        while (capitalist.hasParent() && this.has(capitalist.getParent())) {
        	fatCat.add(capitalist.getParent());
        	capitalist = capitalist.getParent();
        }
        
        return fatCat;
    }
}
