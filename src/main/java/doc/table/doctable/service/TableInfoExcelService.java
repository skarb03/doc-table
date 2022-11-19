package doc.table.doctable.service;

import doc.table.doctable.entity.ColumnInfo;
import doc.table.doctable.entity.TableInfo;
import doc.table.doctable.enumType.TableColumnType;
import doc.table.doctable.repository.TableInfoRepository;
import doc.table.doctable.service.intf.ExcelServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class TableInfoExcelService implements ExcelServiceInterface<TableInfo> {

    @Autowired
    TableInfoRepository tableInfoRepository;

    @Override
    public List<TableInfo> ExcelDataList(MultipartFile multipartFile) throws IOException {

        Workbook workbook = null;
        List<TableInfo> tableInfoList = new LinkedList<>();
        try {
            workbook = new XSSFWorkbook(multipartFile.getInputStream());
            Sheet worksheet = workbook.getSheetAt(TableColumnType.READ_SHEET_LOCATION.getValue());

            long tablePk = 0;
            TableInfo tableInfo = null;

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4
                Row prevRow = worksheet.getRow(i-1);
                Row row = worksheet.getRow(i);

                if(!getRowValue(prevRow,TableColumnType.PHY_TAB_NAME.getValue()).equals(getRowValue(row, TableColumnType.PHY_TAB_NAME.getValue()))){

                    tablePk++;
                    tableInfo = TableInfo.builder()
                            .tableId(tablePk)
                            .physicalName(getRowValue(row, TableColumnType.PHY_TAB_NAME.getValue()))
                            .logicalName(getRowValue(row, TableColumnType.LOGICAL_TAB_NAME.getValue()))
                            .intfPhsicslName(getRowValue(row,TableColumnType.INTF_NUMBER.getValue()))
                            .tobePhysicalName(getRowValue(row,TableColumnType.TOBE_TABLE_NAME.getValue()))
                            .createYn(getRowValue(row,TableColumnType.CREATE_YN.getValue()))
                            .columnInfoList(new LinkedList<>())
                            .build();
                    tableInfoList.add(tableInfo);
                }
                if(tableInfo==null) {
                    continue;
                }
                tableInfo.getColumnInfoList().add(
                        ColumnInfo.builder()
                                .physicalName(getRowValue(row,TableColumnType.PHY_COL_NAME.getValue()))
                                .logicalName(getRowValue(row,TableColumnType.LOGIC_COL_NAME.getValue()))
                                .dataType(getRowValue(row,TableColumnType.DATA_TYPE.getValue()))
                                .length(getRowValue(row,TableColumnType.LENGTH.getValue()))
                                .tableId(tablePk)
                                .precision(getRowValue(row,TableColumnType.PRECISION.getValue()))
                                .scale(getRowValue(row,TableColumnType.SCALE.getValue()))
                                .pk(getRowValue(row,TableColumnType.PK_YN.getValue()))
                                .connYn(getRowValue(row,TableColumnType.CONN_YN.getValue()))
                                .nullable(getRowValue(row,TableColumnType.NULLABLE.getValue()))
                                .defaultValue(getRowValue(row,TableColumnType.DEFAULT.getValue()))
                                .displayOrder(getRowValue(row,TableColumnType.ORDER.getValue()))
                                .build()
                );

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
        return tableInfoList;
    }

    @Override
    public void saveExcelData(List<TableInfo> dataList) throws UnknownHostException {
        tableInfoRepository.deleteAll();
        tableInfoRepository.saveAll(dataList);

    }

    private String getRowValue(Row row, int index){

        Cell cell = isNumericValue(row,index);

        if(cell==null)
            return null;

        if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        }

        return cell.getStringCellValue();
    }

    private Cell isNumericValue(Row row, int index){
        if("NUMERIC".equals(row.getCell(TableColumnType.DATA_TYPE.getValue()).getStringCellValue())
        || "NUMBER".equals(row.getCell(TableColumnType.DATA_TYPE.getValue()).getStringCellValue())){

            if(index == TableColumnType.LENGTH.getValue()){
                return null;
            } else if(index == TableColumnType.PRECISION.getValue()){
                return row.getCell(TableColumnType.LENGTH.getValue());
            } else if(index == TableColumnType.DATA_TYPE.getValue()){
                Cell cell = row.getCell(TableColumnType.DATA_TYPE.getValue());
                cell.setCellValue("NUMBER");
                return cell;
            }
        }

        return row.getCell(index);
    }

    public List<TableInfo> readTOBEExcel(MultipartFile multipartFile){
        return null;
    }

    public void updateExcelData(List<TableInfo> tableInfoList){
        for(TableInfo tableInfo : tableInfoList) {
            tableInfoRepository.findTableInfoByLogicalNameForUpdate(tableInfo.getLogicalName());
        }
    }

}
