package com.tugas.deploy.controller;

import com.tugas.deploy.model.User;
import com.tugas.deploy.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class UserController {

    // Injeksi Repository: Otak untuk berkomunikasi dengan PostgreSQL
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        // Mencegah user yang sudah login kembali ke halaman login
        if (session.getAttribute("user") != null) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginProcess(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {

        // Fakta: Mencari user di DATABASE secara dinamis berdasarkan input NIM
        User user = userRepository.findByNim(username);

        // Validasi: User harus ada di database DAN password harus cocok dengan NIM
        if (user != null && password.equals(user.getNim())) {
            session.setAttribute("user", username);
            return "redirect:/home";
        }

        // Jika gagal, tampilkan pesan error
        model.addAttribute("error", "Username atau Password salah!");
        return "login";
    }

    @GetMapping("/home")
    public String homePage(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";

        // MENGAMBIL DATA NYATA DARI POSTGRESQL
        List<User> listMahasiswa = userRepository.findAll();

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
        try {
            // Fakta: Mencegah error 500 akibat Primary Key (ID) null
            if (user.getId() == null || user.getId().isEmpty()) {
                user.setId(UUID.randomUUID().toString());
            }

            // MENYIMPAN DATA PERMANEN KE POSTGRESQL
            userRepository.save(user);
            return "redirect:/home";

        } catch (DataIntegrityViolationException e) {
            // Fakta: Menangkap error jika kamu memasukkan NIM yang sudah ada di database
            System.err.println("GAGAL: NIM " + user.getNim() + " sudah terdaftar di database!");
            return "redirect:/form";

        } catch (Exception e) {
            // Menangkap error lain di luar dugaan
            System.err.println("ERROR FATAL: " + e.getMessage());
            return "redirect:/form";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}