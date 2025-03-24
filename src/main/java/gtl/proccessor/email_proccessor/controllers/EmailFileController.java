package gtl.proccessor.email_proccessor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gtl.proccessor.email_proccessor.dto.EmailReqDto;
import gtl.proccessor.email_proccessor.shared.config.RabbitMqConfig;
import gtl.proccessor.email_proccessor.shared.utilities.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@CrossOrigin(origins = "http://localhost:4200")  // Allow requests from frontend
@RequestMapping("api/v1/em-pr")
@Validated
@Slf4j
@RequiredArgsConstructor
public class EmailFileController {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqConfig rabbitMqConfig;

    @ResponseBody
    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("File upload request: {}", file.getOriginalFilename());

        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                if(row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null) continue;

                String name = row.getCell(0).getStringCellValue();
                String email = row.getCell(1).getStringCellValue();
                String message = row.getCell(2).getStringCellValue();

                EmailReqDto emailRequest = new EmailReqDto(name, email, message);

                String jsonMessage = new ObjectMapper().writeValueAsString(emailRequest);

                rabbitTemplate.convertAndSend(rabbitMqConfig.EMAIL_QUEUE, jsonMessage);
            }
        } catch (Exception e) {
            log.error("Error while uploading file", e);
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "Error while uploading file", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "File uploaded successfully", null));
    }
}
