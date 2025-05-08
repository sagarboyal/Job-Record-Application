package com.main.app.util;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import com.main.app.entity.Job;

import java.awt.*;
import java.io.OutputStream;
import java.util.List;

public class PdfExportUtil {
    public static void exportJobsToPdf(List<Job> jobs, OutputStream outputStream) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        Paragraph title = new Paragraph("Job Applications", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(5); // 5 columns: Company, Role, Status, Applied At, URL
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        // Table headers
        addTableHeader(table, "Company", headerFont);
        addTableHeader(table, "Role", headerFont);
        addTableHeader(table, "Status", headerFont);
        addTableHeader(table, "Applied At", headerFont);
        addTableHeader(table, "URL", headerFont);

        // Table data
        for (Job job : jobs) {
            table.addCell(new Phrase(job.getCompany(), cellFont));
            table.addCell(new Phrase(job.getRole().getName(), cellFont));
            table.addCell(new Phrase(job.getStatus().toString(), cellFont));
            table.addCell(new Phrase(job.getAppliedAt().toString(), cellFont));

            Anchor link = new Anchor("Link", cellFont);
            link.setReference(job.getUrl());
            table.addCell(link);
        }

        document.add(table);
        document.close();
    }

    private static void addTableHeader(PdfPTable table, String header, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPhrase(new Phrase(header, font));
        table.addCell(cell);
    }
}
