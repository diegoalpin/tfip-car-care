package sg.edu.nus.iss.app.tfip_carcare.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.app.tfip_carcare.models.Maintenance;

@Repository
public class MaintenanceRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private final String findByCustIdSQL = "SELECT * FROM maintenance where car_id IN" +
            "(SELECT id FROM car where customer_id=?)";

    private final String findByCarIdSQL = "SELECT * FROM maintenance where car_id=?";
    private final String findByIdSQL = "SELECT * FROM maintenance where id = ?";
    private final String findCarIdByIdSQL = "SELECT car_id FROM maintenance where id=?";

    private final String insertSQL = "Insert into maintenance(description,date,cost,status,mileage,car_id) values(?,?,?,?,?,?)";
    private final String updateSQL = "UPDATE maintenance SET description = ?,date=?,cost=?,status=?, mileage=? WHERE id=?";
    private final String deleteSQL = "DELETE FROM maintenance where id=?";

    public List<Maintenance> findByCustId(int customerId) {
        return jdbcTemplate.query(findByCustIdSQL, BeanPropertyRowMapper.newInstance(Maintenance.class), customerId);
    }

    public List<Maintenance> findByCarId(int carId) {
        System.out.println("finding maintenance by Car id");
        return jdbcTemplate.query(findByCarIdSQL, BeanPropertyRowMapper.newInstance(Maintenance.class), carId);
    }

    public List<Maintenance> findById(int id) {
        return jdbcTemplate.query(findByIdSQL, BeanPropertyRowMapper.newInstance(Maintenance.class), id);
    }

    public Boolean update(Maintenance maintenance) {
        return jdbcTemplate.update(updateSQL,
                    maintenance.getDescription(),
                    maintenance.getDate(),
                    maintenance.getCost(),
                    maintenance.getStatus(),
                    maintenance.getMileage(),
                    maintenance.getId()) > 0 ? true : false;
    }

    public Boolean saveMaintenance(Maintenance maintenance, int car_id) {
        System.out.println("persisting maintenance");
        return jdbcTemplate.update(insertSQL,
                    maintenance.getDescription(),
                    maintenance.getDate(),
                    maintenance.getCost(),
                    maintenance.getStatus(),
                    maintenance.getMileage(),
                    car_id) > 0 ? true : false;
    }

	public Boolean deleteMaintenanceById(Integer maintenanceId) {
		return jdbcTemplate.update(deleteSQL, maintenanceId) > 0 ? true : false;
	}

    public Integer findCarIdById(Integer maintId) {
        return jdbcTemplate.queryForObject(findCarIdByIdSQL,Integer.class,maintId);
    }

}
