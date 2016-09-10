package com.ea.connect4.be.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
		CommandLineTest.class,
		CommandRectangleTest.class,
		CommandFillTest.class
})
public class AllTests {

}
