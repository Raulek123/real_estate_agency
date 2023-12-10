package pl.agency.real_estate_agency.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.agency.real_estate_agency.config.ImagePathConfig;
import pl.agency.real_estate_agency.model.Address;
import pl.agency.real_estate_agency.model.Property;
import pl.agency.real_estate_agency.service.AddressService;
import pl.agency.real_estate_agency.service.PropertyService;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;
    private final AddressService addressService;
    /* Tak wiem, że dubluję wstrzykiwanie klasy ImagePathConfig, Bo wykorzystujemy już to w PropertyService,
       jak będziemy mieli już działającą aplikację webową, którą będzie można przetestować to postaram się to zmienić.
    */
    private final ImagePathConfig imagePath;


    @RequestMapping(path = "/admin/createProperty", method = RequestMethod.POST)
    public String createOrUpdateCar(Property property,
                                    Address address,
                                    @RequestParam("img") List<MultipartFile> multipartFile) {
        propertyService.createOrUpdateProperty(property);
        addressService.createOrUpdateAddress(address);
        if (!multipartFile.isEmpty()) {
            try {
                for (MultipartFile imageFile : multipartFile) {
                    propertyService.saveImage(property, imageFile);
                }
            } catch (Exception e) {
                log.error("Error save photo", e);
            }
        }
        return "redirect:/admin";
    }
}
