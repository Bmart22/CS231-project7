/*
 * Name: WordCounter.java
 * Author: Brendan Martin
 * Date: 4/16/2019
 */

// Imports
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;


public class WordCounter {
	//Fields
	private int totalWords;
	private MapSet<String, Integer> map;
	
	//Constructor; input True for BSTMap, False for Hashmap
	public WordCounter( boolean structType ) {
		this.totalWords = 0;
		
		if ( structType ) {
			this.map = new BSTMap<String, Integer>( new StringAscending() );
		} else {
			this.map = new HashmapList<String, Integer>( 200000, new StringAscending() );
		}
	}
	
	// Process the contents of a word file, update the map
	public void analyze( String filename ) {
		try {
			FileReader reader = new FileReader(filename);
			BufferedReader buffRead = new BufferedReader(reader);
			
			String line = buffRead.readLine();
			while ( line != null ) {
				// Break the line into a list of words
				String[] words = line.split("[^a-zA-Z0-9']");
				
				// Process each word in the list
				for (int i = 0; i < words.length; i++) {
					String word = words[i].trim().toLowerCase();
					
					// Exclude words with no length
					if ( word.length() > 0 ) {
						// Update the tree with this instance of the word
						if ( this.map.containsKey( word ) ) {
							int num = this.map.get( word ) + 1;
							this.map.put( word, num );
						}
						// If this is the first instance of the word:
						else {
							this.map.put( word, 1 );
						}
						// Increment the total words
						this.totalWords++;
					}
					
				} // end for loop
				line = buffRead.readLine();
			} // end while loop
			
			buffRead.close();
		}
		catch(FileNotFoundException ex) {
		  System.out.println("unable to open file " + filename );
		}
		catch(IOException ex) {
		  System.out.println("error reading file " + filename);
		}
	}
	
	// Return the total number of words in the text document
	public int getTotalWordCount() {
		return this.totalWords;
	}
	
	// Return the number of times a specific word appears in the document
	public int getCount( String word ) {
		return this.map.get( word );
	}
	
	// Return the frequency of a word presence in the document
	public double getFrequency( String word ) {
		return (double)(this.getCount( word )) / this.totalWords;
	}
	
	// Print the map
	public String toString() {
		return this.map.toString();
	}
	
	// Write the contents of the map to a word file
	public void writeWordCountFile( String filename ) {
		try {
			FileWriter file = new FileWriter( filename );
			file.write( "Total Word Count : " + this.getTotalWordCount() + "\n" );
			ArrayList<KeyValuePair<String, Integer>> entries = this.map.entrySet();
			for ( KeyValuePair<String, Integer> pair: entries ) {
				file.write( pair.getKey() + " " + pair.getValue() + "\n");
			}
			file.close();
		}
		catch(FileNotFoundException ex) {
		  System.out.println("unable to open file " + filename );
		}
		catch(IOException ex) {
		  System.out.println("error reading file " + filename);
		}
	}
	
	// Reconstruct a tree from the contents of the text file
	public void readWordCountFile( String filename ) {
		try {
			// Clear the existing map before reconstructing the new map
			this.clear();
		
			FileReader reader = new FileReader(filename);
			BufferedReader buffRead = new BufferedReader(reader);
		
			// Get the total number of words from the first line
			String line = buffRead.readLine();
			String[] words = line.split(":");
			this.totalWords = Integer.parseInt( words[1].trim() );
		
			line = buffRead.readLine();
			while ( line != null ) {
				words = line.split(" ");
				this.map.put( words[0], Integer.parseInt( words[1].trim() ) );
			
				line = buffRead.readLine();
			}
		}
		catch(FileNotFoundException ex) {
		  System.out.println("unable to open file " + filename );
		}
		catch(IOException ex) {
		  System.out.println("error reading file " + filename);
		}
		
	}
	
	// Get the number of unique words in the file (the size of the map)
	public int getUniqueWords() {
		return this.map.size();
	}
	
	// Calculate the average of the times, dropping the lowest and highest
	public double average( double[] times ) {
		int shortIndex = 0;
		double shortest = times[0];
		int longIndex = 0;
		double longest = times[0];
		
		// Identify the longest and shortest times
		for (int j = 0; j < 5; j++) {
			if ( shortest > times[j] ) {
				shortest = times[j];
				shortIndex = j;
			}
			if ( longest < times[j] ) {
				longest = times[j];
				longIndex = j;
			}
		}
		// Remove the longest and shortest times
		times[shortIndex] = 0;
		times[longIndex] = 0;
		
		// Calculate the average time
		double averageTime = 0;
		for ( double j: times ) {
			averageTime += j;
		}
		return averageTime /= 3;
	}
	
	// Get the maximum depth of the tree (BSTMap only)
	public int getTreeDepth() {
		return ((BSTMap)this.map).getDepth();
	}
	
	// Get the number of collisions (Hashmap only)
	public int getCollisions() {
		return ((HashmapList)this.map).getCollisions();
	}
	
	// Erase the existing map
	public void clear() {
		this.totalWords = 0;
		this.map.clear();
	}
	
	
	// Test function
	public static void main( String[] args ) {
		// Use true for BSTMap, false for Hashmap
		WordCounter counter = new WordCounter( false );
		
		
		// BSTMap data headings
		//System.out.println( "year, average time, unique words, total words, tree depth" );
		// Hashmap data headings
		System.out.println( "year, average time, collisions, unique words, total words" );
		
		long beginTime;
		String[] words;
		String fileYear;
		double[] times;
		
		// Analyze each file provided at the command line
		for (int i = 0; i < args.length; i++) {
			times = new double[5];
			
			// Analyze the file five times, save the processing time
			for (int j = 0; j < 5; j++) {
				counter.clear();
				beginTime = System.currentTimeMillis();
				counter.analyze( args[i] );
				times[j] = (System.currentTimeMillis() - beginTime) / 1000.0;	
			}
			
			//Get the average of the processing times
			double averageTime = counter.average( times );
			
			// Pull out the year from the name of the original text file
			words = args[i].split("[_.]");
			fileYear = words[2];
			
			// Either the BSTMap or the Hashmap print statements must be commented out
			
			// Print BSTMap Data
		// 	System.out.print( fileYear );
// 			System.out.print( "," + averageTime );
// 			System.out.print( "," + counter.getUniqueWords() );
// 			System.out.print( "," + counter.getTotalWordCount() );
// 			System.out.println( "," + counter.getTreeDepth() );
			
			// Print Hashmap Data
			System.out.print( fileYear );
			System.out.print( "," + averageTime );
			System.out.print( "," + counter.getCollisions() );
			System.out.print( "," + counter.getUniqueWords() );
			System.out.println( "," + counter.getTotalWordCount() );
			
		} // End loop over reddit files
		
	} // end main
	
} // end class