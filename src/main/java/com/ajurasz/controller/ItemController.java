package com.ajurasz.controller;

import com.ajurasz.model.Item;
import com.ajurasz.model.State;
import com.ajurasz.model.StateHistory;
import com.ajurasz.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Arek Jurasz
 */
@RequestMapping(value = "/item")
@Controller
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    private ManagerService managerService;

//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
//    }

    @Autowired
    public ItemController(ManagerService managerService) {
        this.managerService = managerService;
        LOGGER.debug("ItemController called");
    }

    /***********************************/
    /*********  ADD ITEMS  *************/
    /***********************************/
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);
        return "item/add";
    }

    private String add(Item item, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            LOGGER.debug("errors in add form");
            return "item/add_validation";
        }
        managerService.saveItem(item);
        redirectAttributes.addFlashAttribute("itemAdded", true);
        return "redirect:/item/list";
    }

    @RequestMapping(value = "/add-coal", method = RequestMethod.POST)
    public String processCreationFormForCoal(@Validated({Item.Add_Coal.class}) @ModelAttribute Item item, BindingResult result, RedirectAttributes redirectAttributes) {
        LOGGER.debug("add-coal method called");
        return add(item, result, redirectAttributes);
    }

    @RequestMapping(value = "/add-construction", method = RequestMethod.POST)
    public String processCreationFormForConstruction(@Validated({Item.Add_Construction.class}) @ModelAttribute Item item, BindingResult result, RedirectAttributes redirectAttributes) {
        LOGGER.debug("add-construction method called");
        return add(item, result, redirectAttributes);
    }

    @RequestMapping(value = "/add-service", method = RequestMethod.POST)
    public String processCreationFormForService(@Validated({Item.Add_Service.class}) @ModelAttribute Item item, BindingResult result, RedirectAttributes redirectAttributes) {
        LOGGER.debug("add-service method called");
        return add(item, result, redirectAttributes);
    }


    /***********************************/
    /*********  EDIT ITEMS  ************/
    /***********************************/
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String initEditForm(Model model, @PathVariable Long id) {
        Item item = managerService.getItem(id);
        model.addAttribute("item", item);
        return "item/edit";
    }

    private String edit(Item item, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "item/edit";
        }
        managerService.saveItem(item);
        redirectAttributes.addFlashAttribute("itemEdited", true);
        return "redirect:/item/list";
    }

    @RequestMapping(value = "/edit-coal/{id}", method = RequestMethod.POST)
    public String processEditFormForCoal(@Validated({ Item.Update_Coal.class }) @ModelAttribute Item item, BindingResult result, RedirectAttributes redirectAttributes) {
        LOGGER.debug("edit-coal method called");
        return edit(item, result, redirectAttributes);
    }

    @RequestMapping(value = "/edit-construction/{id}", method = RequestMethod.POST)
    public String processEditFormForConstruction(@Validated({ Item.Update_Construction.class }) @ModelAttribute Item item, BindingResult result, RedirectAttributes redirectAttributes) {
        LOGGER.debug("edit-construction method called");
        return edit(item, result, redirectAttributes);
    }

    @RequestMapping(value = "/edit-service/{id}", method = RequestMethod.POST)
    public String processEditFormForService(@Validated({ Item.Update_Service.class }) @ModelAttribute Item item, BindingResult result, RedirectAttributes redirectAttributes) {
        LOGGER.debug("edit-service method called");
        return edit(item, result, redirectAttributes);
    }

    /***********************************/
    /********  DELETE ITEMS  ***********/
    /***********************************/
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String processItemDelete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        LOGGER.debug("delete method called");
        Item item = managerService.getItem(id);
        managerService.deleteItem(item);
        redirectAttributes.addFlashAttribute("itemDeleted", true);
        return "redirect:/item/list";
    }


    /***********************************/
    /********  LIST ITEMS  *************/
    /***********************************/
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String initItemList(Model model) {
        LOGGER.debug("list method called");
        List<Item> items = managerService.findAllItems();
        model.addAttribute("items", items);
        return "item/list";
    }


    /***********************************/
    /********  STATE HISTORY  **********/
    /***********************************/
    @RequestMapping(value = "/state/{id}/history", method = RequestMethod.GET)
    public String initStateHistory(Model model, @PathVariable Long id) {
        List<StateHistory> historyList = managerService.findAllStateHistoryByStateIdDesc(id);
        model.addAttribute("historyList", historyList);
        model.addAttribute("state", managerService.getState(id));
        return "item/state_history";
    }

    @RequestMapping(value = "/state/{id}/change", method = RequestMethod.POST)
    @ResponseBody
    public String processStateChange(@PathVariable Long id, @RequestParam BigDecimal value) {
        LOGGER.debug("state change method called");
        Item item = managerService.getItem(id);
        State state = item.getState();
        state.setCurrentState(value);
        managerService.saveState(state);
        return state.getCurrentState() + " (" + item.getUnit().getUnit() + ")" ;
    }
}
