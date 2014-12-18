package pl.allegro.abtest;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import pl.allegro.abtest.configuration.ABTestConfiguration;

public class ABTestApplication extends Application<ABTestConfiguration> {

	public static void main(String[] args) throws Exception {
		new ABTestApplication().run(args);
	}

	@Override
	public String getName() {
		return "abtest";
	}

	@Override
	public void initialize(Bootstrap<ABTestConfiguration> bootstrap) {
		// nothing to do yet
	}

	@Override
	public void run(ABTestConfiguration configuration, Environment environment) {
	}
}
