package com.example.RENGAW.controller.view;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.Team;
import com.example.RENGAW.service.PersonnelService;
import com.example.RENGAW.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rng/t")
public class TeamController {

    private final TeamService teamService;
    private final PersonnelService personnelService;

    public TeamController(TeamService teamService, PersonnelService personnelService) {
        this.teamService = teamService;
        this.personnelService = personnelService;
    }

    @GetMapping("/")
    public String findAllTeam(Model model){
        return findAllTeamPaginated(1, "teamCodeName", "asc", model);
    }

    @GetMapping("/page/{pageNo}")
    private String findAllTeamPaginated(@PathVariable int pageNo,
                                          @RequestParam String sortField,
                                          @RequestParam String sortDirection,
                                          Model model) {
        int pageSize = 4;

        Page<Team> teamPage = teamService.findAllTeamPaginated(pageNo, pageSize, sortField, sortDirection);
        List<Team> teamList = teamPage.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", teamPage.getTotalPages());
        model.addAttribute("totalItems", teamPage.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection",
                sortDirection.equalsIgnoreCase("asc") ? "desc" : "asc");

        model.addAttribute("teamList", teamList);

        return "team/allTeam";
    }

    @GetMapping("/newTeamForm")
    public String showNewWeaponForm(Model model){
        Team team = new Team();
        model.addAttribute("team", team);

        return "team/teamForm";
    }

    @PostMapping("/saveTeam")
    public String saveTeam(@Valid @ModelAttribute Team team,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "team/teamForm";
        }

        teamService.saveTeam(team);

        return "redirect:/rng/t/";
    }

    @GetMapping("/showTeam/{teamId}")
    public String showTeamDetails(@PathVariable Long teamId,
                                    Model model){
        Team team = teamService.findTeamById(teamId);
        model.addAttribute("team", team);

        return "team/teamDetails";
    }

    @GetMapping("updateTeamForm/{teamId}")
    public String showUpdateTeamForm(@PathVariable Long teamId,
                                          Model model){
        Team team = teamService.findTeamById(teamId);
        model.addAttribute("team", team);

        return "team/teamForm";
    }

    @GetMapping("/deleteTeam/{teamId}")
    public String deleteTeam(@PathVariable Long teamId){
        teamService.deleteTeamById(teamId);

        return "redirect:/rng/t/";
    }

    @GetMapping("/teamMembers/{teamId}")
    public String showTeamMembers(@PathVariable Long teamId,
                                  Model model){
        List<Personnel> personnelList = personnelService.findPersonnelOfTeam(teamId);

        model.addAttribute("personnelList", personnelList);
        model.addAttribute("teamId", teamId);

        return "team/personnelOfTeam";
    }

    @GetMapping("/addPersonnel/{teamId}")
    public String choosePersonnelForTeam(@PathVariable Long teamId,
                                         Model model){
        List<Personnel> personnelList = personnelService.findPersonnelWithoutTeam();

        model.addAttribute("personnelList", personnelList);
        model.addAttribute("teamId", teamId);

        return "team/newPersonnelForTeam";
    }

    @GetMapping("/finalizeAdding/team/{tid}/personnel/{pid}")
    public String finalizePersonnelAdding(@PathVariable("tid") Long teamId,
                                          @PathVariable("pid") Long personnelId){
        teamService.assignPersonnelToTeamByTeamId(teamId, personnelId);

        return "redirect:/rng/t/teamMembers/" + teamId;
    }

    @GetMapping("/removePersonnel/{personnelId}")
    public String removePersonnel(@PathVariable Long personnelId){
//        personnelService.removePersonnel(personnelId);

        return "redirect:/rng/p/showPersonnel/" + personnelId;
    }
}
