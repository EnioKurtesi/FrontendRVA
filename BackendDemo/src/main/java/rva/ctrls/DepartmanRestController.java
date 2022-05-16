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
import rva.repository.DepartmanRepository;
import rva.repository.FakultetRepository;

@CrossOrigin
@RestController
@Api(tags = {"Departman CRUD operacije"})
public class DepartmanRestController {

	@Autowired
	private DepartmanRepository departmanRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@GetMapping("departman")
	@ApiOperation(value = "Vraca kolekciju svih departmana iz baze podataka")
	public Collection<Departman> getDepartmane() {
		return departmanRepository.findAll();
	}
	
	@GetMapping("departman/{id}")
	@ApiOperation(value = "Vraca departman u odnosu na prosledjenu vrednost path varijable id")
	public Departman getDepartman(@PathVariable("id") Integer id) {
		return departmanRepository.getOne(id);
	}
	
	@GetMapping("departmanNaziv/{naziv}")
	@ApiOperation(value = "Vraca kolekciju departmana koji imaju naziv koji sadrži vrednost prosleđenu u okviru path varijable naziv")
	public Collection<Departman> getDepartmaneByNaziv(@PathVariable("naziv") String naziv)
	{
		return departmanRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("departman")
	@ApiOperation(value = "Dodaje novi departman u bazu podataka.")
	public ResponseEntity<Departman> insertDepartman(@RequestBody Departman departman) {
		if(!departmanRepository.existsById(departman.getId())) {
			departmanRepository.save(departman);
			return new ResponseEntity<Departman>(HttpStatus.OK);
			
		}
		return new ResponseEntity<Departman>(HttpStatus.CONFLICT);
		
		
	}
	
	@PutMapping("departman")
	@ApiOperation(value = "Update-uje postojeći departman.")
	public ResponseEntity<Departman> updateDepartman(@RequestBody Departman departman) {
		
		if(departmanRepository.existsById(departman.getId())) {
			departmanRepository.save(departman);
			return new ResponseEntity<Departman>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Departman>(HttpStatus.NO_CONTENT);
		
		
	}
	
	@DeleteMapping("departman/{id}")
	@ApiOperation(value = "Briše departman u odnosu na vrednost prosleđene path varijable id.")
	public ResponseEntity<Departman> deleteDepartman(@PathVariable("id") Integer id) {
		
		if(departmanRepository.existsById(id)) {
			departmanRepository.deleteById(id);
			
			if(id == -100) {
				
			jdbcTemplate.execute("insert into departman (id, naziv, oznaka, fakultet) values (-100,'Testni departman','Test',-100);");
			jdbcTemplate.execute(" insert into student(id, ime, prezime, broj_indeksa, status, departman) values (-100,'Marko','Rakic','IT6-2019',-100,-100);");
			}
			return new ResponseEntity<Departman>(HttpStatus.OK);
		}
		return new ResponseEntity<Departman>(HttpStatus.NO_CONTENT);
	}
	
	
}
