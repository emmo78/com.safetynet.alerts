package com.safetynet.alerts.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.NullNode;
import com.safetynet.alerts.dto.AddressAdultChildDTO;
import com.safetynet.alerts.dto.AddressPersonDTO;
import com.safetynet.alerts.dto.AddressPersonEmailDTO;
import com.safetynet.alerts.dto.service.AddressDTOService;
import com.safetynet.alerts.dto.service.AddressDTOServiceImpl;
import com.safetynet.alerts.model.Address;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.JsonRepository;
import com.safetynet.alerts.repository.JsonRepositoryImpl;
import com.safetynet.alerts.repository.WriteToFile;

@Service
public class AddressServiceImpl implements AddressService {
	
	private Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

	@Autowired
	private JsonRepository jsonNodeService;
	
    @Autowired
	private AddressDTOService addressDTOService;
    
	@Autowired
	private WriteToFile fileWriter;
	
	@Autowired
	private StringService dataProcService;
	
	private Map<String, Address> allAddressS;

	@PostConstruct
	public void addressServiceImpl () {
		allAddressS = ((JsonRepositoryImpl) jsonNodeService).getAllAddressS();
	}
	
	@Override
	public List<AddressAdultChildDTO> findChildrenByAddress(String address) throws ResourceNotFoundException {
		List<AddressAdultChildDTO> addressChildrenDTO = addressDTOService.addressChildrenToDTO(Optional.ofNullable(allAddressS.get(address)).orElseThrow(() -> {
			fileWriter.writeToFile(NullNode.instance);
			return new ResourceNotFoundException("No address found");})
				.getPersons().values().stream().filter(person -> person.getAge() <= 18).sorted((p1, p2) -> p1.getLastName().compareTo(p2.getLastName())).collect(Collectors.toList()));
		logger.info("Found {} child(ren) at the address : {}", addressChildrenDTO.size(), address);
		return addressChildrenDTO;
	}

	@Override
	public List<AddressPersonDTO> findPersonsByAddress(String address) throws ResourceNotFoundException {
		((AddressDTOServiceImpl) addressDTOService).setStationNumbers(findFirestationssByAddress(address).stream().map(firestation -> String.valueOf(firestation.getStationNumber())).collect(Collectors.toList()));
		List<AddressPersonDTO> addressPersonsDTO = addressDTOService.addressPersonsToDTO(allAddressS.get(address).getPersons().values().stream().collect(Collectors.toList()));
		logger.info("Found {} person(s) at the address : {}", addressPersonsDTO.size()-1, address);
		return addressPersonsDTO; 
	}

	@Override
	public List<AddressPersonEmailDTO> findemailPersonsByCity(String city) throws ResourceNotFoundException {
		List<Address> addressCity = allAddressS.values().stream().filter(address -> address.getCity().equals(dataProcService.upperCasingFirstLetter(city))).collect(Collectors.toList());
 		if ( addressCity.size() == 0) {
 			fileWriter.writeToFile(NullNode.instance);
			throw new ResourceNotFoundException("No city found");
		}
 		List<AddressPersonEmailDTO> addressPersonEmailDTO = addressDTOService.addressPersonEmailToDTO(addressCity.stream().flatMap(address -> address.getPersons().values().stream()).collect(Collectors.toList())); 
 		logger.info("Found {} email(s) in the city : {}", addressPersonEmailDTO.size(), city);
 		return addressPersonEmailDTO;
	}

	@Override
	public List<Firestation> findFirestationssByAddress(String address) throws ResourceNotFoundException {
		return Optional.ofNullable(allAddressS.get(address)).orElseThrow(() -> {
			fileWriter.writeToFile(NullNode.instance);
			return new ResourceNotFoundException("No address found");})
				.getFirestations().values().stream().collect(Collectors.toList());
	}
}
