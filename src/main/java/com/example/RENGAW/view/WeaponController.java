package com.example.RENGAW.view;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.Weapon;
import com.example.RENGAW.service.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rng/w")
public class WeaponController {

    @Autowired
    private WeaponService weaponService;

    @GetMapping("/")
    public String findAllWeapon(Model model){
        return findAllWeaponPaginated(1, "gunType", "asc", model);
    }

    @GetMapping("/page/{pageNo}")
    private String findAllWeaponPaginated(@PathVariable("pageNo") int pageNo,
                                          @RequestParam("sortField") String sortField,
                                          @RequestParam("sortDirection") String sortDirection,
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
        model.addAttribute("formType", "n");

        return "weaponForm";
    }

    @PostMapping("/saveWeapon")
    public String saveWeapon(@ModelAttribute("weapon") Weapon weapon){
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

    @GetMapping("updateWeaponForm/{wsn}")
    public String showUpdateWeaponForm(@PathVariable("wsn") Long weaponSerialNumber,
                                          Model model){
        Weapon weapon = weaponService.findWeaponBySerialNumber(weaponSerialNumber);
        model.addAttribute("weapon", weapon);
        model.addAttribute("formType", "u");

        return "weaponForm";
    }

    @GetMapping("/deleteWeapon/{wsn}")
    public String deletePersonnel(@PathVariable("wsn") Long weaponSerialNumber){
        weaponService.deleteWeaponBySerialNumber(weaponSerialNumber);

        return "redirect:/rng/w/";
    }
}
