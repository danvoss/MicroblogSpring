package com.dvoss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Dan on 6/20/16.
 */
@Controller
public class MicroblogController {

    @Autowired
    MessagesRepository messages;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        Iterable<Message> msgs = messages.findAll();

        //to retain original order (thanks, Porter!):
        ArrayList<Message> msgList = new ArrayList<>();
        for (Message msg : msgs) {
            msgList.add(msg);
        }
        // & compareTo override in Message class
        Collections.sort(msgList);

        model.addAttribute("messages", msgList);

        //model.addAttribute("messages", msgs);
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(String username, HttpSession session) {
        session.setAttribute("username", username);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/add-message", method = RequestMethod.POST)
    public String add(String message, HttpSession session) throws Exception {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new Exception("Not logged in.");
        }
        Message msg = new Message(message);
        messages.save(msg);
        return "redirect:/";
    }

    @RequestMapping(path = "/delete-message", method = RequestMethod.POST)
    public String delete(Integer id, HttpSession session) throws Exception {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new Exception("Not logged in.");
        }
        messages.delete(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/edit-message", method = RequestMethod.POST)
    public String edit(String message, Integer id, HttpSession session) throws Exception {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new Exception("Not logged in.");
        }
        // wrong ids ??
        Message m = new Message(id, message);
        messages.save(m);
        return "redirect:/";
    }
}
