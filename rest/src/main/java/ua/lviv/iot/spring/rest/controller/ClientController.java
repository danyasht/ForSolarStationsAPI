package ua.lviv.iot.spring.rest.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.spring.rest.model.Client;
import ua.lviv.iot.spring.rest.model.SolarStation;
import ua.lviv.iot.spring.rest.service.ClientService;
import ua.lviv.iot.spring.rest.service.SolarStationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ClientController")
@NoArgsConstructor
@AllArgsConstructor
public class ClientController {
    @Autowired
    public ClientService clientService;
    @Autowired
    public SolarStationService solarStationService;
    private SolarStation solarStationToAdd = null;

    @PostMapping("/create")
    public ResponseEntity<Client> CreateNewClient(@RequestBody Client client) {
        Client addedClient = clientService.createNewClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedClient);
    }

    @GetMapping("/client/{id}")
    public Client getClientById(@PathVariable Integer id) {
        return clientService.getClientById(id);
    }

    @GetMapping("/clients")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClientById(@PathVariable Integer id) {
        clientService.deleteClientById(id);
        return ResponseEntity.ok("Client deleted successfully");
    }

    @Getter
    @Setter
    private static class ClientUpdateRequest {
        private String name;
        private String surname;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateClient(@PathVariable("id") Integer id, @RequestBody ClientUpdateRequest request) {
        clientService.updateClient(id, request.getName(), request.getSurname());
        return ResponseEntity.ok("Client updated successfully");
    }

    @PostMapping("/load/solarStationsInfo/{idOfClient}/{idOfSolarStation}")
    public ResponseEntity<String> loadInfoSolarStationToClient(@PathVariable("idOfClient") Integer id,
                                                               @PathVariable("idOfSolarStation") Integer idOfStation){
        Map<Integer, SolarStation> solarStationMap = solarStationService.getSolarStationMap();
        try {
            solarStationToAdd = solarStationMap.get(idOfStation);
        } catch (ObjectNotFoundException e){
            ResponseEntity.badRequest();
        }

        clientService.loadInfoSolarStationToClient(id, idOfStation, solarStationToAdd);
        return ResponseEntity.ok("Solar station all info loaded successfully");
    }

    @PostMapping(path = "/export")
    public ResponseEntity<String> writeAllObjectsToCSV(@RequestParam("folderpath") String folderpath){
        Map<Integer, Client> clientMap = clientService.getClientMap();
        try {
            clientService.writeAllObjectsToCSV(clientMap, folderpath);
            return ResponseEntity.ok("All clients files were created");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
}


