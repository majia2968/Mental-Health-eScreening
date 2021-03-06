package gov.va.escreening.controller.dashboard;

import gov.va.escreening.domain.ProgramDto;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.ProgramService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dashboard")
public class ProgramListViewController {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ProgramListViewController.class);

    private ProgramService programService;

    @Autowired
    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    /**
     * Initialize and setup page.
     * @param model
     * @return
     */
    @RequestMapping(value = "/programListView", method = RequestMethod.GET)
    public String setUpPageProgramListView(Model model) {

        List<ProgramDto> programList = programService.getProgramList();
        model.addAttribute("programList", programList);

        return "systemTab/programListView";
    }
    
    @RequestMapping(value = "/programList", method = RequestMethod.GET)
    @ResponseBody
    public List<DropDownObject> getProgramList(Model model) {

        List<DropDownObject> programList = programService.getDropdownObjects();
        return programList;
    }
}
