package sg.edu.nus.iss.app.tfip_carcare.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MaintenanceItemRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String insertSQL = "INSERT INTO maintenanceitem(maintenance_id, item_id, quantity) values(?,?,?)";
    private final String deleteSQL = "DELETE FROM maintenanceitem where maintenance_id=? AND item_id=?";
    private final String deleteByMaintIdSQL = "DELETE FROM maintenanceitem where maintenance_id=?";
    private final String deleteByItemIdSQL = "DELETE FROM maintenanceitem where item_id=?";

    public Boolean createMaintItem(int maint_id, int item_id, int quantity) {
        return jdbcTemplate.update(insertSQL, maint_id, item_id, quantity) > 0 ? true : false;
    }

    public Boolean deleteMaintItem(int maint_id, int item_id) {
        return jdbcTemplate.update(deleteSQL, maint_id, item_id) > 0 ? true : false;
    }

    public Boolean deleteByMaintId(int maint_id) {
        return jdbcTemplate.update(deleteByMaintIdSQL, maint_id) > 0 ? true : false;
    }

    public Boolean deleteByItemId(int item_id) {
        return jdbcTemplate.update(deleteByItemIdSQL, item_id) > 0 ? true : false;
    }
}
