package com.task.taskmanager.controller;

import com.task.taskmanager.dto.common.PageResponse;
import com.task.taskmanager.entity.Role;
import com.task.taskmanager.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<PageResponse<Role>> getRoles(
            @RequestParam(name = "roleName", required = false) String roleName,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortField", defaultValue = "roleId") String sortField,
            @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection
    ) {
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                roleService.getRoles(roleName, pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(roleService.getRole(id));
    }

    @PostMapping
    public ResponseEntity<Role> save(@RequestBody Role role) {
        return ResponseEntity.ok(
                roleService.save(role)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> update(
            @PathVariable(name = "id") Long id,
            @RequestBody Role role
    ) {
        role.setRoleId(id);
        return ResponseEntity.ok(
                roleService.save(role)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        roleService.delete(id);
        return ResponseEntity.ok().build();
    }

}