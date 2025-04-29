package com.task_management_app.Task;

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
public class TaskService {

    Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepo taskRepo;

    public ResponseEntity<?> createTask(Task task) {
        return ResponseEntity.ok(taskRepo.save(task));
    }

    public ResponseEntity<?> updateTask(String id, Task task) {
        Optional<Task> existingTaskOptional = taskRepo.findById(id);

        if (existingTaskOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        Task existingTask = existingTaskOptional.get();
        existingTask.setAdminId(task.getAdminId());
        existingTask.setTask(task.getTask());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setSupervisorId(task.getSupervisorId());
        existingTask.setUserId(task.getUserId());
        existingTask.setProgress(task.getProgress());

        return ResponseEntity.ok(taskRepo.save(existingTask));
    }

    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskRepo.findAll());
    }

    public ResponseEntity<?> getTaskById(String id) {
        Optional<Task> task = taskRepo.findById(id);
        if (task.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        return ResponseEntity.ok(task.get());
    }

    public ResponseEntity<?> deleteTask(String id) {
        Optional<Task> task = taskRepo.findById(id);
        if (task.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        taskRepo.deleteById(id);
        return ResponseEntity.ok("Task successfully deleted");
    }

    public ResponseEntity<?> allTaskReport(HttpServletResponse response) throws IOException, DocumentException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Paragraph title = new Paragraph("Task Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        LocalDate now = LocalDate.now();
        Paragraph date = new Paragraph(String.valueOf(now), FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));
        date.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(date);

        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        String[] headers = {"Task", "Description", "Days", "Progress (%)"};
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            headerCell.setBorderWidth(0f);
            headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            headerCell.setPadding(10);
            table.addCell(headerCell);
        }

        List<Task> taskList = taskRepo.findAll();

        for (Task task : taskList) {
            try {
                addCell(table, task.getTask());
            } catch (Exception e) {
                addCell(table, "-");
                logger.error("Error setting Task Name");
            }

            try {
                addCell(table, task.getDescription());
            } catch (Exception e) {
                addCell(table, "-");
                logger.error("Error setting Description");
            }

            try {
                addCell(table, String.valueOf(task.getDueDate()));
            } catch (Exception e) {
                addCell(table, "-");
                logger.error("Error setting Days");
            }

            try {
                addCell(table, String.valueOf(task.getProgress()));
            } catch (Exception e) {
                addCell(table, "-");
                logger.error("Error setting Progress");
            }
        }

        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=All_Task_Report_" + new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(new Date()) + ".pdf";
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
