package finalproject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
public class MyHashTable<K,V> implements Iterable<MyPair<K,V>>{

	// num of entries to the table
	private int size;
	// num of buckets 
	private int capacity = 16;
	// load factor needed to check for rehashing 
	private static final double MAX_LOAD_FACTOR = 0.75;
	// ArrayList of buckets. Each bucket is a LinkedList of HashPair
	private ArrayList<LinkedList<MyPair<K,V>>> buckets; 


	// constructors
	public MyHashTable() {
		// initialize buckets with empty LinkedLists
		this.buckets = new ArrayList<>(capacity);
		for(int i=0; i<capacity; i++){
			buckets.add(new LinkedList<MyPair<K, V>>());
		}
	}
	public MyHashTable(int initialCapacity) {
		// initialize buckets with empty LinkedLists and update capacity
		this.capacity = initialCapacity;
		this.buckets = new ArrayList<>(capacity);
		for(int i=0; i<capacity; i++){
			buckets.add(new LinkedList<MyPair<K, V>>());
		}
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public int numBuckets() {
		return this.capacity;
	}

	/**
	 * Returns the buckets variable. Useful for testing  purposes.
	 */
	public ArrayList<LinkedList< MyPair<K,V> > > getBuckets(){
		return this.buckets;
	}

	/**
	 * Given a key, return the bucket position for the key. 
	 */
	public int hashFunction(K key) {
		int hashValue = Math.abs(key.hashCode())%this.capacity;
		return hashValue;
	}

	/**
	 * Takes a key and a value as input and adds the corresponding HashPair
	 * to this HashTable. Expected average run time  O(1)
	 */
	public V put(K key, V value) {
		int index = hashFunction(key);
		LinkedList<MyPair<K, V>> bucket = buckets.get(index);

		// check if key already exists in the bucket, update, and return the old one
		for (MyPair<K, V> pair : bucket) {
			if (pair.getKey().equals(key)) {
				V oldValue = pair.getValue();
				pair.setValue(value);
				return oldValue;
			}
		}

		// add new pair to bucket and update size
		bucket.add(new MyPair<>(key, value));
		size++;

		// check if rehashing is needed
		if ((double) size / capacity > MAX_LOAD_FACTOR) {
			rehash();
		}
		return null;
	}


	/**
	 * Get the value corresponding to key. Expected average runtime O(1)
	 */

	public V get(K key) {
		int index = hashFunction(key);
		LinkedList<MyPair<K, V>> bucket = buckets.get(index);

		// check if key exists in the bucket -> return value otherwise null
		for (MyPair<K, V> pair : bucket) {
			if (pair.getKey().equals(key)) {
				return pair.getValue();
			}
		}
		return null;
	}

	/**
	 * Remove the HashPair corresponding to key . Expected average runtime O(1) 
	 */
	public V remove(K key){
		int index = hashFunction(key);
		LinkedList<MyPair<K,V>> bucket = buckets.get(index);

		// check if the key exists
		Iterator<MyPair<K, V>> iterator = bucket.iterator();
		while(iterator.hasNext()){
			MyPair<K,V> pair = iterator.next();
			if(pair.getKey().equals(key)){
				iterator.remove();
				size--;
				return pair.getValue();
			}
		}
		return null;
	}

	/** 
	 * Method to double the size of the hashtable if load factor increases
	 * beyond MAX_LOAD_FACTOR.
	 * Made public for ease of testing.
	 * Expected average runtime is O(m), where m is the number of buckets
	 */
	public void rehash() {
		// create new bucket list with double the capacity
		ArrayList<LinkedList<MyPair<K, V>>> newBuckets = new ArrayList<>(capacity * 2);
		for (int i = 0; i < capacity * 2; i++) {
			newBuckets.add(new LinkedList<MyPair<K, V>>());
		}

		// rehash entries from old buckets into new buckets
		for (LinkedList<MyPair<K, V>> bucket : buckets) {
			for (MyPair<K, V> pair : bucket) {
				int index = Math.abs(pair.getKey().hashCode()) % (capacity * 2);
				newBuckets.get(index).add(pair);
			}
		}

		// update buckets and capacity
		this.buckets = newBuckets;
		this.capacity *= 2;
	}


	/**
	 * Return a list of all the keys present in this hashtable.
	 * Expected average runtime is O(m), where m is the number of buckets
	 */

	public ArrayList<K> getKeySet() {
		ArrayList<K> keys = new ArrayList<>(size);

		// iterate over all pairs and add keys to list
		for (LinkedList<MyPair<K, V>> bucket : buckets) {
			for (MyPair<K, V> pair : bucket) {
				keys.add(pair.getKey());
			}
		}

		return keys;
	}

	/**
	 * Returns an ArrayList of unique values present in this hashtable.
	 * Expected average runtime is O(m) where m is the number of buckets
	 */
	public ArrayList<V> getValueSet() {
		ArrayList<V> values = new ArrayList<>(size);

		// iterate over all pairs and add unique values to list
		for (LinkedList<MyPair<K, V>> bucket : buckets) {
			for (MyPair<K, V> pair : bucket) {
				// check if value is unique before adding to list
				boolean isUnique = true;
				for (V value : values) {
					if (value.equals(pair.getValue())) {
						isUnique = false;
						break;
					}
				}
				if (isUnique) {
					values.add(pair.getValue());
				}
			}
		}

		return values;
	}

	/**
	 * Returns an ArrayList of all the key-value pairs present in this hashtable.
	 * Expected average runtime is O(m) where m is the number of buckets
	 */
	public ArrayList<MyPair<K, V>> getEntries() {
		ArrayList<MyPair<K, V>> entries = new ArrayList<>(size);

		// iterate over all pairs and add to list
		for (LinkedList<MyPair<K, V>> bucket : buckets) {
			for (MyPair<K, V> pair : bucket) {
				entries.add(pair);
			}
		}

		return entries;
	}

	@Override
	public MyHashIterator iterator() {
		return new MyHashIterator();
	}
	private class MyHashIterator implements Iterator<MyPair<K,V>> {

		private int bucketIndex;
		private Iterator<MyPair<K,V>> listIterator;

		public MyHashIterator() {
			this.bucketIndex = -1;
			this.listIterator = null;
		}

		@Override
		public boolean hasNext() {
			if (listIterator != null && listIterator.hasNext()) {
				return true;
			}
			for (int i = bucketIndex + 1; i < capacity; i++) {
				if (!buckets.get(i).isEmpty()) {
					return true;
				}
			}
			return false;
		}

		@Override
		public MyPair<K,V> next() {
			if (listIterator != null && listIterator.hasNext()) {
				return listIterator.next();
			}
			do {
				bucketIndex++;
				listIterator = buckets.get(bucketIndex).iterator();
			} while (listIterator.hasNext() == false);
			return listIterator.next();
		}
	}
}
