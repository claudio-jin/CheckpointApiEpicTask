package br.com.fiap.epictaskapi.controller;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.epictaskapi.model.Task;
import br.com.fiap.epictaskapi.service.TaskService;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService service;
    
    @GetMapping
    @PreAuthorize("permitAll()")
    @Cacheable("task")
    // HashMap - Redis
    //select from task limit 5 offset 10
    public Page<Task> index(@PageableDefault(size = 5) Pageable paginacao){
        return service.listAll(paginacao);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @CacheEvict(value = "task", allEntries = true)
    public ResponseEntity<Task> create(@RequestBody @Valid Task task){
        service.save(task);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(task);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "task", allEntries = true)
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        var optional = service.getById(id);

        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        service.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> show(@PathVariable Long id){
        return ResponseEntity.of(service.getById(id));
    }

    @PutMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    @CacheEvict(value = "task", allEntries = true)
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody @Valid Task newTask){
        var optional = service.getById(id);

        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        var task = optional.get();
        BeanUtils.copyProperties(newTask, task);
        task.setId(id);

        service.save(task);
        return ResponseEntity.ok(task);
    }




}
