package pl.agency.real_estate_agency.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.agency.real_estate_agency.model.Address;
import pl.agency.real_estate_agency.repository.AddressRepository;

@Service
@RequiredArgsConstructor
public class AddressService {
    private AddressRepository addressRepository;

    @Transactional
    public void createOrUpdateAddress(Address address){
        addressRepository.save(address);
    }
}
