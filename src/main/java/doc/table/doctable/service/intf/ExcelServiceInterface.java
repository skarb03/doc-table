package doc.table.doctable.service.intf;



import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

public interface ExcelServiceInterface<T> {

    List<T> ExcelDataList(MultipartFile multipartFile) throws IOException;

    void saveExcelData(List<T> dataList) throws UnknownHostException;


}
