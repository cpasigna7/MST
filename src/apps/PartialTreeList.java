package apps;

import java.util.Iterator;
import java.util.NoSuchElementException;

import structures.Vertex;


public class PartialTreeList implements Iterable<PartialTree> {
    
	/**
	 * Inner class - to build the partial tree circular linked list 
	 * 
	 */
	public static class Node {
		/**
		 * Partial tree
		 */
		public PartialTree tree;
		
		/**
		 * Next node in linked list
		 */
		public Node next;
		
		/**
		 * Initializes this node by setting the tree part to the given tree,
		 * and setting next part to null
		 * 
		 * @param tree Partial tree
		 */
		public Node(PartialTree tree) {
			this.tree = tree;
			next = null;
		}
	}

	/**
	 * Pointer to last node of the circular linked list
	 */
	private Node rear;
	
	/**
	 * Number of nodes in the CLL
	 */
	private int size;
	
	/**
	 * Initializes this list to empty
	 */
    public PartialTreeList() {
    	rear = null;
    	size = 0;
    }

    /**
     * Adds a new tree to the end of the list
     * 
     * @param tree Tree to be added to the end of the list
     */
    public void append(PartialTree tree) {
    	Node ptr = new Node(tree);
    	if (rear == null) {
    		ptr.next = ptr;
    	} else {
    		ptr.next = rear.next;
    		rear.next = ptr;
    	}
    	rear = ptr;
    	size++;
    }

    /**
     * Removes the tree that is at the front of the list.
     * 
     * @return The tree that is removed from the front
     * @throws NoSuchElementException If the list is empty
     */
    public PartialTree remove() 
    throws NoSuchElementException {/* COMPLETE THIS METHOD */
    	if (rear == null) throw new NoSuchElementException("Empty List");
    	PartialTree ptr = rear.next.tree;
    	if(ptr == rear.tree){//rear is front or if list is a single node
    		rear = null;
    		size = 0;
    	}
    	else{
    		rear.next = rear.next.next;
    		size--;
    	}
    	return ptr;
    }

    /**
     * Removes the tree in this list that contains a given vertex.
     * 
     * @param vertex Vertex whose tree is to be removed
     * @return The tree that is removed
     * @throws NoSuchElementException If there is no matching tree
     */
    public PartialTree removeTreeContaining(Vertex vertex) 
    throws NoSuchElementException {
    	//Iterator<PartialTree> it = this.iterator();
    	if (rear == null) throw new NoSuchElementException("Empty list");//empty list
    	Node ptr = rear.next;
    	Node prev = rear;
    	PartialTree result = ptr.tree;
    	//while(it.hasNext()){
    	for(int i = 0; i<size; i++){
    		if(ptr.tree.getRoot().equals(vertex.getRoot())){
    			if(rear.tree.getRoot().equals(vertex.getRoot())){//if rear is the vertex
    	    		prev.next = rear.next;
    	    		rear = prev;
    	    		size--;
    	    		return ptr.tree;
    	    	}
    			else if (rear == rear.next){//rear is front or list is one node
    				rear = null;
    				size = 0;
    				return ptr.tree;
    			}
    			else{
    				prev.next = ptr.next;
    				size--;
    				return ptr.tree;
    			}
    		}
    		prev = ptr;
			ptr = ptr.next;
    	}
    	throw new NoSuchElementException("No matching tree"); //vertex does not exist within the list
    }
   
    /**
     * Gives the number of trees in this list
     * 
     * @return Number of trees
     */
    public int size() {
    	return size;
    }
    
    /**
     * Returns an Iterator that can be used to step through the trees in this list.
     * The iterator does NOT support remove.
     * 
     * @return Iterator for this list
     */
    public Iterator<PartialTree> iterator() {
    	return new PartialTreeListIterator(this);
    }
    
    private class PartialTreeListIterator implements Iterator<PartialTree> {
    	
    	private PartialTreeList.Node ptr;
    	private int rest;
    	
    	public PartialTreeListIterator(PartialTreeList target) {
    		rest = target.size;
    		ptr = rest > 0 ? target.rear.next : null;
    	}
    	
    	public PartialTree next() 
    	throws NoSuchElementException {
    		if (rest <= 0) {
    			throw new NoSuchElementException();
    		}
    		PartialTree ret = ptr.tree;
    		ptr = ptr.next;
    		rest--;
    		return ret;
    	}
    	
    	public boolean hasNext() {
    		return rest != 0;
    	}
    	
    	public void remove() 
    	throws UnsupportedOperationException {
    		throw new UnsupportedOperationException();
    	}
    	
    }
}

