/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.format;

import java.util.Comparator;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.unit.TreeTable;

/**
 * Defines a contiguous range of tokens by the indexes of the real token end-points. A beginning
 * real token index of {@code -1} represents a virtual BOF real token. The real EOF token has an
 * actual token index.
 */
public class Region implements Comparator<Region>, Comparable<Region> {

	private static final TreeTable<Integer, Integer, Region> POOL = new TreeTable<>();

	public final int min;
	public final int max;

	public static Region key(AdeptToken a, AdeptToken b) {
		int idx = a == null ? -1 : a.getTokenIndex();
		return key(idx, b.getTokenIndex());
	}

	public static Region key(int idx1, int idx2) {
		Region region = POOL.get(idx1, idx1);
		if (region != null) return region;
		return new Region(idx1, idx2);
	}

	public static Region key(TextEdit edit) {
		return key(edit.begIndex(), edit.endIndex());
	}

	public static Region merge(Region lower, Region upper) {
		int min = Math.min(lower.min, upper.min);
		int max = Math.max(lower.max, upper.max);
		return key(min, max);
	}

	private Region(int idx1, int idx2) {
		min = idx1;
		max = idx2;
		POOL.put(idx1, idx2, this);
	}

	public Region larger(Region lower, Region upper) {
		return (upper.max - upper.min) > (lower.max - lower.min) ? upper : lower;
	}

	public Region smaller(Region lower, Region upper) {
		return (upper.max - upper.min) < (lower.max - lower.min) ? upper : lower;
	}

	public boolean overlaps(Region region) {
		if (max > region.min && max < region.max) return true;
		if (min > region.min && min < region.max) return true;
		return false;
	}

	public boolean contains(Region region) {
		return contains(region.min) && contains(region.max) ? true : false;
	}

	public boolean contains(int val) {
		return val >= min && val <= max ? true : false;
	}

	/** Range width of the region including end-points. */
	public int range() {
		return Math.max(0, max - min + 1);
	}

	public boolean adjacent(Region region) {
		return region.min == max || region.max == min;
	}

	@Override
	public int compareTo(Region o) {
		return compare(this, o);
	}

	@Override
	public int compare(Region o1, Region o2) {
		if (o1.min == o2.min && o1.max == o2.max) return 0;
		if (o1.max <= o2.min) return -1;
		if (o1.min >= o2.max) return 1;
		throw new RegionException(o1, o2, "Overlapping");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + max;
		result = prime * result + min;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Region other = (Region) obj;
		if (max != other.max) return false;
		if (min != other.min) return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("{%s:%s}", min, max);
	}
}
