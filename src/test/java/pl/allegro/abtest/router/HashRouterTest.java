package pl.allegro.abtest.router;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

public class HashRouterTest extends TestCase {

	@Test
	public void testSameGroupAssignment() {
		Map<String, Integer> weights = new HashMap<String, Integer>();
		weights.put("Group0", 5);
		weights.put("Group1", 3);
		weights.put("Group2", 2);
		weights.put("Group3", 1);
		weights.put("Group4", 1);

		HashRouter router = new HashRouter(weights);

		// int size = 5 + 3 + 2 + 1 + 1;
		assertEquals(router.route("abcdef"), router.route("abcdef"));
		// Math.abs("abcdef".hashCode()) % size = 1
		// assertEquals(router.route("abcdef"),"Group1");

		assertEquals(router.route("xxxxx"), router.route("xxxxx"));
		// Math.abs("xxxxx".hashCode()) % size = 0
		// assertEquals(router.route("xxxxx"),"Group0");

		assertEquals(router.route("123456"), router.route("123456"));
		// Math.abs("123456".hashCode()) % size = 3
		// assertEquals(router.route("123456"),"Group3");
	}

	@Test
	public void testDistribution() {

		// need to keep the order when we traverse later on
		Map<String, Integer> weights = new LinkedHashMap<String, Integer>();

		weights.put("Group0", 3);
		weights.put("Group1", 5);
		weights.put("Group2", 1);
		weights.put("Group3", 10);
		weights.put("Group4", 40);

		HashRouter router = new HashRouter(weights);

		// need to keep the order when we traverse later on
		Map<String, Integer> counters = new LinkedHashMap<String, Integer>();
		counters.put("Group0", 0);
		counters.put("Group1", 0);
		counters.put("Group2", 0);
		counters.put("Group3", 0);
		counters.put("Group4", 0);

		// count the numbers for each group every time it is assigned by router
		for (int i = 0; i < 1000000L; i++) {
			String testUser = RandomStringUtils.random(10);
			String group = router.route(testUser);
			int value = counters.get(group) + 1;
			counters.put(group, value);
		}

		// compare distributions with delta
		Assert.assertArrayEquals(mapDistribution(weights),
				mapDistribution(counters), 0.01);
	}

	private double mapSum(Map<String, Integer> mapping) {
		long sum = 0;
		for (Map.Entry<String, Integer> entry : mapping.entrySet()) {
			sum += entry.getValue();
		}
		return sum;
	}

	private double[] mapDistribution(Map<String, Integer> mapping) {
		double overall = mapSum(mapping);
		double[] result = new double[mapping.size()];
		int index = 0;
		for (Map.Entry<String, Integer> entry : mapping.entrySet()) {
			result[index++] = entry.getValue() / overall;
		}
		return result;
	}
}
