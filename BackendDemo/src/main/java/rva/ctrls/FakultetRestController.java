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
import rva.jpa.Departman;
import rva.jpa.Fakultet;
import rva.jpa.Status;
import rva.repository.FakultetRepository;
import rva.repository.StatusRepository;
@CrossOrigin
@RestController
@Api(tags = {"Fakultet CRUD operacije"})
public class FakultetRestController {
	
	@Autowired
	private FakultetRepository fakultetRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@GetMapping("fakultet")
	@ApiOperation(value = "Vraca kolekciju svih fakulteta iz baze podataka")
	public Collection<Fakultet> getFakultete() {
		return fakultetRepository.findAll();
	}
	
	@GetMapping("fakultet/{id}")
	@ApiOperation(value = "Vraca fakultet na osnovu vrednosti id-a iz baze podataka")
	public Fakultet getFakultet(@PathVariable("id") Integer id) {
		return fakultetRepository.getOne(id);
	}
	
	@GetMapping("fakultetNaziv/{naziv}")
	@ApiOperation(value = "Vraca kolekciju svih fakulteta po odredjenom nazivu")
	public Collection<Fakultet> getFakulteteByNaziv(@PathVariable("naziv") String naziv)
	{
		return fakultetRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("fakultet")
	@ApiOperation(value = "Dodaje novi fakultet u bazu podataka")
	public ResponseEntity<Fakultet> insertFakultet(@RequestBody Fakultet fakultet) {
		if(!fakultetRepository.existsById(fakultet.getId())) {
			fakultetRepository.save(fakultet);
			return new ResponseEntity<Fakultet>(HttpStatus.OK);
			
		}
		return new ResponseEntity<Fakultet>(HttpStatus.CONFLICT);
		
		
	}
	
	@PutMapping("fakultet")
	@ApiOperation(value = "Update-uje postojeći fakultet")
	public ResponseEntity<Fakultet> updateFakultet(@RequestBody Fakultet fakultet) {
		
		if(fakultetRepository.existsById(fakultet.getId())) {
			fakultetRepository.save(fakultet);
			return new ResponseEntity<Fakultet>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Fakultet>(HttpStatus.NO_CONTENT);
		
		
	}
	
	@DeleteMapping("fakultet/{id}")
	@ApiOperation(value = "Briše fakultet na osnovu vrednosti id-a")
	public ResponseEntity<Fakultet> deleteFakultet(@PathVariable("id") Integer id) {
		
		if(fakultetRepository.existsById(id)) {
			fakultetRepository.deleteById(id);
			
			if(id == -100) {
				
			jdbcTemplate.execute("insert into fakultet (id, naziv, sediste) values (-100,'Testni fakultet','Test');");
			jdbcTemplate.execute("insert into departman (id, naziv, oznaka, fakultet) values (-100,'Testni departman','Test',-100);");

			}
			return new ResponseEntity<Fakultet>(HttpStatus.OK);
		}
		return new ResponseEntity<Fakultet>(HttpStatus.NO_CONTENT);
	
	}
}
