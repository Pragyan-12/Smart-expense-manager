package com.expensetracker.expense;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("")
    public ResponseEntity<List<Expense>> getAllExpenses(HttpServletRequest request) {
        return ResponseEntity.ok(expenseService.getAllExpenses(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpense(@PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity.ok(expenseService.getExpenseById(id, request));
    }

    @PostMapping("")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense, HttpServletRequest request) {
        return ResponseEntity.ok(expenseService.addExpense(expense, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expense, HttpServletRequest request) {
        return ResponseEntity.ok(expenseService.updateExpense(id, expense, request));
    }

    @DeleteMapping("del/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id, HttpServletRequest request) {
        expenseService.deleteExpense(id, request);
        System.out.println("deleted"+id);
        return ResponseEntity.noContent().build();
    }
}
