package com.hashedin.artcollective.utils;

/**
 * Utility class to hold a pair of objects
 * 
 * @param <X> First object in the pair
 * @param <Y> Second object in the pair
 */
public final class Pair<X, Y> {

	private final X x;
	private final Y y;
	
	public Pair(X x, Y y) {
		this.x = x;
		this.y = y;
	}
	
	public X getKey() {
		return x;
	}
	
	public Y getValue() {
		return y;
	}

	@Override
	public String toString() {
		return "Pair [x=" + x + ", y=" + y + "]";
	}
}
