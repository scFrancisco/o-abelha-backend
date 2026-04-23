package com.example.oabelha.Controller;

import com.example.oabelha.Entity.Event;
import com.example.oabelha.Repository.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // GET /api/eventos
    @GetMapping
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    // GET /api/eventos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable Long id) {
        return eventRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/eventos
    @PostMapping
    public ResponseEntity<Event> create(@RequestBody Event event) {
        Event saved = eventRepository.save(event);
        return ResponseEntity.ok(saved);
    }

    // PUT /api/eventos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable Long id, @RequestBody Event updated) {
        return eventRepository.findById(id).map(event -> {
            event.setTitulo(updated.getTitulo());
            event.setDescricao(updated.getDescricao());
            event.setCorpoEvento(updated.getCorpoEvento());
            event.setDataEvento(updated.getDataEvento());
            event.setLocal(updated.getLocal());
            event.setImagemCapa(updated.getImagemCapa());
            return ResponseEntity.ok(eventRepository.save(event));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/eventos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!eventRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        eventRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}