package ua.lviv.iot.spring.rest;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.lviv.iot.spring.rest.controller.ClientController;
import ua.lviv.iot.spring.rest.model.Client;
import ua.lviv.iot.spring.rest.model.SolarStation;
import ua.lviv.iot.spring.rest.service.ClientService;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.spring.rest.service.SolarStationService;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ClientTest {
    private ClientService clientService = new ClientService();
    private ClientController clientController = new ClientController();
    private Map<Integer, Client> clientMap = new HashMap<>();
    private Map<Integer, SolarStation> solarStationMap = new HashMap<>();

    @BeforeEach
    public void SetUp() {
        clientService = new ClientService();
    }

    @Test
    void testCreateClient() {

        Client client = new Client();
        client.setName("Me");
        client.setSurname("Not me");

        Client createdClient = clientService.createNewClient(client);

        assertNotNull(createdClient.getId());
        assertEquals(1, clientService.getClientMap().size());
        assertEquals(createdClient, clientService.getClientMap().get(createdClient.getId()));
    }

    @Test
    void testGetAllClients() {
        Client client = new Client();
        client.setName("Me");
        client.setSurname("Not me");
        Client client1 = new Client();
        client1.setName("Pen");
        client1.setSurname("Cil");
        clientService.createNewClient(client);
        clientService.createNewClient(client1);
        List<Client> listWithClients = clientService.getAllClients();

        assertNotNull(listWithClients);
        assertEquals(2, listWithClients.size());
    }

    @Test
    void testGetClientById() {
        Client client = new Client();
        client.setName("Me");
        client.setSurname("Not me");
        Client createdClient = clientService.createNewClient(client);

        Client anotherClient = clientService.getClientById(createdClient.getId());

        assertNotNull(anotherClient);
        assertEquals(createdClient, anotherClient);
    }

    @Test
    void testDeleteClientById() {
        Client client = new Client();
        client.setName("Me");
        client.setSurname("Not me");

        Client createdClient = clientService.createNewClient(client);

        clientService.deleteClientById(createdClient.getId());

        Client anotherClient = clientService.getClientById(createdClient.getId());

        assertNull(anotherClient);
        assertEquals(0, clientService.getClientMap().size());
    }

    @Test
    void testUpdateClient() {
        Client client = new Client();
        client.setName("Me");
        client.setSurname("Not me");

        Client createdClient = clientService.createNewClient(client);

        String newName = "Pen";
        String newSurname = "Cil";
        clientService.updateClient(createdClient.getId(), newName, newSurname);

        Client updatedClient = clientService.getClientById(createdClient.getId());

        assertNotNull(updatedClient);
        assertEquals(newName, updatedClient.getName());
        assertEquals(newSurname, updatedClient.getSurname());
    }
}
