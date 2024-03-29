package com.safetynet.alerts.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.safetynet.alerts.dto.MedicalrecordDTO;
import com.safetynet.alerts.exception.BadRequestException;
import com.safetynet.alerts.exception.ResourceConflictException;
import com.safetynet.alerts.service.MedicalrecordService;


@ExtendWith(MockitoExtension.class)
public class MedicalrecordControllerTest {

	@InjectMocks
	private MedicalrecordController medicalrecordController;
	
	@Mock
	private MedicalrecordService medicalrecordService;
	
	private MockHttpServletRequest requestMock;
	private WebRequest request;
	
	@BeforeEach
	public void setUpPerTest() {
		requestMock = new MockHttpServletRequest();
		request = new ServletWebRequest(requestMock);
	}
	
	@AfterEach
    public void undefPerTest() {
		requestMock = null;
		request = null;
	}
	
	@Nested
    @Tag("POST")
    @DisplayName("POST /medicalRecord create tests")
    class createMedicalrecordTests {
		
		@Test
		@Tag("NominalCase")
	    @DisplayName("createMedicalrecordTest should return HTTPStatus.OK and the MedicalrecordDTO created")
		public void createMedicalrecordTestShouldReturnHTTPStatusOKAndMedicalrecordCreated() {
			//GIVEN
			MedicalrecordDTO medicalrecordDTO = new MedicalrecordDTO("FirstName", "LastName", LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), new ArrayList<String>(), new ArrayList<String>());
			Optional<MedicalrecordDTO> medicalrecordDTOOpt = Optional.of(medicalrecordDTO);
			try {
				when(medicalrecordService.createMedicalrecord(medicalrecordDTO, request)).thenReturn(medicalrecordDTO);
			} catch (ResourceConflictException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ResponseEntity<MedicalrecordDTO> responseEntity = null;
			
			//WHEN
			try {
				responseEntity = medicalrecordController.createMedicalrecord(medicalrecordDTOOpt, request);
			} catch (ResourceConflictException | BadRequestException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//THEN
			assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
			assertThat(responseEntity.getBody()).isEqualTo(medicalrecordDTO);
		}
		
		@Test
		@Tag("CornerCase")
	    @DisplayName("createMedicalrecordTest should throw BadRequestException")
		public void createMedicalrecordTestShouldThrowBadRequestException() {
			//GIVEN
			Optional<MedicalrecordDTO> medicalrecordDTOOpt = Optional.ofNullable(null);
			//WHEN
			//THEN
			assertThrows(BadRequestException.class, () -> medicalrecordController.createMedicalrecord(medicalrecordDTOOpt, request));
		}
	}
	
	@Nested
    @Tag("PUT")
    @DisplayName("PUT /medicalRecord update tests")
    class updateMedicalrecordTests {
		
		@Test
		@Tag("NominalCase")
	    @DisplayName("updateMedicalrecordTest should return HTTPStatus.OK and the MedicalrecordDTO updated")
		public void updateMedicalrecordTestShouldReturnHTTPStatusOKAndUpdatedMedicalrecord() {
			//GIVEN
			MedicalrecordDTO medicalrecordDTO = new MedicalrecordDTO("FirstName", "LastName", LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), new ArrayList<String>(), new ArrayList<String>());
			Optional<MedicalrecordDTO> medicalrecordDTOOpt = Optional.of(medicalrecordDTO);
			try {
				when(medicalrecordService.updateMedicalrecord(medicalrecordDTO, request)).thenReturn(medicalrecordDTO);
			} catch (ResourceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ResponseEntity<MedicalrecordDTO> responseEntity = null;
			
			//WHEN
			try {
				responseEntity = medicalrecordController.updateMedicalrecord(medicalrecordDTOOpt, request);
			} catch (ResourceNotFoundException | BadRequestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//THEN
			assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
			assertThat(responseEntity.getBody()).isEqualTo(medicalrecordDTO);
		}
		
		@Test
		@Tag("CornerCase")
	    @DisplayName("updateMedicalrecordTest should throw BadRequestException")
		public void updateMedicalrecordTestShouldThrowBadRequestException() {
			//GIVEN
			Optional<MedicalrecordDTO> medicalrecordDTOOpt = Optional.ofNullable(null);
			//WHEN
			//THEN
			assertThrows(BadRequestException.class, () -> medicalrecordController.updateMedicalrecord(medicalrecordDTOOpt, request));
		}
	}
	
	@Nested
    @Tag("DELETE")
    @DisplayName("DELETE /medicalRecord delete tests")
    class deleteMedicalrecordTests {
		
		@Test
		@Tag("NominalCase")
	    @DisplayName("deleteMedicalrecordTest should return HTTPStatus.OK and a new MedicalrecordDTO")
		public void deleteMedicalrecordTestShouldReturnHTTPStatusOKAndANullFieldsMedicalrecord() {
			//GIVEN
			MedicalrecordDTO medicalrecordDTO = new MedicalrecordDTO("FirstName", "LastName", LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), new ArrayList<String>(), new ArrayList<String>());
			Optional<MedicalrecordDTO> medicalrecordDTOOpt = Optional.of(medicalrecordDTO);
			try {
				when(medicalrecordService.deleteMedicalrecord(medicalrecordDTO, request)).thenReturn(new MedicalrecordDTO());
			} catch (ResourceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ResponseEntity<MedicalrecordDTO> responseEntity = null;
			
			//WHEN
			try {
				responseEntity = medicalrecordController.deleteMedicalrecord(medicalrecordDTOOpt, request);
			} catch (ResourceNotFoundException | BadRequestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//THEN
			assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
			assertThat(responseEntity.getBody()).isEqualTo(new MedicalrecordDTO());
		}
		
		@Test
		@Tag("CornerCase")
	    @DisplayName("deleteMedicalrecordTest should throw BadRequestException")
		public void deleteMedicalrecordTestShouldThrowBadRequestException() {
			//GIVEN
			Optional<MedicalrecordDTO> medicalrecordDTOOpt = Optional.ofNullable(null);
			//WHEN
			//THEN
			assertThrows(BadRequestException.class, () -> medicalrecordController.deleteMedicalrecord(medicalrecordDTOOpt, request));
		}
	}
}
