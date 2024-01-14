package pl.agency.real_estate_agency.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.agency.real_estate_agency.config.ImagePathConfig;
import pl.agency.real_estate_agency.exception.RecordNotFoundException;
import pl.agency.real_estate_agency.model.Address;
import pl.agency.real_estate_agency.model.Property;
import pl.agency.real_estate_agency.service.AddressService;
import pl.agency.real_estate_agency.service.PropertyService;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class Controller {
    private final PropertyService propertyService;
    private final AddressService addressService;
    private final ImagePathConfig imagePath;


    @RequestMapping(path = "/createProperty", method = RequestMethod.POST)
    public String createOrUpdateCar(Property property,
                                    Address address,
                                    @RequestParam("img") List<MultipartFile> multipartFile) {
        propertyService.createOrUpdateProperty(property, address);
        if (!multipartFile.isEmpty()) {
            try {
                for (MultipartFile imageFile : multipartFile) {
                    propertyService.saveImage(property, imageFile);
                }
            } catch (Exception e) {
                log.error("Error save photo", e);
            }
        }
        return "redirect:/";
    }

    @GetMapping()
    public String showPropertyList(Model model) {
        List<Property> properties = propertyService.getAllProperties();
        List<Address> addresses = addressService.getAllAddresses();
        model.addAttribute("addresses", addresses);
        model.addAttribute("properties", properties);
        model.addAttribute("imagePath", imagePath.getImageGet());
        return "propertyList";
    }

    @GetMapping(path = {"/edit", "/edit/{id}"})
    public String editPropertyById(Model model, @PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException {
        if (id.isPresent()) {
            Property entity = propertyService.getPropertyById(id.get());
            model.addAttribute("property", entity);
        } else {
            model.addAttribute("property", new Property());
        }
        return "edit-property";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deletePropertyById(Model model, @PathVariable("id") Long id)
            throws RecordNotFoundException {
        propertyService.deletePropertyById(id);
        return "redirect:/";
    }
}
