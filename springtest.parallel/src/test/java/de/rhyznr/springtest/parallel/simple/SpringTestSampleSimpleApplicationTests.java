/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.rhyznr.springtest.parallel.simple;

import static org.junit.Assert.assertNotNull;

import java.lang.management.ManagementFactory;
import java.util.Collection;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import de.rhyznr.springtest.parallel.simple.service.HelloWorldService;

/**
 * Tests for {@link SampleSimpleApplication}.
 * 
 * @author Dave Syer
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(Parameterized.class)
@SpringBootTest(classes = HelloWorldService.class)
public class SpringTestSampleSimpleApplicationTests {

	@ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

	@Autowired
	HelloWorldService helloWorldService;

	private int dummy;
	
	public SpringTestSampleSimpleApplicationTests(int dummy) {
		this.dummy = dummy;
	}
	
	// name attribute is optional, provide an unique name for test
	// multiple parameters, uses Collection<Object[]>
    @Parameters(name = "dummy = {0}")
    public static Collection<Object[]> data() {
    	return IntStream.range(1, 100).mapToObj(dummy -> new Object[] {dummy}).collect(Collectors.toList());
    }

	@Test
	public void testHelloWorld() throws Exception {
		String systemProcess = ManagementFactory.getRuntimeMXBean().getName();
		String threadName = Thread.currentThread().getName();
		System.out.println("systemProcess="+systemProcess+" thread="+threadName);
		Thread.sleep(500);
		
		assertNotNull(helloWorldService);
	}
	
}
