package com.example.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.store.model.Pokemon;
import com.example.store.model.PokemonVariante;
import com.example.store.repository.PokemonRepository;
import com.example.store.repository.PokemonVarianteRepository;

@Controller
public class PokemonController {

    @Autowired
    private PokemonVarianteRepository repository;

    @Autowired
    private PokemonRepository pokemonRepository;


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("variantes", repository.findAll());
        return "index";
    }


    @GetMapping("/pokemon")
    public String listar(Model model) {

        List<PokemonVariante> lista = repository.findAll();

        double totalInventario = lista.stream()
                .mapToDouble(p ->
                        (p.getPrecio() != null ? p.getPrecio() : 0) *
                        (p.getStock() != null ? p.getStock() : 0)
                )
                .sum();

        model.addAttribute("variantes", lista);
        model.addAttribute("variante", new PokemonVariante());
        model.addAttribute("totalInventario", totalInventario);

        return "inventario";
    }


    @GetMapping("/admin")
    public String login() {
        return "login";
    }


    @PostMapping("/login")
    public String validarLogin(@RequestParam String user, @RequestParam String pass) {

        if (user.equals("ADMIN") && pass.equals("12345")) {
            return "redirect:/pokemon";
        }

        return "redirect:/admin";
    }

  
    @PostMapping("/pokemon/guardar")
    public String guardar(@ModelAttribute PokemonVariante variante) {

        String nombre = variante.getPokemon().getNombre().toLowerCase().trim();

        Pokemon pokemon = pokemonRepository.findAll().stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);

        if (pokemon == null) {
            pokemon = new Pokemon();
            pokemon.setNombre(nombre);
            pokemonRepository.save(pokemon);
        }

        if (variante.getPrecio() == null) variante.setPrecio(0.0);
        if (variante.getStock() == null) variante.setStock(0);

        variante.setPokemon(pokemon);
        repository.save(variante);

        return "redirect:/pokemon";
    }


    @PostMapping("/pokemon/editarStock")
    public String editarStock(@RequestParam Long id, @RequestParam Integer stock) {

        PokemonVariante p = repository.findById(id).orElse(null);

        if (p != null) {
            p.setStock(stock);
            repository.save(p);
        }

        return "redirect:/pokemon";
    }


    @GetMapping("/pokemon/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/pokemon";
    }


    @GetMapping("/buscar")
    public String buscar(@RequestParam String query, Model model) {

        List<PokemonVariante> lista = repository.findAll().stream()
                .filter(p ->
                        p.getPokemon().getNombre() != null &&
                        p.getPokemon().getNombre().toLowerCase().contains(query.toLowerCase())
                )
                .toList();


        if (lista.isEmpty()) {
            model.addAttribute("variantes", repository.findAll());
            model.addAttribute("mensaje", "No se encontró el Pokémon, mostrando todo el inventario");
        } else {
            model.addAttribute("variantes", lista);
        }

        return "index";
    }
}