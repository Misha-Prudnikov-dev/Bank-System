package com.project.bank;


import com.project.bank.entity.*;
import com.project.bank.service.*;
import com.project.bank.service.impl.util.BankPdf;
import com.project.bank.service.impl.util.EmailSending;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;


public class App {
    public static void main( String[] args ) {

        try {
            InterestCalculator interestCalculator = new InterestCalculator();
            interestCalculator.startInterestCalculation();
            consoleUI();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

    }
    public static void consoleUI() throws IOException, ServiceException {

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AccountService accountService = serviceFactory.getAccountService();
        BankService bankService = serviceFactory.getBankService();
        UserService userService = serviceFactory.getUserService();
        BankPdf bankPdf = new BankPdf();
        Scanner sc = new Scanner(System.in);

        User user;
        int choice;
        outer:
        while (true) {

            System.out.println("\n-------------------");
            System.out.println(" UNION BANK ");
            System.out.println("-------------------\n");
            System.out.println("1. Registrar cont.");
            System.out.println("2. Login.");
            System.out.println("3. Exit.");
            System.out.print("\nEnter your choice : ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter First Name : ");
                    String firstName = sc.nextLine();
                    System.out.print("Enter Last Name : ");
                    String lastName = sc.nextLine();
                    System.out.print("Enter Email : ");
                    String email = sc.nextLine();
                    System.out.print("Enter password : ");
                    String password = sc.nextLine();

                    while (userService.checkPasswordUsed(email,password)) {
                        System.out.println("Password already exists. Set again : ");
                        password = sc.next();
                    }
                    user = new User();
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setEmail(email);

                    userService.registration(user,password);

                    break;

                case 2:
                    System.out.println("Enter email : ");
                    email = sc.next();
                    sc.nextLine();
                    System.out.println("Enter password : ");
                    password = sc.next();
                    sc.nextLine();

                    user=userService.signIn(email,password);
                    if (user!=null) {
                        int choiceIdAccount=0;

                        while (true) {

                            System.out.println("\n-------------------");
                            System.out.println("LIST YOUR ACCOUNTS");
                            System.out.println("-------------------\n");

                            List<AccountShortInfo> accountShortInfos = accountService.getAllAccountByUserId(user.getId());

                                for (AccountShortInfo accountShortInfo : accountShortInfos) {

                                    System.out.println("ACCOUNT");
                                    System.out.println("ID : " + accountShortInfo.getId());
                                    System.out.println("DATE OF ACCOUNT CREATION : " + accountShortInfo.getCreatedDate());
                                    System.out.println("BALANCE : " + accountShortInfo.getBalance());
                                    System.out.println("CURRENCY : " + accountShortInfo.getCurrency());
                                    System.out.println("NAME BANK : " + accountShortInfo.getNameBank()+"\n");
                                }
                            if (accountShortInfos.size()>0){
                                System.out.println("ENTER ID ACCOUNT FOR ACTION : ");
                                choiceIdAccount = sc.nextInt();


                                System.out.println("\n-------------------");
                                System.out.println("W  E  L  C  O  M  E");
                                System.out.println("-------------------\n");
                                System.out.println("1. Deposit.");
                                System.out.println("2. Transfer.");
                                System.out.println("3. Withdrawal.");
                                System.out.println("4. Get Invoice.");
                                System.out.println("5. Create Account.");
                                System.out.println("6. Log out.");
                                System.out.print("\nEnter your choice : ");
                                choice = sc.nextInt();
                                sc.nextLine();
                        } else {
                                System.out.println("You need to create Account for action");
                                choice=5;
                            }
                                switch (choice) {
                                    case 1:
                                        System.out.print("Enter amount : ");
                                        while (!sc.hasNextBigDecimal()) {
                                            System.out.println("Invalid amount [0.00]. Enter again :");
                                            sc.nextLine();
                                        }
                                        int idTransaction = accountService.deposit(sc.nextBigDecimal(),choiceIdAccount);

                                        String file = bankPdf.formCheckPdf3(accountService.getTransactionById(idTransaction));
                                        EmailSending.send(user.getEmail(),user.getFirstName()+" Your check ","Hello "+user.getFirstName()+" your check in pdf \nThank you for your trust!\n Union Bank",file);

                                        sc.nextLine();
                                        break;
                                    case 2:
                                        System.out.print("Enter id account  : ");
                                        int toAccountId = sc.nextInt();
                                        sc.nextLine();
                                        System.out.println("Enter amount : ");
                                        while (!sc.hasNextBigDecimal()) {
                                            System.out.println("Invalid amount [0.00]. Enter again :");
                                            sc.nextLine();
                                        }
                                        idTransaction = accountService.transfer(sc.nextBigDecimal(),choiceIdAccount,toAccountId,new BigDecimal(0));
                                        EmailSending.send(user.getEmail(),user.getFirstName()+" Your check ","Hello "+user.getFirstName()+" your check in pdf \nThank you for your trust!\n Union Bank",bankPdf.formCheckPdf(accountService.getTransactionById(idTransaction)));
                                        sc.nextLine();

                                        break;

                                    case 3:
                                        System.out.print("Enter amount : ");
                                        while (!sc.hasNextBigDecimal()) {
                                            System.out.println("Invalid amount [0.00]. Enter again :");
                                            sc.nextLine();
                                        }
                                        idTransaction = accountService.withdrawal(sc.nextBigDecimal(),choiceIdAccount);
                                        EmailSending.send(user.getEmail(),user.getFirstName()+" Your check ","Hello "+user.getFirstName()+" your check in pdf \nThank you for your trust!\n Union Bank",bankPdf.formCheckPdf(accountService.getTransactionById(idTransaction)));
                                        sc.nextLine();

                                        break;

                                    case 4:
                                        Account account = accountService.getAccountById(choiceIdAccount);
                                        account.setTransactions(accountService.getAllTransactionByAccountId(choiceIdAccount));
                                        EmailSending.send(user.getEmail(),user.getFirstName()+" Your Invoice ","Hello "+user.getFirstName()+" your invoice in pdf \nThank you for your trust!\n Union Bank",bankPdf.invoicePdf2(user,account));
                                        System.out.println("Invoice was sent by mail");

                                        break;
                                    case 5:

                                        List<Bank> banks = bankService.getAllBank();
                                        for (Bank b : banks){
                                            System.out.println("Bank id: "+b.getId());
                                            System.out.println("Bank name: "+b.getName()+"\n");
                                        }
                                        System.out.println("ENTER ID Bank : ");
                                        int idBank = sc.nextInt();
                                        if (banks.stream().anyMatch(bank -> bank.getId()==idBank)) {
                                            accountService.createAccount(1, idBank, user.getId());
                                        }else {
                                            System.out.println("Wrong Id for bank");
                                        }
                                        break ;

                                    case 6:
                                        continue outer;
                                    default:
                                        System.out.println("Wrong choice !");
                                }
                            }


                    } else {
                        System.out.println("Wrong email/password.");
                    }
                    break;

                case 3:
                    System.out.println("\nThank you for choosing Union Bank.");
                    System.exit(1);
                    break;
                default:
                    System.out.println("Wrong choice !");
            }
        }
    }
}
