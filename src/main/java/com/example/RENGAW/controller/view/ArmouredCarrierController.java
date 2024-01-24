package com.example.RENGAW.controller.view;

import com.example.RENGAW.entity.ArmouredCarrier;
import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.Team;
import com.example.RENGAW.entity.Weapon;
import com.example.RENGAW.service.ArmouredCarrierService;
import com.example.RENGAW.service.TeamService;
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
    private final TeamService teamService;

    public ArmouredCarrierController(ArmouredCarrierService armouredCarrierService, TeamService teamService) {
        this.armouredCarrierService = armouredCarrierService;
        this.teamService = teamService;
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
    public String saveCarrier(@Valid @ModelAttribute ArmouredCarrier carrier,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "carrier/carrierForm";
        }

        armouredCarrierService.saveArmouredCarrier(carrier);

        return "redirect:/rng/ac/";
    }

    @GetMapping("/showCarrier/{csn}")
    public String showCarrierDetails(@PathVariable("csn") Long carrierSerialNumber,
                                    Model model){
        ArmouredCarrier armouredCarrier = armouredCarrierService.findCarrierBySerialNumber(carrierSerialNumber);
        model.addAttribute("carrier", armouredCarrier);

        return "carrier/carrierDetails";
    }

    @GetMapping("/updateCarrierForm/{csn}")
    public String showUpdateCarrierForm(@PathVariable("csn") Long carrierSerialNumber,
                                       Model model){
        ArmouredCarrier armouredCarrier = armouredCarrierService.findCarrierBySerialNumber(carrierSerialNumber);
        model.addAttribute("carrier", armouredCarrier);

        return "carrier/carrierForm";
    }

    @GetMapping("/deleteCarrier/{csn}")
    public String deleteCarrier(@PathVariable("csn") Long carrierSerialNumber){
        armouredCarrierService.deleteCarrierBySerialNumber(carrierSerialNumber);

        return "redirect:/rng/ac/";
    }

    @GetMapping("/assignCarrier/{csn}")
    public String chooseTeamForCarrier(@PathVariable("csn") Long carrierSerialNumber,
                                           Model model){
        List<Team> teamList = teamService.findAllTeam();

        model.addAttribute("teamList", teamList);
        model.addAttribute("carrierSerialNumber", carrierSerialNumber);

        return "carrier/teamForCarrier";
    }

    @GetMapping("/carrierToTeam/tid/{tid}/serial/{csn}")
    public String assignCarrierToTeam(@PathVariable("tid") Long teamId,
                                      @PathVariable("csn") Long carrierSerialNumber,
                                      Model model){
        List<ArmouredCarrier> armouredCarrierList = armouredCarrierService.findCarrierOfTeam(teamId);
        if(armouredCarrierList.isEmpty()){
            return finalizeAssigning(teamId, carrierSerialNumber);
        }

        Team team = teamService.findTeamById(teamId);

        model.addAttribute("carrierList", armouredCarrierList);
        model.addAttribute("teamId", teamId);
        model.addAttribute("teamCodeName", team.getTeamCodeName());
        model.addAttribute("carrierSerialNumber", carrierSerialNumber);

        return "carrier/assignCarrier";
    }

    @GetMapping("/finalizePersonnel/tid/{tid}/serial/{csn}")
    public String finalizeAssigning(@PathVariable("tid") Long teamId,
                                    @PathVariable("csn") Long carrierSerialNumber){
        armouredCarrierService.assignCarrierToTeam(carrierSerialNumber, teamId);

        return "redirect:/rng/ac/showCarrier/" + carrierSerialNumber;
    }

    @GetMapping("/carrierOfTeam/{teamId}")
    public String showCarrierOfTeam(@PathVariable Long teamId,
                                        Model model){
        List<ArmouredCarrier> armouredCarrierList = armouredCarrierService.findCarrierOfTeam(teamId);

        model.addAttribute("carrierList", armouredCarrierList);
        model.addAttribute("teamId", teamId);

        return "carrier/carrierOfTeam";
    }

    @GetMapping("/removeTeam/{csn}")
    public String removeTeam(@PathVariable("csn") Long carrierSerialNumber){
        armouredCarrierService.removeTeam(carrierSerialNumber);

        return "redirect:/rng/ac/showCarrier/" + carrierSerialNumber;
    }

}
