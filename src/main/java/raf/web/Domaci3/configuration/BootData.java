package raf.web.Domaci3.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import raf.web.Domaci3.model.ErrorMassage;
import raf.web.Domaci3.model.Machine;
import raf.web.Domaci3.model.PermissionsEnum;
import raf.web.Domaci3.model.User;
import raf.web.Domaci3.repositories.IErrorMassageRepository;
import raf.web.Domaci3.repositories.IMachineRepository;
import raf.web.Domaci3.repositories.IUserRepository;

import java.sql.Date;


@Component
public class BootData implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final IMachineRepository machineRepository;
    private final IErrorMassageRepository errorMassageRepository;

    private BCryptPasswordEncoder encoder;


    @Autowired
    public BootData(IUserRepository userRepository, IMachineRepository machineRepository, IErrorMassageRepository errorMassageRepository,BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.machineRepository = machineRepository;
        this.errorMassageRepository = errorMassageRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading Data...");

        User root = new User("Root", "Roots", "root@gmail.com", this.encoder.encode("root"));
        for(PermissionsEnum permissionsEnum:PermissionsEnum.values()){
            root.addPermission(permissionsEnum);
        }
        User user1 = new User("Milan","Bojic","mbojic12@raf.rs",this.encoder.encode("milan"));
        user1.addPermission(PermissionsEnum.CAN_READ_USERS);

        User user2 = new User("Stefan","Burgic","burga@raf.rs",this.encoder.encode("Algo"));
        user2.addPermission(PermissionsEnum.CAN_READ_USERS);
        user2.addPermission(PermissionsEnum.CAN_SEARCH_MACHINE);
        user2.addPermission(PermissionsEnum.CAN_START_MACHINE);
        user2.addPermission(PermissionsEnum.CAN_CREATE_MACHINE);
        user2.addPermission(PermissionsEnum.CAN_RESTART_MACHINE);



        Machine machineRoot1 = new Machine(root,"root1");
        Machine machineRoot2 = new Machine(root,"root2");
        machineRoot2.setCreated(new Date(2020,12,1));
        Machine machineRoot3 = new Machine(root,"root3");
        machineRoot3.setCreated(new Date(2021,11,1));
        Machine machineRoot4 = new Machine(root,"Fifa");
        Machine machine2 = new Machine(root,"script");
        machine2.setActive(false);

        Machine machine3 = new Machine(user1,"user12");

        Machine mach4 = new Machine(user2,"super1");
        Machine mach5 = new Machine(user2,"super2");
        Machine mach6 = new Machine(user2,"super3");
        mach6.setCreated(new Date(2021,1,1));

        userRepository.save(root);
        userRepository.save(user1);
        userRepository.save(user2);

        machineRepository.save(machineRoot1);
        machineRepository.save(machineRoot2);
        machineRepository.save(machineRoot3);
        machineRepository.save(machineRoot4);
        machineRepository.save(machine2);
        machineRepository.save(machine3);
        machineRepository.save(mach4);
        machineRepository.save(mach5);
        machineRepository.save(mach6);

        ErrorMassage er1 = new ErrorMassage("Dummy massage",null,root);
        ErrorMassage er2 = new ErrorMassage("Dummy massage2",null,root);

        errorMassageRepository.save(er1);
        errorMassageRepository.save(er2);

        System.out.println("Data loaded!");

    }
}
