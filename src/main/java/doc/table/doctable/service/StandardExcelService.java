package doc.table.doctable.service;

import doc.table.doctable.entity.StandardDict;
import doc.table.doctable.enumType.StandardColumnType;
import doc.table.doctable.repository.LogDataRepository;
import doc.table.doctable.repository.StandardDictRepository;
import doc.table.doctable.service.intf.ExcelServiceInterface;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

@Service
public class StandardExcelService implements ExcelServiceInterface<StandardDict> {

    @Autowired
    private StandardDictRepository standardDictRepository;

    @Autowired
    private LogDataRepository logDataRepository;

    @Override
    public List<StandardDict> ExcelDataList(MultipartFile multipartFile) throws IOException {
        Workbook workbook = null;
        List<StandardDict> standardDictList = new LinkedList<>();

        try {
            workbook = new XSSFWorkbook(multipartFile.getInputStream());
            Sheet worksheet = workbook.getSheetAt(StandardColumnType.READ_SHEET_LOCATION.getValue());

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4
                Row prevRow = worksheet.getRow(i - 1);
                Row row = worksheet.getRow(i);

                standardDictList.add(
                        StandardDict.builder()
                                .standardLogicsLang(getValue(row,StandardColumnType.STANDARD_LOGICS_LANG.getValue()))
                                .standardPhysicsLang(getValue(row,StandardColumnType.STANDARD_PHYSICS_LANG.getValue()))
                                .standardDomainName(getValue(row,StandardColumnType.STANDARD_DOMAIN_NAME.getValue()))
                                .dataType(getValue(row,StandardColumnType.DATA_TYPE.getValue()))
                                .length(getValue(row,StandardColumnType.LENGTH.getValue()))
                                .originLogicalName(getValue(row,StandardColumnType.ORIGIN_LOGICAL_NAME.getValue()))
                                .build()
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
        return standardDictList;
    }

    private String getValue(Row row, int index ){

        switch (row.getCell(index).getCellType()){
            case NUMERIC:
                return String.valueOf(row.getCell(index).getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(row.getCell(index).getBooleanCellValue());
            default:
                return row.getCell(index).getStringCellValue();
        }
    }

    @Override
    public void saveExcelData(List<StandardDict> dataList) throws UnknownHostException {
        standardDictRepository.deleteAll();
        standardDictRepository.saveAll(dataList);
    }
}
