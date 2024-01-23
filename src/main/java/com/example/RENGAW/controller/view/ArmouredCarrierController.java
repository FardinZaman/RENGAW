package com.example.RENGAW.controller.view;

import com.example.RENGAW.entity.ArmouredCarrier;
import com.example.RENGAW.entity.Weapon;
import com.example.RENGAW.service.ArmouredCarrierService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rng/ac")
public class ArmouredCarrierController {

    private final ArmouredCarrierService armouredCarrierService;

    public ArmouredCarrierController(ArmouredCarrierService armouredCarrierService) {
        this.armouredCarrierService = armouredCarrierService;
    }

    @GetMapping("/")
    public String findAllCarrier(Model model){
        return findAllCarrierPaginated(1, "carrierType", "asc", model);
    }

    @GetMapping("/page/{pageNo}")
    private String findAllCarrierPaginated(@PathVariable int pageNo,
                                          @RequestParam String sortField,
                                          @RequestParam String sortDirection,
                                          Model model) {
        int pageSize = 4;

        Page<ArmouredCarrier> armouredCarrierPage = armouredCarrierService.findAllCarrierPaginated(pageNo, pageSize, sortField, sortDirection);
        List<ArmouredCarrier> armouredCarrierList = armouredCarrierPage.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", armouredCarrierPage.getTotalPages());
        model.addAttribute("totalItems", armouredCarrierPage.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection",
                sortDirection.equalsIgnoreCase("asc") ? "desc" : "asc");

        model.addAttribute("carrierList", armouredCarrierList);

        return "carrier/allCarrier";
    }

    @GetMapping("/newCarrierForm")
    public String showNewCarrierForm(Model model){
        ArmouredCarrier armouredCarrier = new ArmouredCarrier();
        model.addAttribute("carrier", armouredCarrier);

        return "carrier/carrierForm";
    }

    @PostMapping("/saveCarrier")
    public String saveWeapon(@Valid @ModelAttribute ArmouredCarrier carrier,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "carrier/carrierForm";
        }

        armouredCarrierService.saveArmouredCarrier(carrier);

        return "redirect:/rng/ac/";
    }
}
