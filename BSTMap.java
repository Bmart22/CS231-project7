/*
Brendan Martin

Template for the BSTMap classes
4/4/2019
CS 231 Project 6
*/
import java.util.ArrayList;
import java.util.Comparator;

public class BSTMap<K, V> implements MapSet<K, V> {
	
	//Fields
	private TNode root;
	private Comparator<K> comp;
	private int size;
	
	//Constructor
	public BSTMap( Comparator<K> comp ) {
		this.root = null;
		this.comp = comp;
		this.size = 0;
	}
	
	// Adds or updates a key-value pair
	// Returns the old value or null if no old value existed
	public V put( K key, V value ) {
		// If the root has no key pair:
		if ( this.root == null ) {
			this.root = new TNode( key, value );
			this.size++;
			return value;
		}
		
		//Otherwise, start the placement search
		V val = this.root.put( key, value, comp );
		if ( val == null ) { this.size++; }
		return val;
	}
	
	// Gets the value at the specified key
	// If the key is not present, returns null
	public V get( K key ) {
		if ( this.root == null ) {
			return null;
		}
		
		return this.root.get( key, this.comp );
	}
	
	// Returns true if the map contains a key-value pair with the given key
    public boolean containsKey( K key ) {
    	if ( this.root != null ) {
    		return this.root.containsKey( key, this.comp );
    	}
    	return false;
    }
    
    // Returns an ArrayList of all the keys in the map.
    // Uses pre-order traversal
    public ArrayList<K> keySet() {
    	ArrayList<K> keys = new ArrayList<K>();
    	this.root.keySet( keys );
    	return keys;
    }

    // Returns an ArrayList of all the values in the map in the same order as keySet
    public ArrayList<V> values() {
    	ArrayList<V> vals = new ArrayList<V>();
    	this.root.values( vals );
    	return vals;
    }
    
    // Returns an ArrayList of all the key-value pairs
	// Uses pre-order traversal
    public ArrayList<KeyValuePair<K, V>> entrySet() {
    	ArrayList<KeyValuePair<K, V>> pairs = new ArrayList<KeyValuePair<K, V>>();
    	this.root.entrySet( pairs );
    	return pairs;
    } 
    
    // Returns a string representation of the map with proper indentation
    public String toString() {
    	if (this.root == null) {
    		return "root: null";
    	}
    	return "root: " + this.root.treeString( "" );
    }

    // Returns the number of key-value pairs in the map.
    public int size() {
    	return this.size;
    }
        
    // removes all mappings from this MapSet
    public void clear() {
    	this.root = null;
    	this.size = 0;
    }
	
	// Return the maximum depth of the tree
	public int getDepth() {
		if (this.root == null) {
			return 0;
		}
		return this.root.getDepth( 0 );
	}
    
	
	// Private inner class for nodes
	private class TNode {
		//Fields
		TNode left;
		TNode right;
		KeyValuePair<K, V> data;
		
		//Constructor
		public TNode( K k, V v ) {
			this.left = null;
			this.right = null;
			this.data = new KeyValuePair<K, V>( k, v );
		}
		
		// Takes in a key, a value, and a comparator and inserts the TNode
        // Returns the old value of the node, if replaced, or null if inserted
		public V put( K key, V value, Comparator<K> comp ) {
			int c = comp.compare( key, this.data.getKey() );
			
			// If this node contains the key
			if (c == 0) {
				this.data.setValue( value );
				return value;
			}
			// If the key is to the left
			if ( c < 0 ) {
				//If there is a left node
				if ( this.left != null ) {
					return this.left.put( key, value, comp );
				} else {
					this.left = new TNode( key, value );
					return null;
				}
			}
			// If the key is to the right
			if ( c > 0 ) {
				//If there is a right node
				if ( this.right != null ) {
					return this.right.put( key, value, comp );
				} else {
					this.right = new TNode( key, value );
					return null;
				}
			}
			
			return null;
		}
		
		// Takes in a key and a comparator
        // Returns the value associated with the key or null
		public V get( K key, Comparator<K> comp ) {
			int c = comp.compare( key, this.data.getKey() );
			
			// If this node contains the key
			if (c == 0) {
				return this.data.getValue();
			}
			// If the key is to the left and a left node exists
			else if ( c < 0 && this.left != null ) {
				return this.left.get( key,  comp );
			}
			// If the key is to the right and a right node exists
			else if ( c > 0 && this.right != null ) {
				return this.right.get( key, comp );
			}
			
			return null;
		}
		
		// Returns true if this subtree contains a key-value pair with the given key
		public boolean containsKey( K key, Comparator<K> comp ) {
			int c = comp.compare( key, this.data.getKey() );
			
			if ( c == 0 ) {
				return true;
			}
			if ( c < 0 && this.left != null ) {
				return this.left.containsKey( key, comp );
			}
			if ( c > 0 && this.right != null ) {
				return this.right.containsKey( key, comp );
			}
			return false;
		}
		
		// Returns an ArrayList of all the keys in the map.
		// Uses pre-order traversal
		public void keySet( ArrayList<K> keys ) {
			keys.add( this.data.getKey() );
			
			if ( this.left != null ) {
				this.left.keySet( keys );
			}
			if ( this.right != null ) {
				this.right.keySet( keys );
			}
		}
		
		// Returns an ArrayList of all the values in the map in the same order as keySet
		public void values( ArrayList<V> vals ) {
			vals.add( this.data.getValue() );
			
			if ( this.left != null ) {
				this.left.values( vals );
			}
			if ( this.right != null ) {
				this.right.values( vals );
			}
		}
		
		// Returns an ArrayList of all the key-value pairs
		// Uses pre-order traversal
		public void entrySet( ArrayList<KeyValuePair<K, V>> pairs ) {
			pairs.add( this.data );
		
			if ( this.left != null ) {
				this.left.entrySet( pairs );
			}
			if ( this.right != null ) {
				this.right.entrySet( pairs );
			}
		}
		
		// Return the maximum depth of the tree
		public int getDepth( int curDepth ) {
			curDepth++; // Increment the current depth counter
			
			// If we reached the bottom of the tree
			if ( this.left == null && this.right == null ) {
				return curDepth;
			}
			
			int lDep = 0;
			int rDep = 0;
			
			// Get the depth of each child branch
			if ( this.left != null ) {
				lDep = this.left.getDepth( curDepth );
			}
			if ( this.right != null ) {
				rDep = this.right.getDepth( curDepth );
			}
			
			// Return the larger depth
			if ( rDep > lDep ) {
				return rDep;
			}
			return lDep;
		}
		
		// Build a representation of the tree as a string
		public String treeString( String indent ) {
			String str = indent + this.data + "\n";
			if ( this.left != null ) {
				str += "left: " + this.left.treeString( indent + "    " );
			}
			if ( this.right != null ) {
				str += "right:" + this.right.treeString( indent + "    " );
			}
			return str;
		}
		
		
	} //end TNode class
	
	// test function
    public static void main( String[] argv ) {
            // create a BSTMap
            BSTMap<String, Integer> bst = new BSTMap<String, Integer>( new StringAscending() );
			
			System.out.println( bst.containsKey( "five" ) );
			System.out.println( bst.get( "eleven" ) );
			System.out.println( "" );
			
			System.out.println( "Adding twenty");
            bst.put( "twenty", 20 );
           //  System.out.println( bst.containsKey( "five" ) );
//             System.out.println( bst.containsKey( "eleven" ) );
//             System.out.println( bst.containsKey( "ten" ) );
//             System.out.println( bst.containsKey( "l" ) );
//             System.out.println( bst.containsKey( "twenty" ) );
//             System.out.println( bst.containsKey( "six" ) );
            
            System.out.println( "Adding ten");
            bst.put( "ten", 10 );
          //   System.out.println( bst.containsKey( "five" ) );
//             System.out.println( bst.containsKey( "eleven" ) );
//             System.out.println( bst.containsKey( "ten" ) );
//             System.out.println( bst.containsKey( "l" ) );
//             System.out.println( bst.containsKey( "twenty" ) );
//             System.out.println( bst.containsKey( "six" ) );
            
            System.out.println( "Adding eleven");
            bst.put( "eleven", 11 );
       //      System.out.println( bst.containsKey( "five" ) );
//             System.out.println( bst.containsKey( "eleven" ) );
//             System.out.println( bst.containsKey( "ten" ) );
//             System.out.println( bst.containsKey( "l" ) );
//             System.out.println( bst.containsKey( "twenty" ) );
//             System.out.println( bst.containsKey( "six" ) );
//             
            System.out.println( "Adding five");
            bst.put( "five", 5 );
           //  System.out.println( bst.containsKey( "five" ) );
//             System.out.println( bst.containsKey( "eleven" ) );
//             System.out.println( bst.containsKey( "ten" ) );
//             System.out.println( bst.containsKey( "l" ) );
//             System.out.println( bst.containsKey( "twenty" ) );
//             System.out.println( bst.containsKey( "six" ) );
            
            System.out.println( "Adding six");
            bst.put( "six", 6 );
         //    System.out.println( bst.containsKey( "five" ) );
//             System.out.println( bst.containsKey( "eleven" ) );
//             System.out.println( bst.containsKey( "ten" ) );
//             System.out.println( bst.containsKey( "l" ) );
//             System.out.println( bst.containsKey( "twenty" ) );
//             System.out.println( bst.containsKey( "six" ) );
            
            System.out.println( bst );
            System.out.println( bst.getDepth() );
            System.out.println( "" );

            System.out.println( bst.get( "eleven" ) );
            System.out.println( bst.get( "ten" ) );
            System.out.println( bst.get( "twenty" ) );
            System.out.println( bst.get( "six" ) );
            System.out.println( bst.get( "five" ) );
            System.out.println( bst.size() );
            System.out.println( "" );
            
            System.out.println( "Overwrite" );
            bst.put( "twenty", 1 );
            bst.put( "ten", 1 );
            bst.put( "eleven", 1 );
            bst.put( "five", 1 );
            bst.put( "six", 1 );
            bst.put( "cah", 2 );
            bst.put( "wah", 3 );
            bst.put( "fuh", 4 );
            bst.put( "coh", 2);
            bst.put("cuh",3);
            bst.put("coo",2);
            System.out.println( bst );
            System.out.println( bst.getDepth() );
            System.out.println( "" );
            
            System.out.println( bst.containsKey( "five" ) );
            System.out.println( bst.containsKey( "eleven" ) );
            System.out.println( bst.containsKey( "ten" ) );
            System.out.println( bst.containsKey( "l" ) );
            System.out.println( bst.containsKey( "twenty" ) );
            System.out.println( bst.containsKey( "six" ) );
            
        	System.out.println( bst.containsKey( "gfds" ) );
        	
        	System.out.println( bst.keySet( ) );
        	System.out.println( bst.values( ) );
        	System.out.println( bst.entrySet( ) );

    }
	
}
