package com.example.RENGAW.controller.view;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rng/p")
public class PersonnelController {

    private final PersonnelService personnelService;

    @Autowired
    public PersonnelController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    @GetMapping("/")
    public String findAllPersonnel(Model model){
        return findAllPersonnelPaginated(1, "firstName", "asc", model);
    }

    @GetMapping("/page/{pageNo}")
    private String findAllPersonnelPaginated(@PathVariable int pageNo,
                                             @RequestParam String sortField,
                                             @RequestParam String sortDirection,
                                             Model model) {

        int pageSize = 4;

        Page<Personnel> personnelPage = personnelService.findAllPersonnelPaginated(pageNo, pageSize, sortField, sortDirection);
        List<Personnel> personnelList = personnelPage.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", personnelPage.getTotalPages());
        model.addAttribute("totalItems", personnelPage.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection",
                sortDirection.equalsIgnoreCase("asc") ? "desc" : "asc");

        model.addAttribute("personnelList", personnelList);

        return "allPersonnel";
    }

    @GetMapping("/newPersonnelForm")
    public String showNewPersonnelForm(Model model){
        Personnel personnel = new Personnel();
        model.addAttribute("personnel", personnel);
        model.addAttribute("formType", "n");

        return "personnelForm";
    }

    @PostMapping("/savePersonnel")
    public String savePersonnel(@ModelAttribute Personnel personnel){
        personnelService.savePersonnel(personnel);

        return "redirect:/rng/p/";
    }

    @GetMapping("/showPersonnel/{personnelId}")
    public String showPersonnelDetails(@PathVariable Long personnelId,
                                       Model model){
        Personnel personnel = personnelService.findPersonnelById(personnelId);
        model.addAttribute("personnel", personnel);

        return "personnelDetails";
    }

    @GetMapping("updatePersonnelForm/{personnelId}")
    public String showUpdatePersonnelForm(@PathVariable Long personnelId,
                                          Model model){
        Personnel personnel = personnelService.findPersonnelById(personnelId);
        model.addAttribute("personnel", personnel);
        model.addAttribute("formType", "u");

        return "personnelForm";
    }

    @GetMapping("/deletePersonnel/{personnelId}")
    public String deletePersonnel(@PathVariable Long personnelId){
        personnelService.deletePersonnelById(personnelId);

        return "redirect:/rng/p/";
    }
}
