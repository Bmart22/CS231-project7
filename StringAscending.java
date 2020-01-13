/*
 * Name: AscendingString.java
 * Author: Brendan Martin
 * Date: 4/4/2019
 */

import java.util.Comparator;

// Comparator class
// Returns negative if first value is less than second value
class StringAscending implements Comparator<String> {
	public int compare( String a, String b ) {
		return a.compareTo(b);
	}
}