/*
 * Name: KeyValuePair.java
 * Author: Brendan Martin
 * Date: 4/2/2019
 */

public class KeyValuePair<Key,Value> {
	
	//Fields
	private Key k;
	private Value v;
	
	//Constructor
	public KeyValuePair( Key k, Value v) {
		this.k = k;
		this.v = v;
	}
	
	//Returns the key
	public Key getKey() {
		return this.k;
	}
	
	//Returns the value
	public Value getValue() {
		return this.v;
	}
	
	//Changes the value
	public void setValue( Value v ) {
		this.v = v;
	}
	
	//Returns a representation of the pair as a string (key, value)
	public String toString() {
		return "(" + this.k + ", " + this.v + ")";
	}
	
	public static void main( String[] args ) {
		KeyValuePair<Integer, String> pair = new KeyValuePair<Integer, String>( 2, "2" );
		System.out.println( pair.getKey() );
		System.out.println( pair.getValue() );
		System.out.println( pair );
		
		pair.setValue( "3" );
		System.out.println( pair.getKey() );
		System.out.println( pair.getValue() );
		System.out.println( pair );
		
		System.out.println( "" );
		
		KeyValuePair<String, Integer> pair2 = new KeyValuePair<String, Integer>( "3", 3 );
		System.out.println( pair2.getKey() );
		System.out.println( pair2.getValue() );
		System.out.println( pair2 );
		
		pair2.setValue( 4 );
		System.out.println( pair2.getKey() );
		System.out.println( pair2.getValue() );
		System.out.println( pair2 );
	}
	
}