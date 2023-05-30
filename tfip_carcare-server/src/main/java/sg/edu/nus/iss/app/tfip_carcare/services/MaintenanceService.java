package sg.edu.nus.iss.app.tfip_carcare.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.edu.nus.iss.app.tfip_carcare.models.ItemListDTO;
import sg.edu.nus.iss.app.tfip_carcare.models.Maintenance;
import sg.edu.nus.iss.app.tfip_carcare.repositories.CustomerRepository;
import sg.edu.nus.iss.app.tfip_carcare.repositories.MaintenanceItemRepository;
import sg.edu.nus.iss.app.tfip_carcare.repositories.MaintenanceRepository;

@Service
@Transactional
public class MaintenanceService {
    @Autowired
    MaintenanceRepository maintRepo;

    @Autowired
    CustomerRepository custRepo;

    @Autowired
    MaintenanceItemRepository maintItemRepo;

    public List<Maintenance> retrieveMaintenancesByEmail(String email) {
        int customerId = custRepo.findIdByEmail(email);
        List<Maintenance> result = maintRepo.findByCustId(customerId);

        return result;
    }

    public List<Maintenance> retrieveMaintenancesByCarId(int carId) {
        List<Maintenance> result = maintRepo.findByCarId(carId);
        return result;
    }

    public Optional<Maintenance> retrieveMaintenanceById(Integer maintId) {
        List<Maintenance> result = maintRepo.findById(maintId);
        if (result.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }

    public Maintenance editMaintenance(Maintenance maintenance) {
        //set cost based on List of Items
        if(maintenance.getItems()!=null && !maintenance.getItems().isEmpty()){
            Double cost = 0.0D;
            for(ItemListDTO itemListDTO:maintenance.getItems()){
                cost += (itemListDTO.getItem().getPrice()*itemListDTO.getQuantity());
            }
            maintenance.setCost(cost);
        }
        else{
            maintenance.setCost(0.0D);
        }

        Boolean updated = maintRepo.update(maintenance);
        maintItemRepo.deleteByMaintId(maintenance.getId());
        for (ItemListDTO itemListDTO : maintenance.getItems()) {
            int quantity = itemListDTO.getQuantity();
            maintItemRepo.createMaintItem(maintenance.getId(), itemListDTO.getItem().getId(),quantity);
        }
        if (updated == true) {
            return maintRepo.findById(maintenance.getId()).get(0);
        } else {
            return null;
        }
    }

    public Maintenance createMaintenance(Maintenance maintenance, int car_id) {
        //set cost based on List of Items
        if(maintenance.getItems()!=null && !maintenance.getItems().isEmpty()){
            Double cost = 0.0D;
            for(ItemListDTO itemListDTO:maintenance.getItems()){
                cost += (itemListDTO.getItem().getPrice()*itemListDTO.getQuantity());
            }
            maintenance.setCost(cost);
        }
        else{
            maintenance.setCost(0.0D);
        }
        
        Boolean result = maintRepo.saveMaintenance(maintenance, car_id);

        System.out.println("is maintenance able to save?" + result);
        if (result == true) {
            List<Maintenance> maintenances = maintRepo.findByCarId(car_id);
            int index = maintenances.size() - 1;
            Maintenance savedMaintenance = maintenances.get(index);

            if ( maintenance.getItems()!=null) {
                if(maintenance.getItems().size() > 0){
                    for (ItemListDTO itemListDTO : maintenance.getItems()) {
                        Integer quantity = itemListDTO.getQuantity();
                        System.out.println("quantity of"+itemListDTO.getItem().getName()+" is "+quantity);
                        maintItemRepo.createMaintItem(savedMaintenance.getId(), itemListDTO.getItem().getId(),quantity);
                    }
                }
            }

            return savedMaintenance;
        } else {
            return null;
        }
    }

    public Boolean deleteMaintenance(Integer maintenanceId) {
        return maintRepo.deleteMaintenanceById(maintenanceId);
    }

	public Integer retrieveCarIdByMaintId(Integer maintId) {
        return maintRepo.findCarIdById(maintId);
	}

}
