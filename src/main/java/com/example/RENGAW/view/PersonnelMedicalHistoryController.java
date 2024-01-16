package com.example.RENGAW.view;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.PersonnelMedicalHistory;
import com.example.RENGAW.service.PersonnelMedicalHistoryService;
import com.example.RENGAW.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rng/pmh")
public class PersonnelMedicalHistoryController {

    @Autowired
    private PersonnelMedicalHistoryService personnelMedicalHistoryService;

    @Autowired
    private PersonnelService personnelService;

    @GetMapping("/medicalHistory/{pid}")
    public String showMedicalHistory(@PathVariable("pid") Long personnelId,
                                     Model model){
        PersonnelMedicalHistory personnelMedicalHistory =
                personnelMedicalHistoryService.findMedicalHistoryByPersonnelId(personnelId);

        model.addAttribute("personnelId", personnelId);

        if(personnelMedicalHistory == null){
            return "addPmh";
        }

        model.addAttribute("personnelMedicalHistory", personnelMedicalHistory);

        return "pmhDetails";
    }

    @GetMapping("/medicalHistoryForm/{pid}")
    public String showMedicalHistoryForm(@PathVariable("pid") Long personnelId,
                                         Model model){
        PersonnelMedicalHistory personnelMedicalHistory =
                new PersonnelMedicalHistory();
        Personnel personnel = personnelService.findPersonnelById(personnelId);

        model.addAttribute("personnelMedicalHistory", personnelMedicalHistory);
        model.addAttribute("personnel", personnel);
        model.addAttribute("formType", "n");

        return "pmhForm";
    }

    @PostMapping("/savePmh/{pid}")
    public String saveMedicalHistory(@PathVariable("pid") Long personnelId,
                                     @ModelAttribute("personnelMedicalHistory") PersonnelMedicalHistory personnelMedicalHistory){
        personnelMedicalHistoryService.savePersonnelMedicalHistory(personnelMedicalHistory, personnelId);

        return "redirect:/rng/p/showPersonnel/" + personnelId;
    }

    @GetMapping("/updatePmhForm/{pmhid}")
    public String showUpdatePmhForm(@PathVariable("pmhid") Long personnelMedicalHistoryId,
                                    Model model){
        PersonnelMedicalHistory personnelMedicalHistory =
                personnelMedicalHistoryService.findMedicalHistoryById(personnelMedicalHistoryId);

        model.addAttribute("personnelMedicalHistory", personnelMedicalHistory);
        model.addAttribute("personnel", personnelMedicalHistory.getPersonnel());
        model.addAttribute("formType", "u");

        return "pmhForm";
    }

    @GetMapping("/deletePmh/{pmhid}")
    public String deleteMedicalHistory(@PathVariable("pmhid") Long personnelMedicalHistoryId){
        Long personnelId = personnelMedicalHistoryService.findMedicalHistoryById(personnelMedicalHistoryId).getPersonnel().getId();
        personnelMedicalHistoryService.deleteMedicalHistory(personnelMedicalHistoryId);

        return "redirect:/rng/p/showPersonnel/" + personnelId;
    }
}
