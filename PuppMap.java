package edu.mccc.cos210.fp.pupp;

import edu.mccc.cos210.ds.IMap;
import edu.mccc.cos210.ds.IOrderedList;
import edu.mccc.cos210.ds.Map;
import edu.mccc.cos210.ds.OrderedList;

public class PuppMap<K,V> extends edu.mccc.cos210.ds.Map<K, V>{

	public PuppMap() {
		super();
	}
	@Override
	public void put(K newKey, V newValue) {
		IMap.Entry<K, V> entry = new Map.Entry<K, V>(newKey, newValue);
		int index = compress(hash(entry));
		IOrderedList<IMap.Entry<K, V>> curList = theVector.get(index);
		if(curList.contains(entry)) {
			IOrderedList<IMap.Entry<K, V>> newList = new OrderedList<>();
			while(!curList.isEmpty()) {
				IMap.Entry<K, V> current = curList.removeFirst();
				if(current.getKey().equals(newKey)) {
					current = entry;
				}
				newList.add(current);
			}
		} else {
			super.put(newKey,newValue);
		}
	}
	
}
