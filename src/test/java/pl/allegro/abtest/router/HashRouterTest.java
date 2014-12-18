package pl.allegro.abtest.router;

import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class HashRouterTest extends TestCase {

	@Test
	public void testSameGroupAssignment() {

		ImmutableMap<String, Integer> weights = new ImmutableMap.Builder<String, Integer>()
				.put("Group0", 5).put("Group1", 3).put("Group2", 2)
				.put("Group3", 1).put("Group4", 1).build();

		HashRouter router = new HashRouter(weights);
		assertEquals(router.route("abcdef"), router.route("abcdef"));
		assertEquals(router.route("xxxxx"), router.route("xxxxx"));
		assertEquals(router.route("123456"), router.route("123456"));
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
