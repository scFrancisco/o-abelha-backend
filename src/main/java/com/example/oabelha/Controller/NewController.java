package com.example.oabelha.Controller;


import com.example.oabelha.Entity.New;
import com.example.oabelha.Repository.NewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/noticias")
@CrossOrigin(origins = "*")
public class NewController {

    private final NewRepository repo;

    public NewController(NewRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public New save(@RequestBody New noticia) {
        return repo.save(noticia);
    }

    @GetMapping
    public List<New> findAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<New> findById(@PathVariable long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
