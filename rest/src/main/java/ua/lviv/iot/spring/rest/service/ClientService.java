package ua.lviv.iot.spring.rest.service;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import ua.lviv.iot.spring.rest.model.Client;
import ua.lviv.iot.spring.rest.model.SolarStation;
import ua.lviv.iot.spring.rest.writer.ClientWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Getter
@Setter
public class ClientService {
    private final Map<Integer, Client> clientMap = new HashMap<>();
    private final AtomicInteger clientsIdCounter = new AtomicInteger();
    private final ClientWriter writer = new ClientWriter();

    public Client createNewClient(Client client) {
        client.setId(clientsIdCounter.incrementAndGet());
        clientMap.put(client.getId(), client);
        return client;
    }

    public Client getClientById(Integer id) {
        return clientMap.getOrDefault(id, null);
    }

    public void deleteClientById(Integer id) {
        Client client = clientMap.get(id);
        if (client != null) {
            clientMap.remove(id);
        } else {
            throw new IllegalArgumentException("Client not found with ID: " + id);
        }
    }

    public List<Client> getAllClients() {
        return new LinkedList<>(clientMap.values());
    }

    public void updateClient(Integer id, String name, String surname) {
        Client client = clientMap.get(id);
        if (client != null) {
            client.setName(name);
            client.setSurname(surname);
            clientMap.put(id, client);
        } else {
            throw new IllegalArgumentException("Client not found with ID: " + id);
        }
    }

    public void loadInfoSolarStationToClient(Integer id, Integer idOfStationToLoad, SolarStation solarStation){
        Client client = clientMap.get(id);
        if (client != null){
            client.addStationToMap(idOfStationToLoad, solarStation);
            clientMap.put(id, client);
        } else {
            throw new ObjectNotFoundException(id, "Solar station not found with ID: " + id);
        }
    }

    public void writeAllObjectsToCSV(Map<Integer, Client> clientMap, String filePath){
        try {
            writer.writeAllObjectsToCSV(clientMap, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
