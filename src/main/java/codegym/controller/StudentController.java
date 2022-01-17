package codegym.controller;

import codegym.model.ClassRoom;
import codegym.model.Student;
import codegym.service.IClassZoomService;
import codegym.service.IStudentService;
import codegym.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
public class StudentController {
    @Autowired
    IStudentService studentService;

    @Autowired
    IClassZoomService classZoomService;

    @GetMapping("/students")
    public ModelAndView showAll(){
        ModelAndView modelAndView = new ModelAndView("show");
        modelAndView.addObject("students", studentService.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreate(){
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("student", new Student());
        modelAndView.addObject("classZooms", classZoomService.findAll());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute(value = "student") Student student, @RequestParam long idClassZoom, @RequestParam MultipartFile upImg){
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(idClassZoom);
        student.setClassRoom(classRoom);

        String nameFile = upImg.getOriginalFilename();
        try {
            FileCopyUtils.copy(upImg.getBytes(), new File("/Users/johntoan98gmail.com/Desktop/Module3/Demo_Spring_Repository/src/main/webapp/WEB-INF/img/" + nameFile));
            student.setImg("/img/"+nameFile);
            studentService.save(student);

        } catch (IOException e) {
            student.setImg("/img/abc.jpeg");
            studentService.save(student);
            e.printStackTrace();
        }
        return "redirect:/students";
    }
    @GetMapping("/edit")
    public ModelAndView editForm( @RequestParam int index) {
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("student",studentService.findAll().get(index));
        modelAndView.addObject("classZooms", classZoomService.findAll());
        return modelAndView;
    }
    @PostMapping("/edit")
    public String edit(@ModelAttribute(value = "student") Student student, @RequestParam long idClassZoom, @RequestParam MultipartFile upImg){
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(idClassZoom);
        student.setClassRoom(classRoom);
        String nameFile = upImg.getOriginalFilename();
        try {
            FileCopyUtils.copy(upImg.getBytes(), new File("C:\\Users\\Lovin\\Downloads\\Demo_Repository_JPA-master\\Demo_Repository_JPA-master\\src\\main\\webapp\\WEB-INF\\img\\" + nameFile));
            String imgOld = student.getImg();
            student.setImg("/img/"+nameFile);
                if(!imgOld.equals(student.getImg())) {
                    String file1 = "C:\\Users\\Lovin\\Downloads\\Demo_Repository_JPA-master\\Demo_Repository_JPA-master\\src\\main\\webapp\\WEB-INF" + imgOld;
                    File file = new File(file1);
                    if (file.exists()) {
                        file.delete();
                    }
                }
            studentService.save(student);
        } catch (IOException e) {
            studentService.save(student);
            e.printStackTrace();
        }
        return "redirect:/students";
    }
}
