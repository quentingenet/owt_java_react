package com.quentingenet.openweighttracker.controller;

import com.quentingenet.openweighttracker.GetUserConnectedId;
import com.quentingenet.openweighttracker.dto.weights.WeightRecordDto;
import com.quentingenet.openweighttracker.dto.weights.WeightsDto;
import com.quentingenet.openweighttracker.entity.PersonEntity;
import com.quentingenet.openweighttracker.entity.WeightRecordEntity;
import com.quentingenet.openweighttracker.repository.PersonRepository;
import com.quentingenet.openweighttracker.repository.WeightRecordRepository;
import com.quentingenet.openweighttracker.service.WeightRecordServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
@RestController
@RequestMapping("/weights")
public class WeightRecordController {

	@Autowired
	WeightRecordServiceImpl weightRecordServiceImpl;

	@Autowired
	WeightRecordRepository weightRecordRepository;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	GetUserConnectedId getUserConnectedId;

	private final Logger logger = LoggerFactory.getLogger(WeightRecordController.class);

	@GetMapping("/all")
	public ResponseEntity<WeightsDto> getAllWeights(Principal principal) {
		logger.info("GET /weights/all");
		Long appUserConnectedId = getUserConnectedId.getAppUserConnectedId(principal);
		return new ResponseEntity<WeightsDto>(weightRecordServiceImpl.getAllWeights(appUserConnectedId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<WeightRecordDto> saveWeight(@RequestBody WeightRecordDto weightRecordFromUser,
																										Principal principal) {
		logger.info("POST /weights");
		Long appUserConnectedId = getUserConnectedId.getAppUserConnectedId(principal);
		Optional<PersonEntity> personUserToSave = personRepository.findById(appUserConnectedId);
		logger.info("PERSON FINDED IN DATABASE");
		if (personUserToSave.isPresent()) {
			return new ResponseEntity<WeightRecordDto>(
					weightRecordServiceImpl.saveWeightRecordByIdUser(appUserConnectedId, weightRecordFromUser),
					HttpStatus.CREATED);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PatchMapping("/update/{weightId}")
	public ResponseEntity<WeightRecordDto> updateWeight(@PathVariable("weightId") Long weightId,
																											@RequestBody WeightRecordDto WeightRecordToUpdate, Principal principal) {
		logger.info("PATCH /weights/update/{}", weightId);
		try {
			Long appUserConnectedId = getUserConnectedId.getAppUserConnectedId(principal);
			PersonEntity personConnected = personRepository.findById(appUserConnectedId).orElseThrow();
			WeightRecordEntity weightRecordFinded = weightRecordRepository.findById(weightId).orElseThrow();

			Long userPersonId = personConnected.getIdPerson();
			Long recordPersonId = weightRecordFinded.getPerson().getIdPerson();

			if (Objects.equals(userPersonId, recordPersonId)) {
				return ResponseEntity.ok(weightRecordServiceImpl.updateWeight(weightId, WeightRecordToUpdate, appUserConnectedId));
			} else {
				logger.info("BAD USER FOR BAD WEIGHT : UPDATE NOT POSSIBLE");
				return ResponseEntity.notFound().build();
			}
		} catch (NoSuchElementException nse) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{weightId}")
	public ResponseEntity<WeightRecordDto> deleteWeigthById(@PathVariable("weightId") Long weightId,
																													Principal principal) {
		logger.info("DELETE /weights/{}", weightId);
		try {

			Long appUserConnectedId = getUserConnectedId.getAppUserConnectedId(principal);
			PersonEntity personConnected = personRepository.findById(appUserConnectedId).orElseThrow();
			WeightRecordEntity weightRecordToDelete = weightRecordRepository.findById(weightId).orElseThrow();

			Long userPersonId = personConnected.getIdPerson();
			Long recordPersonId = weightRecordToDelete.getPerson().getIdPerson();

			if (Objects.equals(userPersonId, recordPersonId)) {
				logger.info("SERVICE FOR DELETING");
				weightRecordServiceImpl.deleteWeightById(appUserConnectedId, weightRecordToDelete.getIdWeightRecord());
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (

				NoSuchElementException nse) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
}
