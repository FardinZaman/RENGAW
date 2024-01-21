package com.example.RENGAW.controller.view;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.Weapon;
import com.example.RENGAW.service.PersonnelService;
import com.example.RENGAW.service.WeaponService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rng/w")
public class WeaponController {

    private final WeaponService weaponService;
    private final PersonnelService personnelService;

    @Autowired
    public WeaponController(WeaponService weaponService, PersonnelService personnelService) {
        this.weaponService = weaponService;
        this.personnelService = personnelService;
    }

    @GetMapping("/")
    public String findAllWeapon(Model model){
        return findAllWeaponPaginated(1, "gunType", "asc", model);
    }

    @GetMapping("/page/{pageNo}")
    private String findAllWeaponPaginated(@PathVariable int pageNo,
                                          @RequestParam String sortField,
                                          @RequestParam String sortDirection,
                                          Model model) {
        int pageSize = 4;

        Page<Weapon> weaponPage = weaponService.findAllWeaponPaginated(pageNo, pageSize, sortField, sortDirection);
        List<Weapon> weaponList = weaponPage.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", weaponPage.getTotalPages());
        model.addAttribute("totalItems", weaponPage.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection",
                sortDirection.equalsIgnoreCase("asc") ? "desc" : "asc");

        model.addAttribute("weaponList", weaponList);

        return "allWeapon";
    }

    @GetMapping("/newWeaponForm")
    public String showNewWeaponForm(Model model){
        Weapon weapon = new Weapon();
        model.addAttribute("weapon", weapon);

        return "weaponForm";
    }

    @PostMapping("/saveWeapon")
    public String saveWeapon(@Valid @ModelAttribute Weapon weapon,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "weaponForm";
        }

        weaponService.saveWeapon(weapon);

        return "redirect:/rng/w/";
    }

    @GetMapping("/showWeapon/{wsn}")
    public String showWeaponDetails(@PathVariable("wsn") Long weaponSerialNumber,
                                       Model model){
        Weapon weapon = weaponService.findWeaponBySerialNumber(weaponSerialNumber);
        model.addAttribute("weapon", weapon);

        return "weaponDetails";
    }

    @GetMapping("/updateWeaponForm/{wsn}")
    public String showUpdateWeaponForm(@PathVariable("wsn") Long weaponSerialNumber,
                                          Model model){
        Weapon weapon = weaponService.findWeaponBySerialNumber(weaponSerialNumber);
        model.addAttribute("weapon", weapon);

        return "weaponForm";
    }

    @GetMapping("/deleteWeapon/{wsn}")
    public String deleteWeapon(@PathVariable("wsn") Long weaponSerialNumber){
        weaponService.deleteWeaponBySerialNumber(weaponSerialNumber);

        return "redirect:/rng/w/";
    }

    @GetMapping("/assignWeapon/{wsn}")
    public String choosePersonnelForWeapon(@PathVariable("wsn") Long weaponSerialNumber,
                                          Model model){
        List<Personnel> personnelList = personnelService.findAllPersonnel();

        model.addAttribute("personnelList", personnelList);
        model.addAttribute("weaponSerialNumber", weaponSerialNumber);

        return "personnelForWeapon";
    }

    @GetMapping("/weaponToPersonnel/pid/{pid}/serial/{wsn}")
    public String assignWeaponToPersonnel(@PathVariable("pid") Long personnelId,
                                          @PathVariable("wsn") Long weaponSerialNumber,
                                          Model model){
        List<Weapon> weaponList = weaponService.findWeaponOfPersonnel(personnelId);
        if(weaponList.isEmpty()){
            return finalizeAssigning(personnelId, weaponSerialNumber);
        }

        Personnel personnel = personnelService.findPersonnelById(personnelId);

        model.addAttribute("weaponList", weaponList);
        model.addAttribute("personnelId", personnelId);
        model.addAttribute("personnelName", personnel.getCurrentRank()+' '+personnel.getFirstName()+' '+personnel.getLastName());
        model.addAttribute("weaponSerialNumber", weaponSerialNumber);

        return "assignWeapon";
    }

    @GetMapping("/finalizePersonnel/pid/{pid}/serial/{wsn}")
    public String finalizeAssigning(@PathVariable("pid") Long personnelId,
                                    @PathVariable("wsn") Long weaponSerialNumber){
        weaponService.assignWeaponToPersonnel(weaponSerialNumber, personnelId);

        return "redirect:/rng/w/showWeapon/" + weaponSerialNumber;
    }

    @GetMapping("/weaponOfPersonnel/{personnelId}")
    public String showWeaponOfPersonnel(@PathVariable Long personnelId,
                                        Model model){
        List<Weapon> weaponList = weaponService.findWeaponOfPersonnel(personnelId);

        model.addAttribute("weaponList", weaponList);
        model.addAttribute("personnelId", personnelId);

        return "weaponOfPersonnel";
    }

    @GetMapping("/removePersonnel/{wsn}")
    public String removePersonnel(@PathVariable("wsn") Long weaponSerialNumber){
        weaponService.removePersonnel(weaponSerialNumber);

        return "redirect:/rng/w/showWeapon/" + weaponSerialNumber;
    }
}
