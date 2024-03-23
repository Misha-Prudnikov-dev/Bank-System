package com.project.bank.service.impl.util;


import com.project.bank.entity.Account;
import com.project.bank.entity.Transaction;
import com.project.bank.entity.User;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * BankPdf has methods for creating a PDF document.
 */
public class BankPdf {

    private static final String PATH_ROOT_CHECK = "check/" ;
    private static final String FILE_PDF = ".pdf" ;
    private static final String PATH_FILE_FONT = "src/main/resources/arnamu.ttf" ;
    private static final String BANK_CHECK = "Bank check" ;
    private static final String TRANSACTION_ID = "Check: ";
    private static final String TRANSACTION_TYPE = "Type Transaction: ";
    private static final String TRANSACTION_DATE = "Date Transaction: ";
    private static final String TRANSACTION_AMOUNT = "Amount: ";
    private static final String TRANSACTION_CURRENCY = "Currency: ";
    private static final String TO_USER_ACCOUNT_ID = "To: ";
    private static final String TO_USER_FIRST_NAME = "Recipient first name: ";
    private static final String TO_USER_LAST_NAME = "Recipient last name:";
    private static final String BANK_INVOICE = "Invoice";
    private static final String USER_INFO = "Client: ";
    private static final String ACCOUNT_ID = "Account Id: ";
    private static final String ACCOUNT_CREATED = "Account created: ";
    private static final String ACCOUNT_BALANCE = "Account balance: ";
    private static final String PATH_ROOT_INVOICE = "invoice/";
    private static final String FROM_USER_ACCOUNT_ID = "from: ";
    private static final String FROM_USER_FIRST_NAME = "Deposit from first name: ";
    private static final String FROM_USER_LAST_NAME = "Deposit from last name";
    private static final String TRANSACTION_DETAIL = "Details";
    private static final String BANK_ID = "Bank Id: ";
    private static final String BANK_NAME = "Bank Name: ";


    /**
     * The method generates a transaction receipt
     * @param transaction The transaction object containing transaction information.
     * @return the file path
     * @throws IOException  If an error occurs while forming  pdf.
     */
    public String formCheckPdf(Transaction transaction) throws IOException {
        PDDocument doc = new  PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(doc,page);
        contentStream.beginText();

        PDFont font = PDType0Font.load(doc,new File(PATH_FILE_FONT));
        contentStream.setFont(font, 16);
        contentStream.setLeading(24.5f);

        contentStream.newLineAtOffset(50, 700);
        contentStream.showText("------------------------------------------------------------------");
        contentStream.newLine();
        contentStream.newLineAtOffset(100, 0);
        contentStream.showText(BANK_CHECK);
        contentStream.newLine();
        contentStream.newLineAtOffset(-100, 0);
        contentStream.showText(TRANSACTION_ID);
        contentStream.newLineAtOffset(200, 0);
        contentStream.showText(String.valueOf(transaction.getId()));
        contentStream.newLine();
        contentStream.newLineAtOffset(-200, 0);
        contentStream.showText(TRANSACTION_DATE);
        contentStream.newLineAtOffset(200, 0);
        contentStream.showText(String.valueOf(transaction.getDateTransaction().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        contentStream.newLine();
        contentStream.newLineAtOffset(-200,0);
        contentStream.showText(TRANSACTION_TYPE);
        contentStream.newLineAtOffset(200,0);
        contentStream.showText(transaction.getTransactionType().getName());
        contentStream.newLine();

        contentStream.newLineAtOffset(-200,0);
        contentStream.showText(TRANSACTION_AMOUNT);
        contentStream.newLineAtOffset(200,0);
        contentStream.showText(String.valueOf(transaction.getAmount()));
        contentStream.newLine();

        contentStream.newLineAtOffset(-200,0);
        contentStream.showText(TRANSACTION_CURRENCY);
        contentStream.newLineAtOffset(200,0);
        contentStream.showText(transaction.getCurrency().getName());
        contentStream.newLine();

        if (transaction.getToUserShortInfo()!=null) {
            contentStream.newLineAtOffset(-200, 0);
            contentStream.showText(TO_USER_ACCOUNT_ID);
            contentStream.newLineAtOffset(200, 0);
            contentStream.showText(String.valueOf(transaction.getToUserShortInfo().getAccountId()));
            contentStream.newLine();
            contentStream.newLineAtOffset(-200,0);
            contentStream.showText(TO_USER_FIRST_NAME);
            contentStream.newLineAtOffset(200,0);
            contentStream.showText(transaction.getToUserShortInfo().getFirstName());
            contentStream.newLine();
            contentStream.newLineAtOffset(-200,0);
            contentStream.showText(TO_USER_LAST_NAME);
            contentStream.newLineAtOffset(200,0);
            contentStream.showText(transaction.getToUserShortInfo().getLastName());
            contentStream.newLine();
        }
        contentStream.newLineAtOffset(-200,0);
        contentStream.showText("------------------------------------------------------------------");
        contentStream.endText();
        contentStream.close();

        String file= PATH_ROOT_CHECK+transaction.getId()+"_"+transaction.getDateTransaction().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss"))+FILE_PDF;

        doc.save(file);
        doc.close();

        return file;
    }

    /**
     * The method generates a transaction receipt
     * @param transaction The transaction object containing transaction information.
     * @return the file path
     * @throws IOException  If an error occurs while forming  pdf.
     */
    public String formCheckPdf3(Transaction transaction) throws IOException {
        final float fontSize = 16f;
        final float leading = 24.5f;
        final float xOffset = 50;
        final float yOffsetStart = 700;
        final float xOffsetValue = 270;

        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {
            contentStream.setFont(PDType0Font.load(doc, new File(PATH_FILE_FONT)), fontSize);
            contentStream.setLeading(leading);

            contentStream.beginText();
            contentStream.newLineAtOffset(xOffsetValue/2,yOffsetStart+40);
            contentStream.showText(BANK_CHECK);
            contentStream.newLineAtOffset(-xOffsetValue,yOffsetStart);
            contentStream.newLine();
            contentStream.endText();

            String[][] details = {
                    {TRANSACTION_ID, String.valueOf(transaction.getId())},
                    {TRANSACTION_DATE, transaction.getDateTransaction().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))},
                    {TRANSACTION_TYPE, transaction.getTransactionType().getName()},
                    {TRANSACTION_AMOUNT, String.valueOf(transaction.getAmount())},
                    {TRANSACTION_CURRENCY, transaction.getCurrency().getName()}
            };

            if (transaction.getToUserShortInfo() != null) {
                details = Arrays.copyOf(details, details.length + 3);
                details[details.length - 3] = new String[]{TO_USER_ACCOUNT_ID, String.valueOf(transaction.getToUserShortInfo().getAccountId())};
                details[details.length - 2] = new String[]{TO_USER_FIRST_NAME, transaction.getToUserShortInfo().getFirstName()};
                details[details.length - 1] = new String[]{TO_USER_LAST_NAME, transaction.getToUserShortInfo().getLastName()};
            }
            float yOffset = yOffsetStart;

            for (String[] detail : details) {
                contentStream.beginText();
                contentStream.newLineAtOffset(xOffset, yOffset);
                contentStream.showText(detail[0]);
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(xOffsetValue, yOffset);
                contentStream.showText(detail[1]);
                contentStream.newLine();
                contentStream.endText();
                yOffset -= leading;
            }
            contentStream.moveTo(xOffset, yOffset);
            contentStream.lineTo(xOffset + xOffsetValue, yOffset);
            contentStream.stroke();
        }
        String file = PATH_ROOT_CHECK + transaction.getId() + "_" +
                transaction.getDateTransaction().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss")) + FILE_PDF;

        doc.save(file);
        doc.close();

        return file;
    }

    /**
     * The method generates invoice about all transactions
     * @param user The user object containing user information.
     * @param account  The account object containing account information.
     * @return the file path
     * @throws IOException  If an error occurs while forming pdf.
     */
    public String invoicePdf2(User user,Account account) throws IOException {
        int countLine=0;
        PDDocument doc = new  PDDocument();
        PDPage page = new PDPage(PDRectangle.A3);
        doc.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(doc,page);
        contentStream.beginText();

        PDFont font = PDType0Font.load(doc,new File(PATH_FILE_FONT));
        contentStream.setFont(font, 12);
        contentStream.setLeading(24.5f);

        contentStream.newLineAtOffset(50, 1100);
        contentStream.newLineAtOffset(350, 0);
        contentStream.showText(BANK_INVOICE);

        contentStream.newLine();
        contentStream.newLineAtOffset(-100, 0);
        contentStream.showText(USER_INFO);
        contentStream.newLineAtOffset(200, 0);
        contentStream.showText(user.getFirstName()+" "+user.getLastName());

        contentStream.newLine();
        contentStream.newLineAtOffset(-200, 0);
        contentStream.showText(ACCOUNT_ID);
        contentStream.newLineAtOffset(200, 0);
        contentStream.showText(String.valueOf(account.getId()));

        contentStream.newLine();
        contentStream.newLineAtOffset(-200, 0);
        contentStream.showText(TRANSACTION_CURRENCY);
        contentStream.newLineAtOffset(200, 0);
        contentStream.showText(account.getCurrency().getName());

        contentStream.newLine();
        contentStream.newLineAtOffset(-200, 0);
        contentStream.showText(ACCOUNT_CREATED);
        contentStream.newLineAtOffset(200, 0);
        contentStream.showText(String.valueOf(account.getCreatedDate()));

        contentStream.newLine();
        contentStream.newLineAtOffset(-200, 0);
        contentStream.showText(ACCOUNT_BALANCE);
        contentStream.newLineAtOffset(200, 0);
        contentStream.showText(String.valueOf(account.getBalance()));
        contentStream.newLine();

        contentStream.newLineAtOffset(-200, 0);
        contentStream.showText(BANK_ID);
        contentStream.newLineAtOffset(200, 0);
        contentStream.showText(String.valueOf(account.getBank().getId()));
        contentStream.newLine();

        contentStream.newLineAtOffset(-200, 0);
        contentStream.showText(BANK_NAME);
        contentStream.newLineAtOffset(200, 0);
        contentStream.showText(String.valueOf(account.getBank().getName()));
        contentStream.newLine();

        contentStream.newLineAtOffset(-450,0);


        tableTransactionPrint(contentStream);




        for (Transaction transaction : account.getTransactions()) {

            if (countLine>=20){
                contentStream.endText();
                contentStream.close();
                countLine=0;

                page = new PDPage(PDRectangle.A3);
                doc.addPage(page);

                contentStream = new PDPageContentStream(doc,page);
                font = PDType0Font.load(doc,new File(PATH_FILE_FONT));
                contentStream.setFont(font, 12);
                contentStream.setLeading(24.5f);
                contentStream.beginText();
                contentStream.newLineAtOffset(250, 700);

                tableTransactionPrint(contentStream);

            }


            contentStream.showText(String.valueOf(transaction.getId()));
            contentStream.newLineAtOffset(100, 0);
            contentStream.showText(String.valueOf(transaction.getDateTransaction().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            contentStream.newLineAtOffset(170, 0);
            contentStream.showText(transaction.getTransactionType().getName());
            contentStream.newLineAtOffset(170, 0);
            contentStream.showText(String.valueOf(transaction.getAmount()));
            contentStream.newLineAtOffset(100, 0);
            contentStream.showText(transaction.getCurrency().getName());


            if (transaction.getToUserShortInfo() != null && transaction.getToUserShortInfo().getToAccountId()!=account.getId()) {
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText(TO_USER_ACCOUNT_ID);
                contentStream.newLineAtOffset(30, 0);
                contentStream.showText(String.valueOf(transaction.getToUserShortInfo().getToAccountId()));
                contentStream.newLineAtOffset(30, 0);
                contentStream.showText(transaction.getToUserShortInfo().getFirstName());
                contentStream.newLineAtOffset(30, 0);
                contentStream.showText(transaction.getToUserShortInfo().getLastName());
                contentStream.newLineAtOffset(-190,0);

            } else {
                if (transaction.getToUserShortInfo()!=null) {
                    contentStream.newLineAtOffset(100, 0);
                    contentStream.showText(FROM_USER_ACCOUNT_ID);
                    contentStream.newLineAtOffset(30, 0);
                    contentStream.showText(String.valueOf(transaction.getToUserShortInfo().getAccountId()));
                    contentStream.newLineAtOffset(30, 0);
                    contentStream.showText(transaction.getToUserShortInfo().getFirstName());
                    contentStream.newLineAtOffset(30, 0);
                    contentStream.showText(transaction.getToUserShortInfo().getLastName());
                    contentStream.newLineAtOffset(-190,0);
                }
            }
            countLine=countLine+1;
            contentStream.newLine();
            contentStream.newLineAtOffset(-540,0);
        }
        contentStream.endText();
        contentStream.close();

        String file= PATH_ROOT_INVOICE+account.getId()+"-"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss")) +FILE_PDF;

        doc.save(file);
        doc.close();

        return file;
    }

    /**
     * The method generates table for invoice.
     * @throws IOException  If an error occurs while forming table.
     */
    private void tableTransactionPrint(PDPageContentStream contentStream) throws IOException {
         contentStream.showText("-".repeat(215));
         contentStream.newLineAtOffset(200, 0);
         contentStream.newLine();
         contentStream.newLineAtOffset(-200, 0);
         contentStream.showText(TRANSACTION_ID);
         contentStream.newLineAtOffset(100, 0);

         contentStream.showText(TRANSACTION_DATE);
         contentStream.newLineAtOffset(170, 0);
         contentStream.showText(TRANSACTION_TYPE);
         contentStream.newLineAtOffset(170, 0);

         contentStream.showText(TRANSACTION_AMOUNT);
         contentStream.newLineAtOffset(100, 0);
         contentStream.showText(TRANSACTION_CURRENCY);
         contentStream.newLineAtOffset(140, 0);
         contentStream.showText(TRANSACTION_DETAIL);
         contentStream.newLine();
         contentStream.newLineAtOffset(-680, 0);
         contentStream.showText("-".repeat(215));
         contentStream.newLine();

     }

}

