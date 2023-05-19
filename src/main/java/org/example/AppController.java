package org.example;

import org.example.dao.ClientDAO;
import org.example.model.BankAccount;
import org.example.model.Client;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class AppController {
    private static EntityManagerFactory entityManagerFactory;
    private static ClientDAO clientDAO;

    public static void main() {
        entityManagerFactory = Persistence.createEntityManagerFactory("tp_vendredi_19-05");
        clientDAO = new ClientDAO(entityManagerFactory);
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("#### Gestion de client/compte bancaire ####");
            System.out.println("1. Ajouter un client");
            System.out.println("2. Afficher tous les clients");
            System.out.println("3. Modifier un client");
            System.out.println("4. Supprimer un client");
            System.out.println("5. Quitter l'application");
            System.out.println("Choix : ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1:
                    addClient(scanner);
                    break;
                case 2:
                    getAllClients(scanner);
                    break;
                case 3:
                    updateClient(scanner);
                    break;
                case 4:
                    deleteClient(scanner);
                    break;
                case 5:
                    System.out.println("Bye");
                    entityManagerFactory.close();
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");

            }

        } while (choice != 5);
    }

    private static void addClient(Scanner scanner){
        System.out.println("Entrez un prénom");
        String firstname = scanner.nextLine();
        System.out.println("Entrez un nom");
        String lastname = scanner.nextLine();
        System.out.println("Entrez une date de naissance");
        String birthDate = scanner.nextLine();
        System.out.println("Entrez un libelle pour le compte bancaire");
        String accountName = scanner.nextLine();
        System.out.println("Entrez un IBAN");
        int iban = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Entrez le solde du compte");
        double balance = scanner.nextDouble();

        Client client = new Client();
        client.setFirstname(firstname);
        client.setLastname(lastname);
        client.setBirthDate(birthDate);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setName(accountName);
        bankAccount.setIBAN(iban);
        bankAccount.setBalance(balance);

         client.setBankAccount(bankAccount);
         bankAccount.setClient(client);

        if (clientDAO.addClient(client)) {
            System.out.println("Client ajouté avec succès !");
        } else {
            System.out.println("Erreur lors de l'ajout");
        }
    }

    private static void getAllClients(Scanner scanner){
        List<Client> clients = clientDAO.getAllClients();
        if (clients.isEmpty()) {
            System.out.println("Aucun clients trouvés");
        } else {
            for (Client c:
                    clients) {
                System.out.println(c+", "+c.getBankAccount());
            }
        }
    }

    private static void updateClient(Scanner scanner) {
        System.out.println("Entrez l'ID du client à modifier");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Entrez un nouveau prénom");
        String firstname = scanner.nextLine();
        System.out.println("Entrez un nouveau nom");
        String lastname = scanner.nextLine();
        System.out.println("Entrez une nouvelle date de naissance");
        String birthDate = scanner.nextLine();

        if (ClientDAO.updateClient(id,firstname,lastname,birthDate)){
            System.out.println("Client mis à jour avec succès !");
        } else {
            System.out.println("Impossible de mettre le client à jour");
        }
    }

    private static void deleteClient(Scanner scanner) {
        System.out.println("Entrez l'ID du clint à supprimer");
        int id = scanner.nextInt();
        if (clientDAO.deleteClient(id)) {
            System.out.println("Client supprimé avec succès !");
        } else {
            System.out.println("Erreur lors de la suppression");
        }
    }




}
