package com.task_management_app.Worker;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    Logger logger = LoggerFactory.getLogger(WorkerService.class);

    @Autowired
    private WorkerRepo workerRepo;

    public ResponseEntity<?> createWorker(Worker worker) {
        if (workerRepo.findByEmail(worker.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        return ResponseEntity.ok(workerRepo.save(worker));
    }

    public ResponseEntity<?> updateWorker(String id, Worker worker) {
        Optional<Worker> existingWorkerOptional = workerRepo.findById(id);

        if (existingWorkerOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Worker not found");
        }

        Worker existingWorker = existingWorkerOptional.get();
        existingWorker.setFirstName(worker.getFirstName());
        existingWorker.setLastName(worker.getLastName());
        existingWorker.setEmail(worker.getEmail());
        existingWorker.setPassword(worker.getPassword());
        existingWorker.setCountry(worker.getCountry());
        existingWorker.setStatus(worker.getStatus());
        existingWorker.setExpressionStatus(worker.getExpressionStatus());

        return ResponseEntity.ok(workerRepo.save(existingWorker));
    }

    public ResponseEntity<List<Worker>> getAllWorkers() {
        return ResponseEntity.ok(workerRepo.findAll());
    }

    public ResponseEntity<?> getWorkerById(String id) {
        Optional<Worker> worker = workerRepo.findById(id);
        if (worker.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Worker not found");
        }
        return ResponseEntity.ok(worker.get());
    }

    public ResponseEntity<?> deleteWorker(String id) {
        Optional<Worker> worker = workerRepo.findById(id);
        if (worker.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Worker not found");
        }
        workerRepo.deleteById(id);
        return ResponseEntity.ok("Worker successfully deleted");
    }

    public ResponseEntity<?> allWorkerReport(HttpServletResponse response) throws IOException, DocumentException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Paragraph title = new Paragraph("Worker Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        LocalDate now = LocalDate.now();
        Paragraph date = new Paragraph(String.valueOf(now), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));
        date.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(date);

        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        String[] headers = {"Full Name", "E-mail", "Password", "Status", "Expression Status"};
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            headerCell.setBorderWidth(0f);
            headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerCell.setPadding(10);
            table.addCell(headerCell);
        }

        List<Worker> workerList = workerRepo.findAll();

        for (Worker worker : workerList) {
            try {
                addCell(table, worker.getFirstName() + " " + worker.getLastName());
            } catch (Exception e) {
                addCell(table, "-");
                logger.error("Error setting Full Name");
            }

            try {
                addCell(table, worker.getEmail());
            } catch (Exception e) {
                addCell(table, "-");
                logger.error("Error setting E-mail");
            }

            try {
                addCell(table, worker.getPassword());
            } catch (Exception e) {
                addCell(table, "-");
                logger.error("Error setting password");
            }

            try {
                addCell(table, worker.getStatus());
            } catch (Exception e) {
                addCell(table, "-");
                logger.error("Error setting Status");
            }

            try {
                addCell(table, worker.getExpressionStatus());
            } catch (Exception e) {
                addCell(table, "-");
                logger.error("Error setting Expression Status");
            }
        }

        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=All_Worker_Report" + new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(new Date()) + ".pdf";
        response.setHeader(headerKey, headerValue);

        document.add(table);
        document.close();

        return ResponseEntity.ok().build();
    }

    private void addCell(PdfPTable table, String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content != null ? content : "-"));
        cell.setPadding(20);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        table.addCell(cell);
    }
}