package net.sourceforge.jgeocoder.us;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.sourceforge.jgeocoder.AddressComponent;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Duration;

public class ParseAddress {
	private static final Logger LOG = Logger.getLogger(ParseAddress.class);
	private static final int REASONABLE_UPPER_BOUND = 80;
	
	public static Map<AddressComponent, String> withTimeout(String rawAddress) {
		return withTimeout(rawAddress, 4, TimeUnit.SECONDS);
	}
	
	public static Map<AddressComponent, String> withTimeout(String rawAddress, int timeoutValue, TimeUnit timeoutUnit) {
        if(rawAddress == null || rawAddress.length() > REASONABLE_UPPER_BOUND) {
        	//LOG.debug("The value passed is unacceptable: '" + rawAddress + "'.");
        	return null;
        }
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Map<AddressComponent, String>> future = executor.submit(new Task(rawAddress));
        Map<AddressComponent, String> result = null;
        DateTime start = DateTime.now();

        try {
            result = future.get(timeoutValue, timeoutUnit);
        } catch (TimeoutException | ExecutionException | InterruptedException | RuntimeException e) {
            Duration elapsed = new Duration(start, DateTime.now());
            LOG.error("Exception while trying to parse an address from '" + rawAddress + 
                      "'. Time taken was " + elapsed.getStandardSeconds() + " seconds.", e);
            result = null;
        } finally {
        	executor.shutdownNow();
        }
        
        return result;
	}
	
	public static class Task implements Callable<Map<AddressComponent, String>> {
		private final String address;
		
		public Task(String rawAddress) {
			address = rawAddress;
		}

		@Override
		public Map<AddressComponent, String> call() throws Exception {
			return AddressParser.parseAddress(address, true);
		}
		
	}
}
