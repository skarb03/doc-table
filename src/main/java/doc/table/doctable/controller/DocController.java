package doc.table.doctable.controller;

import doc.table.doctable.entity.TableInfo;
import doc.table.doctable.repository.StandardDictRepository;
import doc.table.doctable.repository.TableInfoRepository;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RequestMapping("/doc")
@RestController
public class DocController {

    @Autowired
    private TableInfoRepository tableInfoRepository;

    @Autowired
    private StandardDictRepository standardDictRepository;

    @GetMapping("interface")
    public void interfaceDownload(@RequestParam String fileName, HttpServletResponse res) throws IOException {

        ClassPathResource resource = new ClassPathResource("/src/main/resources/excelFile/interface.xlsx");
        FileInputStream fis=new FileInputStream(new File(resource.getPath()));
        ZipSecureFile.setMinInflateRatio(0);
        Workbook workbook = new XSSFWorkbook(fis);

        List<TableInfo> tableInfos =  tableInfoRepository.findAll();

        Sheet sheet =workbook.getSheetAt(2);
        Sheet sheet2 =workbook.cloneSheet(2);


        for(int i = 17 ; i<30;i++){
            Row row =sheet2.createRow(i);

            row.createCell(0).setCellValue(i);

        }


        res.setContentType("application/vnd.ms-excel");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName+".xlsx");
        try {
            workbook.write(res.getOutputStream());
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            fis.close();
            workbook.close();
        }
//        return null;
    }
}
