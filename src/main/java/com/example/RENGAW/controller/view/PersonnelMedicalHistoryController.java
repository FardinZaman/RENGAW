package com.example.RENGAW.controller.view;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.PersonnelMedicalHistory;
import com.example.RENGAW.service.PersonnelMedicalHistoryService;
import com.example.RENGAW.service.PersonnelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rng/pmh")
public class PersonnelMedicalHistoryController {

    private final PersonnelMedicalHistoryService personnelMedicalHistoryService;
    private final PersonnelService personnelService;

    @Autowired
    public PersonnelMedicalHistoryController(PersonnelMedicalHistoryService personnelMedicalHistoryService, PersonnelService personnelService) {
        this.personnelMedicalHistoryService = personnelMedicalHistoryService;
        this.personnelService = personnelService;
    }

    @GetMapping("/medicalHistory/{personnelId}")
    public String showMedicalHistory(@PathVariable Long personnelId,
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

    @GetMapping("/medicalHistoryForm/{personnelId}")
    public String showMedicalHistoryForm(@PathVariable Long personnelId,
                                         Model model){
        PersonnelMedicalHistory personnelMedicalHistory =
                new PersonnelMedicalHistory();
        Personnel personnel = personnelService.findPersonnelById(personnelId);

        model.addAttribute("personnelMedicalHistory", personnelMedicalHistory);
        model.addAttribute("personnel", personnel);

        return "pmhForm";
    }

    @PostMapping("/savePmh/{personnelId}")
    public String saveMedicalHistory(@PathVariable Long personnelId,
                                     @Valid @ModelAttribute PersonnelMedicalHistory personnelMedicalHistory,
                                     BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "pmhForm";
        }

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

        return "pmhForm";
    }

    @GetMapping("/deletePmh/{pmhid}")
    public String deleteMedicalHistory(@PathVariable("pmhid") Long personnelMedicalHistoryId){
        Long personnelId = personnelMedicalHistoryService.findMedicalHistoryById(personnelMedicalHistoryId).getPersonnel().getId();
        personnelMedicalHistoryService.deleteMedicalHistory(personnelMedicalHistoryId);

        return "redirect:/rng/p/showPersonnel/" + personnelId;
    }
}
