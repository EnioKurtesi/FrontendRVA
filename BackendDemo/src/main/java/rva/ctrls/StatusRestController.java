package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import rva.jpa.Status;
import rva.repository.StatusRepository;

@RestController
public class StatusRestController {
	
	@Autowired
	private StatusRepository statusRepository;
	
	@GetMapping("status")
	public Collection<Status> getStatuse() {
		return statusRepository.findAll();
	}
	
	
	@GetMapping("status/{id}")
	public Status getStatus(@PathVariable("id") Integer id) {
		return statusRepository.getOne(id);
	}
	
	

}
