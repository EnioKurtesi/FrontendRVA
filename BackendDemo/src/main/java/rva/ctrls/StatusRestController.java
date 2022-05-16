package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Status;
import rva.repository.StatusRepository;

@CrossOrigin
@RestController
@Api(tags = {"Status CRUD operacije"})
public class StatusRestController {
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@GetMapping("status")
	@ApiOperation(value = "Vraca kolekciju svih statusa iz baze podataka")
	public Collection<Status> getStatuse() {
		return statusRepository.findAll();
	}
	
	
	@GetMapping("status/{id}")
	@ApiOperation(value = "Vraca status na osnovu vrednosti id-a")
	public Status getStatus(@PathVariable("id") Integer id) {
		return statusRepository.getOne(id);
	}
	
	@GetMapping("statusNaziv/{naziv}")
	@ApiOperation(value = "Vraca kolekciju svih statusa na osnovu vrednosti naziva iz baze podataka")
	public Collection<Status> getStatuseByNaziv(@PathVariable("naziv") String naziv)
	{
		return statusRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	
	@PostMapping("status")
	@ApiOperation(value = "Dodaje novi status u bazu podataka")

	public ResponseEntity<Status> insertStatus(@RequestBody Status status) {
		if(!statusRepository.existsById(status.getId())) {
			statusRepository.save(status);
			return new ResponseEntity<Status>(HttpStatus.OK);
			
		}
		return new ResponseEntity<Status>(HttpStatus.CONFLICT);
		
		
	}
	
	@PutMapping("status")
	@ApiOperation(value = "Update-uje postojeci status")

	public ResponseEntity<Status> updateStatus(@RequestBody Status status) {
		
		if(statusRepository.existsById(status.getId())) {
			statusRepository.save(status);
			return new ResponseEntity<Status>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Status>(HttpStatus.NO_CONTENT);
		
		
	}
	
	@DeleteMapping("status/{id}")
	@ApiOperation(value = "Brise status na osnovu prosledjenog id-a iz baze podataka")

	public ResponseEntity<Status> deleteStatus(@PathVariable("id") Integer id) {
		
		if(statusRepository.existsById(id)) {
			statusRepository.deleteById(id);
			
			if(id == -100) {
				
				jdbcTemplate.execute("insert into status (id, naziv, oznaka) values (-100,'Testni status','Test');");
				jdbcTemplate.execute(" insert into student(id, ime, prezime, broj_indeksa, status, departman) values (-100,'Marko','Rakic','IT6-2019',-100,-100);");
			}
			return new ResponseEntity<Status>(HttpStatus.OK);
		}
		return new ResponseEntity<Status>(HttpStatus.NO_CONTENT);
	}
	

}
