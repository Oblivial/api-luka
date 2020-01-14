package de.luka.api.nummern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller	// This means that this class is a Controller
@RequestMapping(path="/nummern")
public class NummerController {
	@Autowired // This means to get the bean called userRepository
			   // Which is auto-generated by Spring, we will use it to handle the data
	private NummerRepository nummerRepository;

	@PostMapping(path="/add") // Map ONLY POST Requests
	public @ResponseBody String addNewNumber (@RequestParam String inputNumber) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		Nummer n = new Nummer();
		n.setNumberInput(inputNumber);
		nummerRepository.save(n);
		return "Saved";
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<Nummer> getAllNumbers() {
		// This returns a JSON or XML with the 
		return nummerRepository.findAll();
	}
}