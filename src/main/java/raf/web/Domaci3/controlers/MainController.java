package raf.web.Domaci3.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.web.Domaci3.repositories.IUserRepository;

@RestController
@RequestMapping("")
public class MainController {

    @Autowired
    private IUserRepository userRepository;



}
