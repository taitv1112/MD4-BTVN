package codegym.service;

import codegym.model.ClassRoom;
import codegym.repository.ClassRoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public class ClassZoomService implements IClassZoomService{
    @Autowired
    ClassRoomRepo classRoomRepo;
    @Override
    public List<ClassRoom> findAll() {
        return (List<ClassRoom>) classRoomRepo.findAll();
    }
}
