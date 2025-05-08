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

        // Fonts
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.DARK_GRAY);
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);

        // Title
        Paragraph title = new Paragraph("Job Applications Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);

        PdfPTable table = new PdfPTable(5); // Company, Role, Status, Applied At, URL
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setWidths(new float[] {2.5f, 2.5f, 1.5f, 2.5f, 2.5f});

        // Header row
        addHeaderCell(table, "Company", headerFont);
        addHeaderCell(table, "Role", headerFont);
        addHeaderCell(table, "Status", headerFont);
        addHeaderCell(table, "Applied At", headerFont);
        addHeaderCell(table, "URL", headerFont);

        // Data rows
        boolean alternate = false;
        Color rowColor1 = new Color(245, 245, 245);
        Color rowColor2 = new Color(255, 255, 255);

        for (Job job : jobs) {
            Color bgColor = alternate ? rowColor1 : rowColor2;

            addDataCell(table, job.getCompany(), cellFont, bgColor);
            addDataCell(table, job.getRole().getName(), cellFont, bgColor);
            addDataCell(table, job.getStatus().toString(), cellFont, bgColor);
            addDataCell(table, job.getAppliedAt().toString(), cellFont, bgColor);

            Anchor link = new Anchor("Link", cellFont);
            link.setReference(job.getUrl());
            PdfPCell linkCell = new PdfPCell(link);
            linkCell.setBackgroundColor(bgColor);
            linkCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            linkCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            linkCell.setPadding(5f);
            table.addCell(linkCell);

            alternate = !alternate;
        }

        document.add(table);
        document.close();
    }

    private static void addHeaderCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(new Color(63, 81, 181)); // Indigo
        cell.setPadding(8f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
    }

    private static void addDataCell(PdfPTable table, String text, Font font, Color backgroundColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(backgroundColor);
        cell.setPadding(5f);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
    }
}
