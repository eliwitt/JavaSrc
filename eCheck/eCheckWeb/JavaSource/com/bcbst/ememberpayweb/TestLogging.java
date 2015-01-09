/*
 * Created on Aug 26, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bcbst.ememberpayweb;

import org.apache.log4j.Logger;

/**
 * @author b85763m
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestLogging {

	private static Logger _logger = Logger.getLogger(TestLogging.class);	

	public static void main(String[] args) {
		_logger.info("Test logging");
		_logger.error("Test logging");
		_logger.warn("Test logging");
		_logger.debug("Test logging");	
	}
	
}

