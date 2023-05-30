package sg.edu.nus.iss.app.tfip_carcare.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.app.tfip_carcare.models.Item;
import sg.edu.nus.iss.app.tfip_carcare.models.ItemListDTO;
import sg.edu.nus.iss.app.tfip_carcare.repositories.ItemRepository;

@Service
@Transactional
public class ItemService {
    @Autowired
    ItemRepository itemRepo;

    public Item createItem(Item item) {
        Boolean result = itemRepo.saveItem(item);
        if (result == true) {
            return itemRepo.findLastRow().get(0);
        } else {
            return null;
        }
    }

    public Boolean deleteItem(int itemId) {
        return itemRepo.deleteById(itemId);
    }

    public Optional<Item> retrieveItemById(int itemId) {
        List<Item> result = itemRepo.findById(itemId);
        if (result.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }

    public List<Item> retrieveAllItems() {
        List<Item> result = itemRepo.findAll();
        return result;
    }

    public List<Item> retrieveItembyNameLike(String name) {
        List<Item> result = itemRepo.findByNameLike(name);
        return result;
    }

    public List<ItemListDTO> retrieveItemByMaintId(Integer maintId) {
        List<ItemListDTO> result = itemRepo.findByMaintId(maintId);
        return result;
    }
}
