package com.ajurasz.controller;

import com.ajurasz.model.Item;
import com.ajurasz.model.ItemType;
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

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @Autowired
    public ItemController(ManagerService managerService) {
        this.managerService = managerService;
        LOGGER.info("ItemController called");
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);
        return "item/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processCreationForm(@Validated({Item.Add.class}) @ModelAttribute Item item, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "item/add";
        }
        managerService.saveItem(item);
        redirectAttributes.addFlashAttribute("itemAdded", true);
        return "redirect:/item/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String initItemList(Model model) {
        List<Item> items = managerService.findAllItems();
        model.addAttribute("items", items);
        return "item/list";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String initEditForm(Model model, @PathVariable Long id) {
        Item item = managerService.getItem(id);
        model.addAttribute("item", item);
        return "item/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processEditForm(@Validated({ Item.Update.class }) @ModelAttribute Item item, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "item/edit";
        }
        managerService.saveItem(item);
        redirectAttributes.addFlashAttribute("itemEdited", true);
        return "redirect:/item/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String processItemDelete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        Item item = managerService.getItem(id);
        managerService.deleteItem(item);
        redirectAttributes.addFlashAttribute("itemDeleted", true);
        return "redirect:/item/list";
    }

    @RequestMapping(value = "/state/{id}/history", method = RequestMethod.GET)
    public String initStateHistory(Model model, @PathVariable Long id, Pageable pageable) {
        Page<StateHistory> historyPage = managerService.findAllStateHistoryByStateIdDesc(id, pageable);
        model.addAttribute("historyPage", historyPage);
        model.addAttribute("state", managerService.getState(id));
        return "item/state_history";
    }

    @RequestMapping(value = "/state/{id}/change", method = RequestMethod.POST)
    @ResponseBody
    public BigDecimal processStateChange(@PathVariable Long id, @RequestParam BigDecimal value) {
        State state = managerService.getState(id);
        state.setCurrentState(value);
        managerService.saveState(state);

        return state.getCurrentState();
    }
}
