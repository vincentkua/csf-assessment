package ibf2022.batch2.csf.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class UploadController {

	@GetMapping(value = "/test")
	public ResponseEntity<String> testServer(){
		System.out.println("ok");
		return null;
	}


	// TODO: Task 2, Task 3, Task 4
	

	// TODO: Task 5
	

	// TODO: Task 6

}
