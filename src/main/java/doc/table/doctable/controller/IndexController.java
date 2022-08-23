package doc.table.doctable.controller;

import doc.table.doctable.entity.LogData;
import doc.table.doctable.enumType.ExcelType;
import doc.table.doctable.repository.LogDataRepository;
import doc.table.doctable.service.TableInfoExcelService;
import doc.table.doctable.service.StandardExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class IndexController {

    @Autowired
    TableInfoExcelService excelService;

    @Autowired
    StandardExcelService standardExcelService;

    @Autowired
    LogDataRepository logDataRepository;

    @RequestMapping("/index")
    public String index(){
        return "index.html";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart("excelFile") MultipartFile multipartFile, Model model, HttpServletRequest request) throws IOException {
        if(multipartFile.isEmpty()) {
            return "redirect:/index";
        }

        excelService.saveExcelData(excelService.ExcelDataList(multipartFile));
        logDataRepository.save(LogData.builder()
                .excelType(ExcelType.STANDARD)
                .ipAddress(request.getRemoteAddr())
                .build());
        return "redirect:/movePrinting";
    }

    @PostMapping("/standardData")
    public String stanadrdData(@RequestPart("standardDataFile") MultipartFile multipartFile, Model model, HttpServletRequest request) throws IOException {
        if(multipartFile.isEmpty()) {
            return "redirect:/index";
        }

        standardExcelService.saveExcelData(standardExcelService.ExcelDataList(multipartFile));
        logDataRepository.save(LogData.builder()
                .excelType(ExcelType.STANDARD)
                .ipAddress(request.getRemoteAddr())
                .build());
        return "redirect:/movePrinting";
    }

    @PostMapping("/tobeList")
    public String tobeList(@RequestPart("tobeListFile") MultipartFile multipartFile, Model model, HttpServletRequest request) throws IOException {
        if(multipartFile.isEmpty()) {
            return "redirect:/index";
        }

        standardExcelService.saveExcelData(standardExcelService.ExcelDataList(multipartFile));
        logDataRepository.save(LogData.builder()
                .excelType(ExcelType.TOBE_LIST)
                .ipAddress(request.getRemoteAddr())
                .build());
        return "redirect:/movePrinting";
    }

    @GetMapping("/movePrinting")
    public String movePrinting(Model model){
        model.addAttribute("dataList",logDataRepository.findAll(Sort.by(Sort.Direction.DESC,"regTimestamp")));
        return "printing.html";
    }
}
