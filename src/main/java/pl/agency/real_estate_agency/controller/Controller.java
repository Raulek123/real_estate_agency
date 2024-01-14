package pl.agency.real_estate_agency.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @GetMapping("/createAddress")
    public String showAddAddressForm(Model model) {
        model.addAttribute("address", new Address());
        return "addAddress";
    }

    @GetMapping("/createProperty")
    public String showAddPropertyForm(Model model) {
        model.addAttribute("property", new Property());
        return "addProperty";
    }

//    @PostMapping("/createAddress")
//    public String createAddress(@ModelAttribute Address address) {
//        try {
//            addressService.createOrUpdateAddress(address);
//            return "redirect:/addressList";
//        } catch (Exception e) {
//            log.error("Error while creating address", e);
//            return "redirect:/error";
//        }
//    }

    @PostMapping("/createAddress")
    public String createAddress(@ModelAttribute Address address, RedirectAttributes redirectAttributes) {
        try {
            addressService.createOrUpdateAddress(address);
            log.debug("Address created successfully: " + address);
            redirectAttributes.addFlashAttribute("successMessage", "Address created successfully");
        } catch (Exception e) {
            log.error("Error while creating address", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating address");
        }
        return "redirect:/addressList";
    }
    @GetMapping("/addressList")
    public String showAddressList(Model model) {
        List<Address> addresses = addressService.getAllAddresses();
        model.addAttribute("addresses", addresses);
        return "addressList";
    }

}
