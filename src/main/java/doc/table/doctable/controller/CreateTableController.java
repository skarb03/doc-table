package doc.table.doctable.controller;

import doc.table.doctable.service.CreateTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/table")
public class CreateTableController {

    @Autowired
    private CreateTableService createTableService;

    @GetMapping("/snapshotTable")
    public String snapshotTable(@RequestParam String ownerPlace){

        createTableService.snapshotTable(ownerPlace);
        return "redirect:/";
    }

    @GetMapping("/interfaceTable")
    public String interfaceTable(@RequestParam String ownerPlace){

        return "redirect:/";
    }

}
