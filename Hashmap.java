/*
 * Name: Hashmap.java
 * Author: Brendan Martin
 * Date: 4/9/2019
 */
 
// Imports
import java.util.ArrayList;
import java.util.Comparator;


public class Hashmap<K,V> implements MapSet<K,V> {
	//Fields
	private int size;
	private int spotsFilled;
	private int numCollisions;
	private Object[] array;
	private Comparator<K> comp;
	
	//Constructor
	public Hashmap( int size, Comparator<K> comp ) {
		this.size = size;
		this.spotsFilled = 0;
		this.numCollisions = 0;
		this.array = new Object[size];
		this.comp = comp;
	}
	
	// Generate the index for a provided key
	private int hash( K key ) {
		return Math.abs(key.hashCode()) % this.size;
	}
	
	// Add a key-value pair to the hash table
	public V put( K key, V value ) {
		// If the table is more than 50% full, double the size of the table
		if ( this.spotsFilled > (this.size/2) ) {
			this.expand();
		}
	
		// Hash the index
		int index = this.hash( key );
		
		// If there is nothing at this spot, make a new binary search tree
		if ( this.array[index] == null ) {
			this.array[index] = new BSTMap<K,V>( this.comp );
			this.spotsFilled++;
			return ((BSTMap<K,V>)this.array[index]).put( key, value );
		}
		
		// If there is a tree, add the key-value pair
		V val = ((BSTMap<K,V>)this.array[index]).put( key, value );
		
		// If no value was returned, then a new key was added, so a collision took place
		if ( val == null ) {
			this.numCollisions++;
			this.spotsFilled++;
		}
		return val;
		
	}
	
	// Double the size of the array, reassign the elements in the array
	private void expand() {
		// Store old array, make new bigger array
		Object[] oldArray = this.array;
		this.array = new Object[this.size * 2];
		
		//Reset the counter for collisions and spots filled
		//Update the available size parameter
		this.numCollisions = 0;
		this.spotsFilled = 0;
		this.size = this.size * 2;
		
		// For each element of the array, get a list of all the keys and values
		// In that binary search tree. Put the values in the new array
		ArrayList<K> keys;
		ArrayList<V> vals;
		BSTMap<K,V> map;
		for ( Object tree: oldArray ) {
			// If there is no tree here, skip
			if ( tree != null ) {
				map = (BSTMap<K,V>)tree;
				keys = map.keySet();
				vals = map.values();
				for ( int i = 0; i < keys.size(); i++ ) {
					this.put( keys.get(i), vals.get(i) );
				}
			}
		}
	}
	
	// Returns true if the map contains a key-value pair with the given key
    public boolean containsKey( K key ) {
    	// Get the index for this key
		int index = this.hash( key );
		BSTMap<K,V> map = (BSTMap<K,V>)(this.array[index]);
    	
    	// If there is a tree at this index
    	if ( map != null ) {
			// Search that tree for the key
			if ( map.containsKey( key ) ) {
				return true;
			}
    	}
    	return false;
    }
    
    // Returns the value associated with the given key.
    // If that key is not in the map, then it returns null.
    public V get( K key ) {
    	// Get the index for this key
		int index = this.hash( key );
		BSTMap<K,V> map = (BSTMap<K,V>)(this.array[index]);
    	
    	// If there is a tree at this index
    	if ( map != null ) {
			// If the tree contains the key, return the corresponding value
			if ( map.containsKey( key ) ) {
				return map.get( key );
			}
    	}
    	return null;
    }
    
    // Returns an ArrayList of all the keys in the map. There is no
    // defined order for the keys.
    public ArrayList<K> keySet() {
    	ArrayList<K> list = new ArrayList<K>();
    	BSTMap<K,V> map;
    	// Check each spot in array
    	for ( Object tree: this.array ) {
    		map = (BSTMap<K,V>)tree;
    		// If a tree is there
    		if ( map != null ) {
    			list.addAll( map.keySet() );
    		}
    	}
    	return list;
    }
    
    // Returns an ArrayList of all the values in the map. These should
    // be in the same order as the keySet.
    public ArrayList<V> values() {
    	ArrayList<V> list = new ArrayList<V>();
    	BSTMap<K,V> map;
    	// Check each spot in array
    	for ( Object tree: this.array ) {
    		map = (BSTMap<K,V>)tree;
    		// If a tree is there
    		if ( map != null ) {
    			list.addAll( map.values() );
    		}
    	}
    	return list;
    }
    
    // return an ArrayList of pairs.
    // For the sake of the word-counting project, the pairs should
    // be added to the list by a pre-order traversal.
    public ArrayList<KeyValuePair<K,V>> entrySet() {
    	ArrayList<KeyValuePair<K,V>> list = new ArrayList<KeyValuePair<K,V>>();
    	BSTMap<K,V> map;
    	// Check each spot in array
    	for ( Object tree: this.array ) {
    		map = (BSTMap<K,V>)tree;
    		// If a tree is there
    		if ( map != null ) {
    			list.addAll( map.entrySet() );
    		}
    	}
    	return list;
    }
    
    // Returns the number of key-value pairs in the map.
    public int size() {
    	return this.spotsFilled;
    }
    
    // removes all mappings from this MapSet
    public void clear() {
		this.spotsFilled = 0;
		this.numCollisions = 0;
		this.array = new Object[this.size];
    }
	
	// Return the number of collisions
	public int getCollisions() {
		return this.numCollisions;
	}
	
	// print the hashmap neatly
	public String toString() {
		BSTMap<K,V> map;
		String str = "";
		for ( Object tree: this.array ) {
			if ( tree == null ) {
				str += "null\n";
			} else {
				map = (BSTMap<K,V>)tree;
				str += map.toString();
			}
		}
		return str;
	}
	
	// Testing function
	public static void main( String[] args ) {
		Hashmap<String, Integer> hash = new Hashmap<String, Integer>( 5, new StringAscending() );
		
		hash.put( "w", 2 );
		hash.put( "yes", 3 );
		hash.put("no", 4);
		hash.put( "w", 1);
		hash.put( "w", 5);
		hash.put( "cool", 6);
		hash.put( "nah", 11);
		
		System.out.println( hash );
		System.out.println( "" );
		System.out.println( "size: " + hash.size() );
		System.out.println( "collision: " + hash.getCollisions() );
		System.out.println( "contains what: " + hash.containsKey( "what" ) );
		System.out.println( "contains cool: " + hash.containsKey( "cool" ) );
		System.out.println( "what: " + hash.get( "what"));
		System.out.println( "cool: " + hash.get( "cool" ) );
		System.out.println( "" );
		
		System.out.println( hash.keySet() );
		System.out.println( hash.values() );
		System.out.println( hash.entrySet() );
		
		hash.clear();
		System.out.println( "" );
		System.out.println( "cleared" );
		System.out.println( "" );
		
		hash.put( "nah", 11);
		hash.put( "co", 6);
		hash.put("no", 4);
		hash.put( "w", 1);
		hash.put( "what", 5);
		hash.put( "w", 2 );
		hash.put( "yes", 3 );
		
		
		System.out.println( hash );
		System.out.println( "" );
		System.out.println( "size: " + hash.size() );
		System.out.println( "collision: " + hash.getCollisions() );
		System.out.println( "contains what: " + hash.containsKey( "what" ) );
		System.out.println( "contains cool: " + hash.containsKey( "cool" ) );
		System.out.println( "what: " + hash.get( "what"));
		System.out.println( "cool: " + hash.get( "cool" ) );
		System.out.println( "" );
		
		System.out.println( hash.keySet() );
		System.out.println( hash.values() );
		System.out.println( hash.entrySet() );
		
		
	}
	
}