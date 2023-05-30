package sg.edu.nus.iss.app.tfip_carcare.repositories;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;
import sg.edu.nus.iss.app.tfip_carcare.models.Car;
import sg.edu.nus.iss.app.tfip_carcare.models.Sale;
@Repository
public class CarRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    private final String selectByEmailSQL = "select * from car where customer_id = " +
            "(select id from customer where email = ?) ";
    private final String selectByCarPlateSQL = "select * from car where carPlate = ?";
    private final String selectByIdSQL = "SELECT * FROM car where id=?";
    private final String selectCustIdByIdSQL = "SELECT customer_id FROM car where id= ";

    private final String insertSQL = "Insert into car(carPlate,brand,model,yearManufactured, customer_id) values(?,?,?,?,?)";
    private final String updateSQL = "UPDATE car SET carPlate = ?, brand=?,model=?, yearManufactured=? WHERE id=?";
    private final String updateOwnerSQL = "UPDATE car SET customer_id=? WHERE id=?";
    private final String deleteSQL = "DELETE FROM car where id=?";

    public List<Car> findByEmail(String email) {
        return jdbcTemplate.query(selectByEmailSQL,
                BeanPropertyRowMapper.newInstance(Car.class),
                email);

    }

    public List<Car> findByCarPlate(String carPlate) {
        return jdbcTemplate.query(selectByCarPlateSQL,
                BeanPropertyRowMapper.newInstance(Car.class),
                carPlate);

    }

    public List<Car> findById(Integer carId) {
        return jdbcTemplate.query(selectByIdSQL, BeanPropertyRowMapper.newInstance(Car.class), carId);
    }

    public Boolean saveCar(Car car, int customer_id) {
        return jdbcTemplate.update(insertSQL,
                car.getCarPlate(),
                car.getBrand(),
                car.getModel(),
                car.getYearManufactured(),
                customer_id) > 0 ? true : false;
    }

    public Boolean update(Car car) {
        return jdbcTemplate.update(updateSQL,
                car.getCarPlate(),
                car.getBrand(),
                car.getModel(),
                car.getYearManufactured(),
                car.getId()) > 0 ? true : false;
    }

    public Boolean deleteCarById(Integer carId) {
        return jdbcTemplate.update(deleteSQL, carId) > 0 ? true : false;
    }

    public String putCarInTransferList(Sale sale){
        JsonObject jsonSale = sale.toJson();
        String carId = String.valueOf(sale.getCarId());
        redisTemplate.opsForValue().set(carId, jsonSale.toString());
        redisTemplate.expire(String.valueOf(sale.getCarId()),24L,TimeUnit.HOURS);

        return redisTemplate.opsForValue().get(carId);
    }

	public int findCustIdById(int carId) {
        int result = jdbcTemplate.queryForObject(selectCustIdByIdSQL,Integer.class,carId);
		return result;
	}

    public List<Car> findCarsByBuyer(int buyerId) {
        Set<String> keys = redisTemplate.keys("*");
        
        return keys.stream().map((k)->redisTemplate.opsForValue().get(k))
                        .map(value->Sale.create(value))
                        .filter(sale->sale.getBuyerId()==buyerId)
                        .map(sale->findById(sale.getCarId()).get(0))
                        .collect(Collectors.toList());
    }

    public List<Car> findCarsBySeller(int sellerId) {
        Set<String> keys = redisTemplate.keys("*");
        
        return keys.stream().map((k)->redisTemplate.opsForValue().get(k))
                        .map(value->Sale.create(value))
                        .filter(sale->sale.getSellerId()==sellerId)
                        .map(sale->findById(sale.getCarId()).get(0))
                        .collect(Collectors.toList());
    }

    public Sale findSaleByCarId(int carId) {
        String result = redisTemplate.opsForValue().get(String.valueOf(carId));

        Sale sale = Sale.create(result);
        return sale;
    }

    public Boolean updateOwner(int buyerId, int carId) {
        return jdbcTemplate.update(updateOwnerSQL,
                buyerId,
                carId) > 0 ? true : false;
    }

    public void deleteSaleByCarId(int carId) {
        redisTemplate.delete(String.valueOf(carId));
    }

}
