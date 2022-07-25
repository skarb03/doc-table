package doc.table.doctable.controller;

import doc.table.doctable.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class IndexRestController {

    @Autowired
    ExcelService excelService;

    @PostMapping("/uploadFile")
    public ResponseEntity<Void> uploadFile(@RequestPart("excelFile")MultipartFile multipartFile) throws IOException {
        excelService.ExcelUpload(multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
