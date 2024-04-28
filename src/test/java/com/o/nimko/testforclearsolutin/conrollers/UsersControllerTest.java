package com.o.nimko.testforclearsolutin.conrollers;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.o.nimko.testforclearsolutin.dto.Person;
import com.o.nimko.testforclearsolutin.dto.ResponseDetails;
import com.o.nimko.testforclearsolutin.dto.ValidationResult;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsersControllerTest {

  @LocalServerPort
  private int port;

  private Person person;
  private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

  @Value("${person.age.min}")
  private int age;

  @BeforeEach
  void setUp() {
    Locale.setDefault(Locale.ENGLISH);
    RestAssured.baseURI = "http://localhost:" + port + "/users";
    startPersonInit();
  }

  private void startPersonInit() {
    person = new Person("my@email.com", "Ivan", "Ivanov", LocalDate.of(2004, 1, 2),
        "my address", "+380500001111");
  }

  private void newtPersonInit() {
    person = new Person("nomy@email.en", "Petro", "Petrov", LocalDate.of(2002, 2, 22),
        "no my address", "+380679991111");
  }

  @Test
  @Order(1)
  void createPerson() {
    var response = postResponse(HttpStatus.CREATED.value());
    String id = response.body().asString();
    assertNotNull(id);
  }

  @Test
  @Order(2)
  void wrongEmailPerson() throws JsonProcessingException {
    person.setEmail("wrong-email.com");
    var response = postResponse(HttpStatus.BAD_REQUEST.value());
    var error = objectMapper.readValue(response.body().print(), ResponseDetails.class);
    var result = new ValidationResult("email", "must be a well-formed email address");
    assertEquals(result, error.getValidation().get(0));
  }

  @Test
  @Order(3)
  void wrongDatePerson() throws JsonProcessingException {
    person.setBirthDate(now().plusDays(1));
    var response = postResponse(HttpStatus.BAD_REQUEST.value());
    var error = objectMapper.readValue(response.body().print(), ResponseDetails.class);
    var result = new ValidationResult("birthDate", "Date of birth invalid!");
    assertEquals(result, error.getValidation().get(0));
  }


  @Test
  @Order(4)
  void wrongAgePerson() throws JsonProcessingException {
    var date = now().minusYears(age).plusDays(1);
    person.setBirthDate(date);
    var response = postResponse(HttpStatus.BAD_REQUEST.value());
    var error = objectMapper.readValue(response.body().print(), ResponseDetails.class);
    var result = new ValidationResult("birthDate", "Date of birth invalid!");
    assertEquals(result, error.getValidation().get(0));
  }

  @Test
  @Order(5)
  void wrongFirstNamePersonNull() throws JsonProcessingException {
    person.setFirstName(null);
    var response = postResponse(HttpStatus.BAD_REQUEST.value());
    var error = objectMapper.readValue(response.body().print(), ResponseDetails.class);
    var result = new ValidationResult("firstName", "must not be blank");
    assertEquals(result, error.getValidation().get(0));
  }

  @Test
  @Order(6)
  void wrongLastNamePersonBlank() throws JsonProcessingException {
    person.setLastName("");
    var response = postResponse(HttpStatus.BAD_REQUEST.value());
    var error = objectMapper.readValue(response.body().print(), ResponseDetails.class);
    var result = new ValidationResult("lastName", "must not be blank");
    assertEquals(result, error.getValidation().get(0));
  }


  @Test
  @Order(7)
  void getPersonByDateRange() throws JsonProcessingException {
    var jsonString = getResponse(HttpStatus.OK.value(), person.getBirthDate().minusDays(1),
        person.getBirthDate().plusDays(1)).getBody().asString();
    List<Person> personList = objectMapper.readValue(jsonString, new TypeReference<>() {
    });
    assertFalse(personList.isEmpty());
    assertEquals(personList.get(0), person);
  }

  @Test
  @Order(7)
  void getPersonByDateRangeWrong() {
    getResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
        person.getBirthDate().plusDays(1)).getBody().asString();
  }

  @Test
  @Order(8)
  void patchPerson() throws JsonProcessingException {
    patchPersonResponse(0, HttpStatus.OK.value());
    getPersonByDateRange();
  }

  @Test
  @Order(8)
  void patchPersonByFakeId() {
    patchPersonResponse(1, HttpStatus.NOT_FOUND.value());
  }

  @Test
  @Order(9)
  void updatePerson() throws JsonProcessingException {
    newtPersonInit();
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(person)
        .when()
        .put("/0"  )
        .then()
        .statusCode(HttpStatus.OK.value()).extract().response();
    getPersonByDateRange();
  }

  @Test
  @Order(10)
  void deletePerson() {
    deletePersonResponse(HttpStatus.OK.value());
    deletePersonResponse(HttpStatus.NOT_FOUND.value());
  }

  private Response postResponse(int statusCode) {
    return RestAssured.given()
        .contentType(ContentType.JSON)
        .body(person)
        .when()
        .post("")
        .then()
        .statusCode(statusCode).extract().response();
  }

  private Response getResponse(int statusCode, LocalDate from, LocalDate to) {
    var fromDate = from != null ? from.toString() : null;
    return RestAssured.given()
        .contentType(ContentType.JSON)
        .param("from", fromDate)
        .param("to", to.toString())
        .when()
        .get("")
        .then()
        .statusCode(statusCode).extract().response();
  }

  private void patchPersonResponse(int id, int status) {
    startPersonInit();
    person.setLastName("Petrov");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(person)
        .when()
        .patch("/" + id )
        .then()
        .statusCode(status).extract().response();
  }

  private void deletePersonResponse(int status) {
    startPersonInit();
    person.setLastName("Petrov");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/0" )
        .then()
        .statusCode(status).extract().response();
  }

}