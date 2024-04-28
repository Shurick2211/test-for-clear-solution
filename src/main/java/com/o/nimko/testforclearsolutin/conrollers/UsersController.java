package com.o.nimko.testforclearsolutin.conrollers;

import com.o.nimko.testforclearsolutin.dto.Person;
import com.o.nimko.testforclearsolutin.services.PersonService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@OpenAPIDefinition(info =
@Info(
    title = "${spring.application.name}",
    version = "${spring.application.version}",
    description = "Test API",
    contact = @Contact(name = "Olexandr", email = "shurick2211@gmail.com")
)
)
public class UsersController {

  private final PersonService personService;

  @PostMapping
  @Operation(summary = "Create User", description = "In this method available to create User. Date of user`s birthday in format yyyy-MM-dd")
  public ResponseEntity<Integer> createPerson(@Valid @RequestBody Person person) {
    return ResponseEntity.status(HttpStatus.CREATED).body(personService.createPerson(person));
  }

  @GetMapping
  @Operation(summary = "Get users for range dates", description = "Date is like: yyyy-MM-dd")
  public ResponseEntity<List<Person>> getPersonByDateRange(
      @NotNull @RequestParam("from") String from, @NotNull @RequestParam("to") String to) {
    return ResponseEntity.ok(personService.getPersonByDateRange(from, to));
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Patch one or some field of User", description = "User`s optional fields maybe null if it no change")
  public ResponseEntity<?> patchPerson(@PathVariable("id") int id, @Valid @RequestBody Person person) {
    personService.patchPerson(id, person);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update User", description = "It full update an User. All user`s fields will be updated.")
  public ResponseEntity<?> updatePerson(@PathVariable("id") int id,
      @Valid @RequestBody Person person) {
    personService.updatePerson(id, person);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete User", description = "In this method for delete User by id")
  public ResponseEntity<?> deletePerson(@PathVariable("id") int id) {
    personService.deletePerson(id);
    return ResponseEntity.ok().build();
  }
}
