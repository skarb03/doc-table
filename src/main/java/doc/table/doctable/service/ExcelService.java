package doc.table.doctable.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ExcelService {

    public void ExcelUpload(MultipartFile multipartFile) throws IOException {

        Workbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
        Sheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4
//
            Row row = worksheet.getRow(i);

        }
//
//            ExcelData data = new ExcelData();
//
//            data.setNum((int) row.getCell(0).getNumericCellValue());
//            data.setName(row.getCell(1).getStringCellValue());
//            data.setEmail(row.getCell(2).getStringCellValue());
//
//            dataList.add(data);
//        }
//
//        model.addAttribute("datas", dataList); // 5

    }
}
