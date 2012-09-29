package com.cwq.test;

import junit.framework.TestCase;

public class HelloWorldTest extends TestCase {

	public HelloWorldTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testHello() {
		HelloWorld helloWorld=new HelloWorld();
		assertEquals("Hello World",helloWorld.Hello());
	}

}
