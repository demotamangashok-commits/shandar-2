package com.example.shandar.service;

import com.example.shandar.model.CustomerOrder;
import com.example.shandar.model.OrderItem;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.print.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class KotPrinterService {

    public void printKot(CustomerOrder order, List<OrderItem> items) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new KotPrintable(order, items));

        // --- NEW: Open the Windows Printer Selection Dialog ---
        // If the user clicks "Print" in the popup, this returns true.
        // If they click "Cancel" or close the window, it returns false.
        if (job.printDialog()) {
            try {
                job.print();
                System.out.println("KOT successfully sent to printer!");
            } catch (PrinterException e) {
                System.err.println("Printer error: " + e.getMessage());
            }
        } else {
            System.out.println("Print job cancelled by user.");
        }
    }

    // The receipt drawing logic remains exactly the same
    private static class KotPrintable implements Printable {
        private CustomerOrder order;
        private List<OrderItem> items;

        public KotPrintable(CustomerOrder order, List<OrderItem> items) {
            this.order = order;
            this.items = items;
        }

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if (pageIndex > 0) return NO_SUCH_PAGE;

            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            Font font = new Font("Monospaced", Font.BOLD, 12);
            g2d.setFont(font);

            int y = 20;

            g2d.drawString("=== KITCHEN ORDER TICKET ===", 10, y);
            y += 20;
            g2d.drawString("Table: " + order.getTableNumber(), 10, y);
            y += 15;
            g2d.drawString("Time : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), 10, y);
            y += 20;
            g2d.drawString("------------------------------", 10, y);

            for (OrderItem item : items) {
                y += 15;
                g2d.drawString(item.getQuantity() + "x  " + item.getMenuItem().getName(), 10, y);
            }

            y += 20;
            g2d.drawString("------------------------------", 10, y);

            return PAGE_EXISTS;
        }
    }
}