package com.besafx.app.init;

import com.besafx.app.entity.*;
import com.besafx.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class Initializer implements CommandLineRunner {

    @Autowired
    private ScreenService screenService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PersonService personService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (personService.count() == 0) {
            runForFirstTimeOnly();
        }
    }

    private void runForFirstTimeOnly() {

        Team team = new Team();
        team.setName("الدعم الفني");
        teamService.save(team);

        Person person = new Person();
        person.setNickname("مدير النظام");
        person.setName("بسام المهدي");
        person.setTeam(team);
        person.setEmail("admin@admin.com");
        person.setPassword(passwordEncoder.encode("admin"));
        person.setEnabled(true);
        person.setTokenExpired(false);
        person.setActive(false);
        personService.save(person);

        Screen screen = new Screen();
        screen.setCode("COMPANY");
        screen.setName("الشركات");
        screenService.save(screen);

        screen = new Screen();
        screen.setCode("BRANCH");
        screen.setName("الفروع");
        screenService.save(screen);

        screen = new Screen();
        screen.setCode("CONTACT");
        screen.setName("جهات الاتصال الخارجية");
        screenService.save(screen);

        screen = new Screen();
        screen.setCode("OPERATION_TYPE");
        screen.setName("أنواع المعاملات");
        screenService.save(screen);

        screen = new Screen();
        screen.setCode("INSIDE_OPERATION");
        screen.setName("المعاملات الداخلية");
        screenService.save(screen);

        screen = new Screen();
        screen.setCode("OUTSIDE_OPERATION");
        screen.setName("المعاملات الخارجية");
        screenService.save(screen);

        screen = new Screen();
        screen.setCode("EMPLOYEE");
        screen.setName("الموظفون");
        screenService.save(screen);

        screen = new Screen();
        screen.setCode("PERSON");
        screen.setName("المستخدمون");
        screenService.save(screen);

        screen = new Screen();
        screen.setCode("TEAM");
        screen.setName("الصلاحيات");
        screenService.save(screen);

        Iterator<Screen> iterator = screenService.findAll().iterator();

        while (iterator.hasNext()) {

            screen = iterator.next();

            Permission permissionFounded = permissionService
                    .findByCreateEntityAndUpdateEntityAndDeleteEntityAndReportEntityAndScreen(
                            true,
                            true,
                            true,
                            true,
                            screen);

            if (permissionFounded == null) {
                Permission permission = new Permission();
                permission.setScreen(screen);
                permission.setCreateEntity(true);
                permission.setUpdateEntity(true);
                permission.setDeleteEntity(true);
                permission.setReportEntity(true);
                permissionService.save(permission);

                Role role = new Role();
                role.setTeam(team);
                role.setPermission(permission);
                roleService.save(role);
            } else {
                Role role = new Role();
                role.setTeam(team);
                role.setPermission(permissionFounded);
                roleService.save(role);
            }
        }
    }
}
