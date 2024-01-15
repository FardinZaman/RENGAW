package com.example.RENGAW.view;

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

    @Autowired
    private PersonnelService personnelService;

    @GetMapping("/")
    public String findAllPersonnel(Model model){
        return findAllPersonnelPaginated(1, "firstName", "asc", model);
    }

    @GetMapping("/page/{pageNo}")
    private String findAllPersonnelPaginated(@PathVariable("pageNo") int pageNo,
                                             @RequestParam("sortField") String sortField,
                                             @RequestParam("sortDirection") String sortDirection,
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

        return "newPersonnel";
    }

    @PostMapping("/savePersonnel")
    public String savePersonnel(@ModelAttribute("personnel") Personnel personnel){
        personnelService.savePersonnel(personnel);

        return "redirect:/rng/p/";
    }

    @GetMapping("/showPersonnel/{pid}")
    public String showPersonnelDetails(@PathVariable("pid") Long personnelId,
                                       Model model){
        Personnel personnel = personnelService.findPersonnelById(personnelId);
        model.addAttribute("personnel", personnel);

        return "personnelDetails";
    }

    @GetMapping("updatePersonnelForm/{pid}")
    public String showUpdatePersonnelForm(@PathVariable("pid") Long personnelId,
                                          Model model){
        Personnel personnel = personnelService.findPersonnelById(personnelId);
        model.addAttribute("personnel", personnel);

        return "updatePersonnel";
    }

    @GetMapping("/deletePersonnel/{pid}")
    public String deletePersonnel(@PathVariable("pid") Long personnelId){
        personnelService.deletePersonnelById(personnelId);

        return "redirect:/rng/p/";
    }
}
