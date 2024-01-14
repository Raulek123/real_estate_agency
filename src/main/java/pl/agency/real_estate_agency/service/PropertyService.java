package pl.agency.real_estate_agency.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.agency.real_estate_agency.config.ImagePathConfig;
import pl.agency.real_estate_agency.exception.RecordNotFoundException;
import pl.agency.real_estate_agency.model.Address;
import pl.agency.real_estate_agency.model.Property;
import pl.agency.real_estate_agency.repository.PropertyRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final AddressService addressService;
    private final ImagePathConfig imagePath;


    @Transactional
    public void createOrUpdateProperty(Property property, Address address) {

//        addressService.createOrUpdateAddress(address);

        property.setAddress(address);
        propertyRepository.save(property);
    }
    @Transactional
    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Property not found with id: " + id));
    }
    @Transactional
    public List<Property> getAllProperties() {
        return (List<Property>) propertyRepository.findAll();
    }

    @Transactional
    public void deletePropertyById(Long id) {
        Property property = propertyRepository.findById(id).orElse(null);
        if(property != null){
            Address address = property.getAddress();
            addressService.deleteAddressById(address.getId());
        }
        propertyRepository.deleteById(id);
    }


    @Transactional
    public void saveImage(Property property,
                          @RequestParam("image") MultipartFile multipartFile) throws Exception {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        property.setImage(fileName);

        Property propertySaved = propertyRepository.save(property);
        String uploadDiectory = imagePath.getImageSave() + propertySaved.getId();
        saveFile(uploadDiectory, fileName, multipartFile);
    }

    private void saveFile(String uploadDirectory, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save file: " + fileName, e);
        }
    }
}
