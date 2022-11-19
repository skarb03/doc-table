package doc.table.doctable.controller;

import doc.table.doctable.service.CreateTableService;
import doc.table.doctable.service.SqlFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/table")
public class CreateTableController {

    @Autowired
    private CreateTableService createTableService;

    @Autowired
    private SqlFileService sqlFileService;

    @GetMapping("/snapshotTable")
    public String snapshotTable(@RequestParam String ownerPlace){

        createTableService.snapshotTable(ownerPlace);
        return "redirect:/";
    }

    @GetMapping("/interfaceTable")
    public String interfaceTable(){
        List<String> sqlDataList = sqlFileService.getSqlDataList();
        sqlFileService.createFile(sqlDataList);
        return "redirect:/";
    }

//    @GetMapping("/interfaceTable")
//    public ResponseEntity<Resource> download(String param) throws IOException {
//
//        File file =
//
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentLength(file.length())
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(resource);
//    }

}
