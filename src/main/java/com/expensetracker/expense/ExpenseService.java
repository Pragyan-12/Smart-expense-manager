package com.expensetracker.expense;

import com.expensetracker.auth.User;
import com.expensetracker.auth.UserRepository;
import com.expensetracker.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public ExpenseService(ExpenseRepository expenseRepository,
                          UserRepository userRepository,
                          JwtService jwtService) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    private User extractUser(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt = authHeader.substring(7);
        String email = jwtService.extractUsername(jwt);
        return userRepository.findByEmail(email).orElseThrow();
    }

    public List<Expense> getAllExpenses(HttpServletRequest request) {
        User user = extractUser(request);
        return expenseRepository.findByUser(user);
    }

    public Expense getExpenseById(Long id, HttpServletRequest request) {
        User user = extractUser(request);
        return expenseRepository.findById(id)
                .filter(expense -> expense.getUser().getId().equals(user.getId()))
                .orElseThrow();
    }

    public Expense addExpense(Expense expense, HttpServletRequest request) {
        User user = extractUser(request);
        expense.setUser(user);
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense updatedExpense, HttpServletRequest request) {
        Expense existingExpense = getExpenseById(id, request);
        existingExpense.setTitle(updatedExpense.getTitle());
        existingExpense.setCategory(updatedExpense.getCategory());
        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setDate(updatedExpense.getDate());
        return expenseRepository.save(existingExpense);
    }

    public void deleteExpense(Long id, HttpServletRequest request) {
        Expense existingExpense = getExpenseById(id, request);
        expenseRepository.delete(existingExpense);
    }
}
