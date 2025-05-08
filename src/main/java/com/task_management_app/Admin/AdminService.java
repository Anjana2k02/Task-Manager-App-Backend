package com.task_management_app.Admin;

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
public class AdminService {

    Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private AdminRepo adminRepo;

    public ResponseEntity<?> createAdmin(Admin admin) {
        if (adminRepo.findByEmail(admin.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        return ResponseEntity.ok(adminRepo.save(admin));
    }

    public ResponseEntity<?> updateAdmin(String id, Admin admin) {
        Optional<Admin> existingAdminOptional = adminRepo.findById(id);

        if (existingAdminOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
        }

        Admin existingAdmin = existingAdminOptional.get();
        existingAdmin.setFirstName(admin.getFirstName());
        existingAdmin.setLastName(admin.getLastName());
        existingAdmin.setEmail(admin.getEmail());
        existingAdmin.setPassword(admin.getPassword());
        existingAdmin.setStatus(admin.getStatus());

        return ResponseEntity.ok(adminRepo.save(existingAdmin));
    }

    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminRepo.findAll());
    }

    public ResponseEntity<?> getAdminById(String id) {
        Optional<Admin> Admin = adminRepo.findById(id);
        if (Admin.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
        }
        return ResponseEntity.ok(Admin.get());
    }

    public ResponseEntity<?> deleteAdmin(String id) {
        Optional<Admin> Admin = adminRepo.findById(id);
        if (Admin.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
        }
        adminRepo.deleteById(id);
        return ResponseEntity.ok("Admin successfully deleted");
    }

             //Creat Report

        public ResponseEntity<?> allAdminReport(HttpServletResponse response) throws IOException, DocumentException {

//        Document document = new Document(PageSize.A3.rotate());
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Paragraph title = new Paragraph("Admin Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

       // SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        LocalDate now = LocalDate.now();

        Paragraph date = new Paragraph(String.valueOf(now), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));
        date.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(date);


        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(3); //Column 
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        String[] headers = {"Full Name", "E-mail", "Password"};
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            headerCell.setBorderWidth(0f);
            headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerCell.setPadding(10);
            table.addCell(headerCell);
        }



        List<Admin> adminList = adminRepo.findAll();

        for (int i=0; i< adminList.size(); i++){


            try {
                addCell(table, adminList.get(i).getFirstName() + " " + adminList.get(i).getLastName() );
            } catch (Exception e) {
                addCell(table, "-");
                logger.error("Error setting Name");
            }

            try {
//                addCell(table, String.valueOf(userList.get(i).getEmail())); if number use this method
                addCell(table, adminList.get(i).getEmail());
            } catch (Exception e) {
                addCell(table, "-");
                logger.error("Error setting E-mail");
            }

            try {
                addCell(table, adminList.get(i).getPassword());
            } catch (Exception e) {
                addCell(table, "-");
                logger.error("Error setting password");
            }
        }

        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=All_Admin_Report" + new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(new Date()) + ".pdf";
        response.setHeader(headerKey, headerValue);

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

