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
import rva.jpa.Status;
import rva.jpa.Student;
import rva.repository.DepartmanRepository;
import rva.repository.StatusRepository;
import rva.repository.StudentRepository;

@CrossOrigin
@RestController
@Api(tags = {"Student CRUD operacije"})

public class StudentRestController {
	
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private DepartmanRepository departmanRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@GetMapping("student")
	@ApiOperation(value = "Vraca kolekciju svih studenata iz baze podataka")
	public Collection<Student> getStudente() {
		return studentRepository.findAll();
	}
	
	@GetMapping("student/{id}")
	@ApiOperation(value = "Vraca studenta na osnovu id-a iz baze podataka")
	public Student getStudent(@PathVariable("id") Integer id) {
		return studentRepository.getOne(id);
	}
	
	@GetMapping("studentIme/{ime}")
	@ApiOperation(value = "Vraca kolekciju svih statusa po imenu iz baze podataka")
	public Collection<Student> getStudenteByIme(@PathVariable("ime") String ime)
	{
		return studentRepository.findByImeContainingIgnoreCase(ime);
	}
	
	@GetMapping("studentByDepartman/{id}")
	@ApiOperation(value = "Vraca kolekciju svih studenata po id-u departmana iz baze podataka")
	public Collection<Student> getStudenteByDepartman(@PathVariable("id") Integer id)
	{
		Departman departman = departmanRepository.getOne(id);
		return studentRepository.findByDepartman(departman);
	}
	
	
	@GetMapping("studentByStatus/{id}")
	@ApiOperation(value = "Vraca kolekciju svih studenata po id-u statusa iz baze podataka")
	public Collection<Student> getStudenteByStatus(@PathVariable("id") Integer id)
	{
		Status departman = statusRepository.getOne(id);
		return studentRepository.findByStatus(departman);
	}

	@PostMapping("student")
	@ApiOperation(value = "Dodaje novog studenta u bazu podataka")
	public ResponseEntity<Student> insertStudent(@RequestBody Student student) {
		if(!studentRepository.existsById(student.getId())) {
			studentRepository.save(student);
			return new ResponseEntity<Student>(HttpStatus.OK);
			
		}
		return new ResponseEntity<Student>(HttpStatus.CONFLICT);
		
		
	}
	
	@PutMapping("student")
	@ApiOperation(value = "Update-uje postojećeg studenta")
	public ResponseEntity<Student> updateStutend(@RequestBody Student student) {
		
		if(studentRepository.existsById(student.getId())) {
			studentRepository.save(student);
			return new ResponseEntity<Student>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
		
		
	}
	
	@DeleteMapping("student/{id}")
	@ApiOperation(value = "Brise studenta na osnovu vrednosti id-a iz baze podataka")
	public ResponseEntity<Student> deleteStudent(@PathVariable("id") Integer id) {
		
		if(studentRepository.existsById(id)) {
			studentRepository.deleteById(id);
			
			if(id == -100) {
				
				jdbcTemplate.execute("insert into student(id, ime, prezime, broj_indeksa, status, departman) values (-100,'Marko','Rakic','IT6-2019',-100,-100);");
			}
			return new ResponseEntity<Student>(HttpStatus.OK);
		}
		return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
	}
	
}
