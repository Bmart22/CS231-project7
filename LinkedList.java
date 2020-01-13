/*
 * File: LinkedList.java
 * Author: Brendan Martin
 * Date: 4/22/2019
 */
 
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class LinkedList<T> implements Iterable<T> {
	
	//Fields
	private Node head;
	private int size;
	
	//Constructor
	public LinkedList() {
		this.head = null;
		this.size = 0;
	}
	
	//Set the head to null, size to zero
	public void clear() {
		this.head = null;
		this.size = 0;
	}
	
	//Return the number of elements in the list
	public int size() {
		return this.size;
	}
	
	//Add a node to the beginning of the list
	public void addFirst( T item ) {
		Node newNode = new Node( item );
		newNode.setNext( this.head );
		this.head = newNode;
		this.size++;
	}
	
	//Add a node to the end of the list
	public void addLast( T item ) {
		//If the list is empty, add the node to the beginning of the list
		if (this.size == 0) {
			this.addFirst( item );
			return;
		}
		
		//Find the end of the list
		Node curPlace = this.head;
		while (curPlace.getNext() != null) {
			curPlace = curPlace.getNext();
		}
		
		//Make the node and add it
		Node newNode = new Node( item );
		newNode.setNext( null );
		curPlace.setNext( newNode );
		this.size++;
	}
	
	//Add a node at the specified index
	public void add(int index, T item) {
		//If the list is empty, add the node to the beginning of the list
		if (this.size == 0 || index == 0) {
			this.addFirst( item );
			return;
		} else if ( index >= this.size ) {
			this.addLast( item );
			return;
		}
		
		//Find the specified location
		Node curPlace = this.head;
		for (int i = 0; i < (index - 1); i++) {
			curPlace = curPlace.getNext();
		}
		
		//Make the node, add it
		Node newNode = new Node( item );
		newNode.setNext( curPlace.getNext() );
		curPlace.setNext( newNode );
		this.size++;
	}
	
	//Remove the node at the specified index
	public T remove (int index) {
		//If the list is empty
		if (this.size == 0) {
			return null;
		} else if (index >= this.size) { //If the index is out of bounds
			return null;
		}
		//If you are removing the first element of the list
		if ( index == 0 ) {
			T answer = this.head.getThing();
			this.head = this.head.getNext();
			this.size--;
			return answer;
		}
		
		//Find the specified location
		Node curPlace = this.head;
		for (int i = 0; i < (index - 1); i++) {
			curPlace = curPlace.getNext();
		}
		
		//Get the contents of the node, remove the node
		T answer = curPlace.getNext().getThing();
		Node thing = curPlace.getNext().getNext();
		curPlace.setNext( thing );
		this.size--;
		return answer;
	}
	
	// Get the value at the specified location
	public T get( int index ) {
		//If the list is empty
		if (this.size == 0) {
			return null;
		} else if (index >= this.size) { //If the index is out of bounds
			return null;
		}
		//Find the specified location
		Node curPlace = this.head;
		for (int i = 0; i < index; i++) {
			curPlace = curPlace.getNext();
		}
		
		return curPlace.getThing();
	}
	
	// Project 7
	// Returns -1 if the key is not found, or the index if found
	public int containsKey( String key ) {
		// Check if the list is empty
		if (this.size == 0) {
			return -1;
		}
	
		//Find the specified location
		Node curPlace = this.head;
		KeyValuePair<String,Integer> pair = (KeyValuePair<String,Integer>)(curPlace.getThing());
		if ( pair.getKey().compareTo( key ) == 0 ) {
			return 0;
		}
		for (int i = 1; i < (this.size); i++) {
			curPlace = curPlace.getNext();
			pair = (KeyValuePair<String,Integer>)(curPlace.getThing());
			if ( pair.getKey().compareTo( key ) == 0 ) {
				return i;
			}
		}
		
		return -1;
	}
	
	//Return the contents of the linked list as an ArrayList
	public ArrayList<T> toArrayList() {
		ArrayList list = new ArrayList();
		for (T thing: this) {
			list.add( thing );
		}
		return list;
	}
	
	//Return the contents of the linked list shuffled in an ArrayList
	public ArrayList<T> toShuffledList() {
		Random rand = new Random();
		ArrayList list = this.toArrayList();
		
		//Shuffle algorithm from Project 1
		for (int i = list.size(); i > 0; i--) {
			list.add( list.remove( rand.nextInt(i) ) );
		}
		return list;
	}
	
	// Print the linked list neatly
	// public String toString() {
// 		String str = "[";
// 		Node curNode = this.head;
// 		str += (String)curNode.getThing().toString();
// 		
// 		for ( int i = 1; i < this.size; i++ ) {
// 			curNode.getNext();
// 			str += ", " + (String)curNode.getThing().toString();
// 		}
// 		
// 		return str + "]\n";
// 	}
	
	//Return iterator object
	public Iterator<T> iterator() {
		return new LLIterator(this.head);
	}

	//Node subclass
	private class Node {
	
		//Fields
		private Node next;
		private T value;
		
		//Constructor
		public Node( T item ) {
			this.value = item;
			this.next = null;
		}
		
		//Return the value of this node
		public T getThing() {
			return this.value;
		}
		
		//Store a pointer to the next node in the chain
		public void setNext( Node n ) {
			this.next = n;
		}
		
		//Get the pointer to the next node in the chain
		public Node getNext() {
			return this.next;
		}
	}
	
	//Iterator subclass
	private class LLIterator implements Iterator<T> {
		//Fields
		private Node next;
		
		//Constructor
		public LLIterator(Node head) {
			this.next = head;
		}
		
		//Return true if there is another node after this one, false if  not
		public boolean hasNext() {
			return this.next != null;
		}
		
		//Return the value of the next node in the list
		public T next() {
			T nextValue = this.next.getThing();
			this.next = this.next.getNext();
			return nextValue;
		}
	}
}