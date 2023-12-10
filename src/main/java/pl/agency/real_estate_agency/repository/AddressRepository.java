package pl.agency.real_estate_agency.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.agency.real_estate_agency.model.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
}
