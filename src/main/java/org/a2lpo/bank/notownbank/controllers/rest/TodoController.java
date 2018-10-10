package org.a2lpo.bank.notownbank.controllers.rest;

import org.a2lpo.bank.notownbank.model.ToDo;
import org.a2lpo.bank.notownbank.payload.TodoRequest;
import org.a2lpo.bank.notownbank.repos.ToDoRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {

    private final ToDoRepo toDoRepo;

    public TodoController(ToDoRepo toDoRepo) {
        this.toDoRepo = toDoRepo;
    }

    @PostMapping("/todo")
    public boolean addTask(@RequestBody TodoRequest todoRequest) {
        ToDo todo = new ToDo();
        todo.setMessage(todoRequest.getMessage());
        toDoRepo.save(todo);
        return true;
    }
    @GetMapping
    public List<ToDo> toDoList() {
        return toDoRepo.findAll();
    }
    @PutMapping("{id}")
    public ToDo modify(@PathVariable("id") ToDo toDo, @RequestBody TodoRequest todoRequest) {
        toDo.setMessage(todoRequest.getMessage());
        toDoRepo.save(toDo);
        return toDo;
    }
}
