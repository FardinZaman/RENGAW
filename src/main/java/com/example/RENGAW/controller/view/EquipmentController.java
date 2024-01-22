package com.example.RENGAW.controller.view;

import com.example.RENGAW.entity.Equipment;
import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.Weapon;
import com.example.RENGAW.service.EquipmentService;
import com.example.RENGAW.service.PersonnelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rng/e")
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final PersonnelService personnelService;

    public EquipmentController(EquipmentService equipmentService, PersonnelService personnelService) {
        this.equipmentService = equipmentService;
        this.personnelService = personnelService;
    }

    @GetMapping("/")
    public String findAllEquipment(Model model){
        return findAllEquipmentPaginated(1, "equipmentType", "asc", model);
    }

    @GetMapping("/page/{pageNo}")
    private String findAllEquipmentPaginated(@PathVariable int pageNo,
                                          @RequestParam String sortField,
                                          @RequestParam String sortDirection,
                                          Model model) {
        int pageSize = 4;

        Page<Equipment> equipmentPage = equipmentService.findAllEquipmentPaginated(pageNo, pageSize, sortField, sortDirection);
        List<Equipment> equipmentList = equipmentPage.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", equipmentPage.getTotalPages());
        model.addAttribute("totalItems", equipmentPage.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection",
                sortDirection.equalsIgnoreCase("asc") ? "desc" : "asc");

        model.addAttribute("equipmentList", equipmentList);

        return "equipment/allEquipment";
    }

    @GetMapping("/newEquipmentForm")
    public String showNewEquipmentForm(Model model){
        Equipment equipment = new Equipment();
        model.addAttribute("equipment", equipment);

        return "equipment/equipmentForm";
    }

    @PostMapping("/saveEquipment")
    public String saveEquipment(@Valid @ModelAttribute Equipment equipment,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "equipment/equipmentForm";
        }

        equipmentService.saveEquipment(equipment);

        return "redirect:/rng/e/";
    }

    @GetMapping("/showEquipment/{esn}")
    public String showEquipmentDetails(@PathVariable("esn") Long equipmentSerialNumber,
                                    Model model){
        Equipment equipment = equipmentService.findEquipmentBySerialNumber(equipmentSerialNumber);
        model.addAttribute("equipment", equipment);

        return "equipment/equipmentDetails";
    }
    @GetMapping("/updateEquipmentForm/{esn}")
    public String showUpdateEquipmentForm(@PathVariable("esn") Long equipmentSerialNumber,
                                       Model model){
        Equipment equipment = equipmentService.findEquipmentBySerialNumber(equipmentSerialNumber);
        model.addAttribute("equipment", equipment);

        return "equipment/equipmentForm";
    }

    @GetMapping("/deleteEquipment/{esn}")
    public String deleteEquipment(@PathVariable("esn") Long equipmentSerialNumber){
        equipmentService.deleteEquipmentBySerialNumber(equipmentSerialNumber);

        return "redirect:/rng/e/";
    }

    @GetMapping("/assignEquipment/{esn}")
    public String choosePersonnelForEquipment(@PathVariable("esn") Long equipmentSerialNumber,
                                           Model model){
        List<Personnel> personnelList = personnelService.findAllPersonnel();

        model.addAttribute("personnelList", personnelList);
        model.addAttribute("equipmentSerialNumber", equipmentSerialNumber);

        return "equipment/personnelForEquipment";
    }

    @GetMapping("/equipmentToPersonnel/pid/{pid}/serial/{esn}")
    public String assignEquipmentToPersonnel(@PathVariable("pid") Long personnelId,
                                          @PathVariable("esn") Long equipmentSerialNumber,
                                          Model model){
        List<Equipment> equipmentList = equipmentService.findEquipmentOfPersonnel(personnelId);
        if(equipmentList.isEmpty()){
            return finalizeAssigning(personnelId, equipmentSerialNumber);
        }

        Personnel personnel = personnelService.findPersonnelById(personnelId);

        model.addAttribute("equipmentList", equipmentList);
        model.addAttribute("personnelId", personnelId);
        model.addAttribute("personnelName", personnel.getCurrentRank()+' '+personnel.getFirstName()+' '+personnel.getLastName());
        model.addAttribute("equipmentSerialNumber", equipmentSerialNumber);

        return "equipment/assignEquipment";
    }

    @GetMapping("/finalizePersonnel/pid/{pid}/serial/{esn}")
    public String finalizeAssigning(@PathVariable("pid") Long personnelId,
                                    @PathVariable("esn") Long equipmentSerialNumber){
        equipmentService.assignEquipmentToPersonnel(equipmentSerialNumber, personnelId);

        return "redirect:/rng/e/showEquipment/" + equipmentSerialNumber;
    }

    @GetMapping("/equipmentOfPersonnel/{personnelId}")
    public String showEquipmentOfPersonnel(@PathVariable Long personnelId,
                                        Model model){
        List<Equipment> equipmentList = equipmentService.findEquipmentOfPersonnel(personnelId);

        model.addAttribute("equipmentList", equipmentList);
        model.addAttribute("personnelId", personnelId);

        return "equipment/equipmentOfPersonnel";
    }

    @GetMapping("/removePersonnel/{esn}")
    public String removePersonnel(@PathVariable("esn") Long equipmentSerialNumber){
        equipmentService.removePersonnel(equipmentSerialNumber);

        return "redirect:/rng/e/showEquipment/" + equipmentSerialNumber;
    }

}
