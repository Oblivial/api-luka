package de.luka.api.tasks;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ScheduledTasks {
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	
	/*
	@Scheduled(fixedRate = 5000)
	public void updateSecurityConstants() {
		log.info("Initiating Security Constants Update");
		InputStream iStream = this.getClass().getClassLoader().getResourceAsStream("keys.properties");
		Properties props = new Properties();
		try {
			props.load(iStream);
			SecurityConstants.JWT_SECRET = props.getProperty("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("IOException in load", e);
		}
	}
	*/
	
	
}
