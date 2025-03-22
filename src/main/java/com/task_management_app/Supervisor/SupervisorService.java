package com.task_management_app.Supervisor;

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
public class SupervisorService {

    Logger logger = LoggerFactory.getLogger(SupervisorService.class);

    @Autowired
    private SupervisorRepo supervisorRepo;

    public ResponseEntity<?> createSupervisor(Supervisor supervisor) {
        if (supervisorRepo.findByEmail(supervisor.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        return ResponseEntity.ok(supervisorRepo.save(supervisor));
    }

    public ResponseEntity<?> updateSupervisor(String id, Supervisor supervisor) {
        Optional<Supervisor> existingSupervisorOptional = supervisorRepo.findById(id);

        if (existingSupervisorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supervisor not found");
        }

        Supervisor existingSupervisor = existingSupervisorOptional.get();
        existingSupervisor.setFirstName(supervisor.getFirstName());
        existingSupervisor.setLastName(supervisor.getLastName());
        existingSupervisor.setEmail(supervisor.getEmail());
        existingSupervisor.setPassword(supervisor.getPassword());
        existingSupervisor.setStatus(supervisor.getStatus());

        return ResponseEntity.ok(supervisorRepo.save(existingSupervisor));
    }

    public ResponseEntity<List<Supervisor>> getAllSupervisors() {
        return ResponseEntity.ok(supervisorRepo.findAll());
    }

    public ResponseEntity<?> getSupervisorById(String id) {
        Optional<Supervisor> supervisor = supervisorRepo.findById(id);
        if (supervisor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supervisor not found");
        }
        return ResponseEntity.ok(supervisor.get());
    }

    public ResponseEntity<?> deleteSupervisor(String id) {
        Optional<Supervisor> supervisor = supervisorRepo.findById(id);
        if (supervisor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supervisor not found");
        }
        supervisorRepo.deleteById(id);
        return ResponseEntity.ok("Supervisor successfully deleted");
    }

    public ResponseEntity<?> allSupervisorReport(HttpServletResponse response) throws IOException, DocumentException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // Title of the report
        Paragraph title = new Paragraph("Supervisor Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        // Date of the report
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        LocalDate now = LocalDate.now();
        Paragraph date = new Paragraph(String.valueOf(now), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));
        date.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(date);

        document.add(Chunk.NEWLINE);

        // Table for supervisor details
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Header row for the table
        String[] headers = {"Full Name", "E-mail", "Status", "Password"};
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            headerCell.setBorderWidth(0f);
            headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerCell.setPadding(10);
            table.addCell(headerCell);
        }

        List<Supervisor> supervisorList = supervisorRepo.findAll();

        // Adding supervisor details to the table
        for (Supervisor supervisor : supervisorList) {
            addCell(table, supervisor.getFirstName() + " " + supervisor.getLastName());
            addCell(table, supervisor.getEmail());
            addCell(table, supervisor.getStatus());
            addCell(table, supervisor.getPassword());
        }

        // Setting response content type for PDF
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=All_Supervisor_Report_" + new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(new Date()) + ".pdf";
        response.setHeader(headerKey, headerValue);

        // Adding the table to the document and closing it
        document.add(table);
        document.close();

        return ResponseEntity.ok().build();
    }

    private void addCell(PdfPTable table, String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content != null ? content : "-"));
        cell.setPadding(10);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        table.addCell(cell);
    }
}
