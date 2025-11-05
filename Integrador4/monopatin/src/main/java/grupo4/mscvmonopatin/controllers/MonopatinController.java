package grupo4.mscvmonopatin.controllers;

import grupo4.mscvmonopatin.model.Monopatin;
import grupo4.mscvmonopatin.repository.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/monopatines")
public class MonopatinController {

    @Autowired
    private MonopatinRepository repository;

    @GetMapping
    public List<Monopatin> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Monopatin create(@RequestBody Monopatin monopatin) {
        return repository.save(monopatin);
    }
}
