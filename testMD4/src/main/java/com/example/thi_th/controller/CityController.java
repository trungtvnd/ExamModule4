package com.example.thi_th.controller;

import com.example.thi_th.model.City;
import com.example.thi_th.model.Country;
import com.example.thi_th.service.ICityService;
import com.example.thi_th.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/city")
public class CityController {

    @Autowired
    private ICountryService iCountryService;

    @Autowired
    private ICityService iCityService;

    @ModelAttribute(name = "country")
    public Iterable<Country> findAdd() {
        return iCountryService.findAll();
    }

    @GetMapping
    public ModelAndView showAll(@PageableDefault(value = 3) Pageable pageable,
                                @RequestParam Optional<String> search) {
        ModelAndView modelAndView = new ModelAndView("city/list");
        Page<City> cities;
        if (search.isPresent()) {
            cities = iCityService.findAllByName(pageable, search.get());
            modelAndView.addObject("search", search.get());
        } else {
            cities = iCityService.findAll(pageable);

        }
        modelAndView.addObject("cities", cities);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("city/create");
        modelAndView.addObject("city", new City());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createCity(@Valid  @ModelAttribute("city") City city,
                             BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("city/create");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("city", city);
            return modelAndView;
        }
        iCityService.save(city);
        modelAndView.addObject("status", true);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("city/edit");
        Optional<City> city = iCityService.findById(id);
        city.ifPresent(value -> modelAndView.addObject("city", value));
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editCity(@Valid  @ModelAttribute("city") City city,
                           BindingResult bindingResult,
                           @PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("city/edit");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("city", city);
            return  modelAndView;
        }
        city.setId(id);
        iCityService.save(city);
        modelAndView.addObject("statusEdit", true);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PageableDefault(value = 3) Pageable pageable, @PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("city/list");
        iCityService.delete(id);
        Page<City> cities = iCityService.findAll(pageable);
        modelAndView.addObject("cities", cities);
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView showDetail(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("city/detail");
        Optional<City> city = iCityService.findById(id);
        city.ifPresent(value -> modelAndView.addObject("city", value));
        return modelAndView;
    }

}
