package pl.agency.real_estate_agency.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.agency.real_estate_agency.exception.RecordNotFoundException;
import pl.agency.real_estate_agency.model.Address;
import pl.agency.real_estate_agency.repository.AddressRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public void createOrUpdateAddress(Address address){
        addressRepository.save(address);
    }

    @Transactional
    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Address not found with id: " + id));
    }

    @Transactional
    public List<Address> getAllAddresses() {
        return  (List<Address>) addressRepository.findAll();
    }

    @Transactional
    public void deleteAddressById(Long id) {
        addressRepository.deleteById(id);
    }

    @Transactional
    public List<Address> getAddressesByCity(String city) {
        return addressRepository.findByCity(city);
    }
}
