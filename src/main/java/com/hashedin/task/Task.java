package com.hashedin.task;

/**
 * A class that performs task in the background.
 * 
 * Usage : 
 * 
 * public class Tasks {
 * 		public static class SendPasswordExpiryEmail<DateTime> implements Task<DateTime> {
 * 			@Autowired
 * 			private CustomerRepository repository;
 *  
 * 			public void execute(DateTime lastRunTime) {
 *				List<Customer> customers = 
 *					repository.findCustomersWithPasswordsThatAreExpiring(lastRunTime);
 *				for(Customer customer : customers) {
 *					sendPasswordExpiryEmail(customer);
 *				}
 * 			}
 * 		}		
 * }
 * 
 * The 
 * @author sripathikrishnan
 *
 * @param <P>
 */
public interface Task<P> {

	public void execute(P parameter);
}
