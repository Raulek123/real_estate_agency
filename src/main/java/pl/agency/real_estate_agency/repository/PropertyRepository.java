package pl.agency.real_estate_agency.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.agency.real_estate_agency.model.Property;

@Repository
public interface PropertyRepository extends CrudRepository<Property, Long> {
}
