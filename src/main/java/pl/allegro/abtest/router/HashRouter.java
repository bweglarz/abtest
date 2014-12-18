package pl.allegro.abtest.router;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * The class implements a simple way of assigning user ids to groups with a
 * given distribution. </br> The internal <code>groups</code> list should
 * contain the groups names with each group name being stored as many times as
 * the input mapping specifies so we can mimic the distribution. </br> Upon a
 * call to <code>route</code> method the input parameter should be hashed. The
 * hash will be squeezed to the <code>groups</code> size with a modulo operation
 * and then used as an index to the <code>groups</code> array.
 * 
 * @author bweglarz
 */
public class HashRouter implements Router {

	private List<String> groups = null;

	public HashRouter(Map<String, Integer> mapping) {
		Preconditions.checkNotNull(mapping);
		Preconditions.checkArgument(!mapping.isEmpty());

		groups = initializeGroups(mapping);
	}

	private List<String> initializeGroups(Map<String, Integer> mapping) {
		Map<String,Integer> temporary = Maps.newHashMap(mapping);
		List<String> list = Lists.newArrayList();

		// iterate while we still have items to process
		while (!temporary.isEmpty()) {
			for (Iterator<Map.Entry<String, Integer>> it = temporary.entrySet()
					.iterator(); it.hasNext();) {
				Map.Entry<String, Integer> element = it.next();
				//if we used all the instances of the group name then remove it
				if (element.getValue() == 0) {
					it.remove();
				} else {
					//if the value is bigger than 0 add such group name to the list
					//and decrease the counter
					list.add(element.getKey());
					element.setValue(element.getValue() - 1);
				}
			}
		}
		return ImmutableList.copyOf(list);
	}

	/**
	 * Hashes the input parameter and gets performs a modulo operation to get an index to
	 * the <code>groups</code> list.
	 */
	@Override
	public String route(String userId) {
		Preconditions.checkNotNull(userId);
		return groups.get(Math.abs(userId.hashCode()) % groups.size());
	}
}
