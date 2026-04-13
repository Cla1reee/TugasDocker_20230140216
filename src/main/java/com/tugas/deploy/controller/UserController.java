package com.tugas.deploy.controller;

import com.tugas.deploy.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    // List static agar data tidak hilang saat pindah halaman (Temporary)
    private static List<User> listMahasiswa = new ArrayList<>();

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginProcess(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session) {
        // Password adalah NIM Anda masing-masing [cite: 1937]
        if ("admin".equals(username) && "20230140216".equals(password)) {
            session.setAttribute("user", username);
            return "redirect:/home";
        }
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String homePage(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";

        model.addAttribute("dataMhs", listMahasiswa);
        return "home";
    }

    @GetMapping("/form")
    public String formPage(HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        return "form";
    }

    @PostMapping("/save")
    public String saveData(@ModelAttribute User user) {
        listMahasiswa.add(user);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}