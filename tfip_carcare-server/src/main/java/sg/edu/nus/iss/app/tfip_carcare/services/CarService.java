package sg.edu.nus.iss.app.tfip_carcare.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.app.tfip_carcare.models.Car;
import sg.edu.nus.iss.app.tfip_carcare.models.Customer;
import sg.edu.nus.iss.app.tfip_carcare.models.EmailDTO;
import sg.edu.nus.iss.app.tfip_carcare.models.Sale;
import sg.edu.nus.iss.app.tfip_carcare.repositories.CarRepository;
import sg.edu.nus.iss.app.tfip_carcare.repositories.CustomerRepository;

@Service
@Transactional
public class CarService {
    @Autowired
    CarRepository carRepo;

    @Autowired
    CustomerRepository custRepo;
    
    @Autowired
    MqProducerService mqProducerService;

    public Car createCar(Car car, String email) {
        Integer customerId = custRepo.findIdByEmail(email);
        Boolean result = carRepo.saveCar(car, customerId);

        if (result == true) {
            return carRepo.findByCarPlate(car.getCarPlate()).get(0);
        } else {
            return null;
        }
    }

    public Optional<Car> retrieveCarById(Integer carId) {
        List<Car> result = carRepo.findById(carId);
        if (result.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }

    public List<Car> retrieveCarsByEmail(String email) {
        return carRepo.findByEmail(email);
    }

    public Car editCar(Car car) {
        Boolean updated = carRepo.update(car);
        if (updated == true) {
            return carRepo.findById(car.getId()).get(0);
        } else {
            return null;
        }
    }

    public Boolean deleteCar(Integer carId) {
        return carRepo.deleteCarById(carId);
    }

    public Boolean createCarSale(int carId, int buyerId) {
        int sellerId = carRepo.findCustIdById(carId);
        Sale sale = new Sale(carId,buyerId,sellerId);
        String result = carRepo.putCarInTransferList(sale);
        String carPlate = carRepo.findById(carId).get(0).getCarPlate();
        if(result!=null){
            Customer buyer = custRepo.findById(sale.getBuyerId()).get(0);
            Customer seller = custRepo.findById(sale.getSellerId()).get(0);
            String messageToBuyer = String.format("Dear %s, \n \n Please go to transferlist tab of our website to claim your new car %s \n \n Regards, \n TFIP CAR CARE", buyer.getEmail(),carPlate);
            String messageToSeller = String.format("Dear %s, \n \n Car of car Plate %s has been put in transfer list.\n \n Regards, \n TFIP CAR CARE", seller.getEmail(),carPlate);
            EmailDTO emailToBuyer = new EmailDTO(buyer.getEmail(),"Your Car Purchase",messageToBuyer);
            EmailDTO emailToSeller = new EmailDTO(seller.getEmail(),"Your Car Purchase",messageToSeller);
            mqProducerService.sendEmail(emailToBuyer);
            mqProducerService.sendEmail(emailToSeller);
            return true;
        }
        else{
            return false;
        }
    }

    public List<Car> retriveCarsSoldTo(int buyerId) {
        return carRepo.findCarsByBuyer(buyerId);
    }
    
    public List<Car> retriveCarsSoldFrom(int sellerId) {
        return carRepo.findCarsBySeller(sellerId);
    }

    public Boolean transferOwnerShip(String carPlate) {
        int carId = carRepo.findByCarPlate(carPlate).get(0).getId();
        Sale sale = carRepo.findSaleByCarId(carId);
        Boolean updated = carRepo.updateOwner(sale.getBuyerId(),carId);
        if(updated == true){
            carRepo.deleteSaleByCarId(carId);
        }
        Customer buyer = custRepo.findById(sale.getBuyerId()).get(0);
        Customer seller = custRepo.findById(sale.getSellerId()).get(0);

        String messageToBuyer = String.format("Dear %s, \n \n Car of car Plate %s is now yours.\n \n Regards, \n TFIP CAR CARE", buyer.getEmail(),carPlate);
        String messageToSeller = String.format("Dear %s, \n \n Car of car Plate %s has been transferred.\n \n Regards, \n TFIP CAR CARE", seller.getEmail(),carPlate);
        EmailDTO emailToBuyer = new EmailDTO(buyer.getEmail(),"Your Car Purchase",messageToBuyer);
        EmailDTO emailToSeller = new EmailDTO(seller.getEmail(),"Your Car Purchase",messageToSeller);
        mqProducerService.sendEmail(emailToBuyer);
        mqProducerService.sendEmail(emailToSeller);
        
        return updated;
    }
}
