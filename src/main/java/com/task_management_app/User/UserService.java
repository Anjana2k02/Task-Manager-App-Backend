package com.task_management_app.User;

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
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepo userRepo;

    public ResponseEntity<?> createUser(User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        return ResponseEntity.ok(userRepo.save(user));
    }

    public ResponseEntity<?> updateUser(String id, User user) {
        Optional<User> existingUserOptional = userRepo.findById(id);

        if (existingUserOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User existingUser = existingUserOptional.get();
        existingUser.setFirstName(user.getFirstName());
        existingUser.setSecondName(user.getSecondName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        return ResponseEntity.ok(userRepo.save(existingUser));
    }

    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    public ResponseEntity<?> getUserById(String id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(user.get());
    }

    public ResponseEntity<?> deleteUser(String id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userRepo.deleteById(id);
        return ResponseEntity.ok("User successfully deleted");
    }

    public List<User> getUsersByDevType(int devType) {
        return userRepo.findByDevType(devType);
    }


    public ResponseEntity<?> allUserReport(HttpServletResponse response) throws IOException, DocumentException {

//        Document document = new Document(PageSize.A3.rotate());
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Paragraph title = new Paragraph("User Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        LocalDate now = LocalDate.now();

        Paragraph date = new Paragraph(String.valueOf(now), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));
        date.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(date);


        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(3); 
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        String[] headers = {"Full Name", "E-mail", "Country", "Dev Type"};
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            headerCell.setBorderWidth(0f);
            headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerCell.setPadding(10);
            table.addCell(headerCell);
        }



        List<User> userList = userRepo.findAll();

        for (int i=0; i< userList.size(); i++){


            try {
                addCell(table, userList.get(i).getFirstName() + " " + userList.get(i).getSecondName() );
            } catch (Exception e) {
                addCell(table, "-");
                logger.error("Error setting Name");
            }

            try {
//                addCell(table, String.valueOf(userList.get(i).getEmail())); if number use this method
                addCell(table, userList.get(i).getEmail());
            } catch (Exception e) {
                addCell(table, "-");
                logger.error("Error setting E-mail");
            }

            try {
                addCell(table, userList.get(i).getCountry());
            } catch (Exception e) {
                addCell(table, "-");
                logger.error("Error setting country");
            }

            try {
                int devType = userList.get(i).getDevType();
                addCell(table, String.valueOf(devType));
            } catch (NullPointerException e) {
                addCell(table, "-");
                logger.error("Error setting dev type: Value is null");
            }

        }

        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=All_User_Report" + new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(new Date()) + ".pdf";
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
