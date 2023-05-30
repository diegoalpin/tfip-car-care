package sg.edu.nus.iss.app.tfip_carcare.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.app.tfip_carcare.models.Item;
import sg.edu.nus.iss.app.tfip_carcare.models.ItemListDTO;

@Repository
public class ItemRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String insertSQL = "INSERT INTO item (name, price) values(?,?)";
    private final String findAllSQL = "SELECT * FROM item ORDER BY name ASC";
    private final String findLastRowSQL = "SELECT * FROM item ORDER BY id DESC LIMIT 1";
    private final String findByIdSQL = "SELECT * FROM item where id = ?";
    private final String findByMaintIdSQL = "SELECT i.id,i.name, i.price,mi.quantity FROM item i INNER JOIN maintenanceitem mi" +
                                            " ON i.id = mi.item_id WHERE mi.maintenance_id  = ?";
    private final String findByNameLikeSQL = "SELECT * FROM item where name LIKE ?";
    private final String deleteByIdSQL = "DELETE FROM item where id=?";

    public Boolean saveItem(Item item) {
        return jdbcTemplate.update(insertSQL, item.getName(), item.getPrice()) > 0 ? true : false;
    }

    public List<Item> findAll() {
        System.out.println("finding all item");
        return jdbcTemplate.query(findAllSQL, BeanPropertyRowMapper.newInstance(Item.class));
    }
    public List<Item> findLastRow(){
        return jdbcTemplate.query(findLastRowSQL, BeanPropertyRowMapper.newInstance(Item.class));
    }

    public List<Item> findById(int id) {
        return jdbcTemplate.query(findByIdSQL, BeanPropertyRowMapper.newInstance(Item.class),id);
        
    }

    public List<Item> findByNameLike(String name) {
        String nameLike = '%' + name + '%';
        return jdbcTemplate.query(findByNameLikeSQL, BeanPropertyRowMapper.newInstance(Item.class),nameLike);
    }

    public Boolean deleteById(int id) {
        return jdbcTemplate.update(deleteByIdSQL, id) > 0 ? true : false;
    }

    public List<ItemListDTO> findByMaintId(Integer maintId) {
        return jdbcTemplate.query(findByMaintIdSQL, new ResultSetExtractor<List<ItemListDTO>>() {

            @Override
            public List<ItemListDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<ItemListDTO> itemListDTOs = new ArrayList<ItemListDTO>();
                while(rs.next()){
                    System.out.println(rs.toString());
                    ItemListDTO itemListDTO = new ItemListDTO();
                    Item item = new Item(rs.getInt(1), rs.getString(2), rs.getFloat(3));
                    itemListDTO.setItem(item);
                    itemListDTO.setQuantity(rs.getInt(4));
                    itemListDTOs.add(itemListDTO);
                    System.out.println("return from sql"+itemListDTO.toString());
                }
                return itemListDTOs;
            }
            
        },maintId);
        // return jdbcTemplate.query(findByMaintIdSQL, BeanPropertyRowMapper.newInstance(ItemListDTO.class),maintId);
    }
}