package hr.fer.zemris.java.hw04.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The class SimpleHashtable is an implementation of a table which uses hash to
 * organize it's elements.
 *
 * @author Karlo Bencic
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * The class TableEntry represents one entry in the hash table.
	 *
	 * @author Karlo Bencic
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 */
	public static class TableEntry<K, V> {
		/** The key. */
		private final K key;

		/** The value. */
		private V value;

		/** The next. */
		private TableEntry<K, V> next;

		/**
		 * Instantiates a new table entry.
		 *
		 * @param key
		 *            the key
		 * @param value
		 *            the value
		 * @param next
		 *            the next
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Gets the key.
		 *
		 * @return the key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets the value.
		 *
		 * @param value
		 *            the new value
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return key + "=" + value;
		}
	}

	/**
	 * The class IteratorImpl represents the iterator implementation for the
	 * {@code SimpleHashtable} class.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/** The index. */
		private int index = table.length;

		/** The entry. */
		private TableEntry<K, V> entry;

		/** The last returned. */
		private TableEntry<K, V> lastReturned;

		/** The expected modification count. */
		private int expectedModCount = modificationCount;

		/**
		 * Checks for next entry.
		 *
		 * @return true, if successful
		 */
		public boolean hasNext() {
			while (entry == null && index > 0) {
				entry = table[--index];
			}

			return entry != null;
		}

		/**
		 * Gets the next entry.
		 *
		 * @return the next table entry
		 */
		public SimpleHashtable.TableEntry<K, V> next() {
			if (modificationCount != expectedModCount) {
				throw new ConcurrentModificationException();
			}
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			TableEntry<K, V> e = lastReturned = entry;
			entry = e.next;
			return e;
		}

		/**
		 * Removes the last returned entry from the table.
		 */
		public void remove() {
			if (modificationCount != expectedModCount) {
				throw new ConcurrentModificationException();
			}
			if (lastReturned == null) {
				throw new IllegalStateException();
			}

			int hash = getHash(lastReturned.key);
			TableEntry<K, V> e = table[hash];
			for (TableEntry<K, V> prev = null; e != null; prev = e, e = e.next) {
				if (e == lastReturned) {
					modificationCount++;
					expectedModCount++;

					if (prev != null) {
						prev.next = e.next;
					} else {
						table[hash] = e.next;
					}

					size--;
					lastReturned = null;
					return;
				}
			}
		}
	}

	/** The Constant DEFAULT_SIZE. */
	private static final int DEFAULT_SIZE = 16;

	/** The Constant LOAD_FACTOR. */
	private static final float LOAD_FACTOR = 0.75f;

	/** The table. */
	private TableEntry<K, V>[] table;

	/** The size. */
	private int size;

	/** The threshold. */
	private int threshold;

	/** The modification count. */
	private int modificationCount;

	/**
	 * Instantiates a new simple hashtable. Default capacity of the internal
	 * data array is 16.
	 */
	public SimpleHashtable() {
		this(DEFAULT_SIZE);
	}

	/**
	 * Instantiates a new simple hashtable with a given capacity. The capacity
	 * should be a number that is the power of 2, if the provided number is not
	 * the power of 2, it will be calculated as the next closest power of 2
	 * number and used instead.
	 *
	 * @param initialCapacity
	 *            the initial capacity
	 * @throws IllegalArgumentException
	 *             if capacity is less than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Capacity can't be negative.");
		}

		initialCapacity = nextPowerOfTwo(initialCapacity);
		table = (TableEntry<K, V>[]) new TableEntry[initialCapacity];
		setThreshold(initialCapacity);
	}

	/**
	 * Gets the next power of two for the given number.
	 *
	 * @param num
	 *            the number
	 * @return the next power of two
	 */
	private static int nextPowerOfTwo(int num) {
		return (int) Math.pow(2, Math.ceil(Math.log10(num) / Math.log10(2)));
	}

	/**
	 * Gets the hash.
	 *
	 * @param key
	 *            the key
	 * @return the hash
	 */
	private int getHash(Object key) {
		return Math.abs(key.hashCode()) % table.length;
	}

	/**
	 * Puts the element in the table with the given key and value. If the value
	 * with the given key already exists in the table, the old value is replaced
	 * with the new value.
	 *
	 * @param key
	 *            the key, not null
	 * @param value
	 *            the value
	 * @throws IllegalArgumentException
	 *             if key is null
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Key can't be null.");
		}

		int hash = getHash(key);

		for (TableEntry<K, V> entry = table[hash]; entry != null; entry = entry.next) {
			if (entry.key.equals(key)) {
				entry.value = value;
				return;
			}
		}

		addEntry(key, value);
	}

	/**
	 * Adds a new entry into the table. If the table size exceeds 75% of it's
	 * capacity, the table is rehashed.
	 * 
	 * @param hash
	 *            the key hash
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	private void addEntry(K key, V value) {
		modificationCount++;

		if (size >= threshold) {
			rehash();
		}

		int hash = getHash(key);
		TableEntry<K, V> entry = table[hash];
		table[hash] = new TableEntry<>(key, value, entry);
		size++;
	}

	/**
	 * Gets the element from the table with the given key.
	 *
	 * @param key
	 *            the key
	 * @return the value
	 */
	public V get(Object key) {
		if (key == null) {
			return null;
		}

		int hash = getHash(key);

		for (TableEntry<K, V> entry = table[hash]; entry != null; entry = entry.next) {
			if (key.equals(entry.key)) {
				return entry.value;
			}
		}

		return null;
	}

	/**
	 * Removes the element from the table with the given key.
	 *
	 * @param key
	 *            the key
	 */
	public void remove(Object key) {
		if (key == null) {
			return;
		}

		int hash = getHash(key);

		TableEntry<K, V> entry = table[hash];
		for (TableEntry<K, V> prev = null; entry != null; prev = entry, entry = entry.next) {
			if (key.equals(entry.key)) {
				modificationCount++;

				if (prev != null) {
					prev.next = entry.next;
				} else {
					table[hash] = entry.next;
				}

				size--;
				break;
			}
		}
	}

	/**
	 * Checks if the table contains the given key.
	 *
	 * @param key
	 *            the key
	 * @return true, if the table contains the given key
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}

		int hash = getHash(key);

		for (TableEntry<K, V> entry = table[hash]; entry != null; entry = entry.next) {
			if (key.equals(entry.key)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if the table contains the given value.
	 *
	 * @param value
	 *            the value
	 * @return true, if the table contains the given value
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			for (TableEntry<K, V> entry = table[i]; entry != null; entry = entry.next) {
				if(value == null && entry.value == null) {
					return true;
				}
				if (value != null && value.equals(entry.value)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Gets the table size.
	 *
	 * @return the table size
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks if the table is empty.
	 *
	 * @return true, if it's empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');

		for (int i = 0; i < table.length; i++) {
			for (TableEntry<K, V> entry = table[i]; entry != null; entry = entry.next) {
				sb.append(entry.toString());
				if (i < table.length - 1 || entry.next != null) {
					sb.append(", ");
				}
			}
		}

		sb.append(']');
		return sb.toString();
	}

	/**
	 * Removes all the entries from the table.
	 */
	public void clear() {
		modificationCount++;

		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Increases the capacity and internally reorganizes this hashtable. This
	 * method is called when the number of keys in the hashtable exceeds this
	 * hashtable's load factor.
	 */
	@SuppressWarnings("unchecked")
	private void rehash() {
		TableEntry<K, V>[] oldTable = table;

		int newCapacity = oldTable.length * 2;
		TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[newCapacity];

		table = newTable;
		setThreshold(newCapacity);
		modificationCount++;

		for (int i = 0; i < oldTable.length; i++) {
			for (TableEntry<K, V> oldEntry = oldTable[i]; oldEntry != null;) {
				TableEntry<K, V> entry = oldEntry;
				oldEntry = oldEntry.next;

				int hash = getHash(entry.key);
				entry.next = newTable[hash];
				newTable[hash] = entry;
			}
		}
	}

	/**
	 * Sets the table threshold.
	 * 
	 * @param capacity
	 *            the capacity
	 */
	private void setThreshold(int capacity) {
		threshold = (int) (capacity * LOAD_FACTOR);
	}
}
