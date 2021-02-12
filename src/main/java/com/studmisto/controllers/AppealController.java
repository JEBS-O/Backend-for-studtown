package com.studmisto.controllers;

import com.studmisto.entities.Appeal;
import com.studmisto.entities.Message;
import com.studmisto.entities.User;
import com.studmisto.entities.enums.AppealCategory;
import com.studmisto.entities.enums.AppealStatus;
import com.studmisto.entities.enums.Status;
import com.studmisto.services.AppealService;
import com.studmisto.services.EmailService;
import com.studmisto.services.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/appeal")
public class AppealController {
    private final AppealService appealService;
    private final MessageService messageService;
    private final EmailService emailService;

    public AppealController(AppealService appealService, MessageService messageService, EmailService emailService) {
        this.appealService = appealService;
        this.messageService = messageService;
        this.emailService = emailService;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getAppealById(@PathVariable("id") Appeal appeal,
                                             @AuthenticationPrincipal User user) {
        try {
            if(user.getId().longValue() == appeal.getAuthor().getId().longValue() || user.getStatus() == appeal.getRecipient() || user.getStatus() == Status.ADMINISTRATION) {
                return appealService.getAppealInfo(appeal);
            } else {
                log.warn("Звернення користувача {} до недоступної для нього заявки з іd {}", user.toString(), appeal.getId());
                return Map.of("errorMessage", "Звернення недоступне для вас");
            }
        } catch(NullPointerException e) {
            log.error("Звернення користувача {} до неіснуючого звернення", user.toString());
            return Map.of("errorMessage", "Звернення з таким id не знайдено");
        }
    }

    @GetMapping("/all")
    public List<Map<String, Object>> getAllAppeals(@AuthenticationPrincipal User user) throws ServletRequestBindingException {
        switch(user.getStatus()) {
            case WAITING_FOR_ORDER :
            case INMATE :
                return appealService.getUsersAppeals(user);
            case STAFF_3:
            case STAFF_5:
            case STAFF_6:
            case STAFF_7:
                return appealService.getAdminsAppeals(user);
            case ADMINISTRATION:
                return appealService.getAllAppeals();
        }
        throw new ServletRequestBindingException("Користувач без статусу");
    }

    @PostMapping("/addAppeal")
    public Map<String, Object> addAppeal(@AuthenticationPrincipal User user,
                                         @RequestParam("title") String title,
                                         @RequestParam("description") String description,
                                         @RequestParam("appealCategory") String appealCategoryName) {
        try {
            Appeal appeal = new Appeal();
            appeal.setAuthor(user);
            appeal.setTitle(title);
            Message message = new Message();
            message.setAuthor(user);
            message.setText(description);
            messageService.saveMessage(message);
            appeal.setMessages(List.of(message));
            appeal.setAppealCategory(AppealCategory.getAppealCategory(appealCategoryName));
            appeal.setAppealStatus(AppealStatus.CREATED);
            appeal.setRecipient(appealService.getRecipientForAppeal(user));
            appealService.saveAppeal(appeal);
            log.info("Додане нове звернення з id {}", appeal.getId());
            emailService.sendNotificationAboutNewAppeal(appeal);
            return Map.of("message", "Звернення зареєстроване. Про відповідь вам буде сповіщено на електронну пошту");
        } catch(IllegalArgumentException e) {
            log.error("При створенні звернення виникла помилка: {}", e.getMessage());
            return Map.of("errorMessage", e.getMessage());
        }
    }

    @PostMapping("/{id}/addMessage")
    public Map<String, Object> addMessageToAppeal(@AuthenticationPrincipal User user,
                                                  @PathVariable("id") Appeal appeal,
                                                  @RequestParam("text") String text) {
        Message message = new Message();
        message.setAuthor(user);
        message.setText(text);
        if (user.getStatus() == Status.ADMINISTRATION || user.getStatus() == Status.STAFF_3 || user.getStatus() == Status.STAFF_5 || user.getStatus()  == Status.STAFF_6 || user.getStatus() == Status.STAFF_7) {
            appeal.setAppealStatus(AppealStatus.IN_PROCESS);
        }
        messageService.saveMessage(message);
        List<Message> messages = appeal.getMessages();
        messages.add(message);
        appeal.setMessages(messages);
        appealService.saveAppeal(appeal);
        log.info("Додане нове повідомлення до звернення id {}", appeal.getId());
        emailService.sendNotificationAboutNewMessage(user, appeal);
        return Map.of("message", "Повідомлення додано");
    }

    @PutMapping("/{id}")
    public Map<String, Object> changeCategory(@PathVariable("id") Appeal appeal, @RequestParam("appealCategory") String appealCategoryName) {
        try {
            appeal.setAppealCategory(AppealCategory.getAppealCategory(appealCategoryName));
            appealService.saveAppeal(appeal);
            log.info("Оновлено статус звернення з id {} на {}", appeal.getId(), appeal.getAppealStatus().getName());
            return Map.of("message", "Статус оновлено");
        } catch(IllegalArgumentException | NullPointerException e) {
            log.error("При зміні статусу звернення виникла помилка: {}", e.getMessage());
            return Map.of("errorMessage", e.getMessage());
        }
    }

    @PutMapping("/close/{id}")
    public Map<String, Object> closeAppeal(@AuthenticationPrincipal User user, @PathVariable("id") Appeal appeal) {
        try {
            if(user.getId().longValue() == appeal.getAuthor().getId().longValue() || user.getStatus() == appeal.getRecipient() || user.getStatus() == Status.ADMINISTRATION) {
                appeal.setAppealStatus(AppealStatus.CLOSED);
                log.info("Оновлено статус звернення з id {} на {}", appeal.getId(), appeal.getAppealStatus().getName());
                return Map.of("message", "Звернення закрито");
            } else {
                log.info("Спроба закрити звернення {} користувачем {} без доступу до нього", appeal.getId(), user.toString());
                return Map.of("errorMessage", "Ви не маєте права закривати це звернення");
            }
        } catch(NullPointerException e) {
            log.error("При закритті звернення виникла помилка: {}", e.getMessage());
            return Map.of("errorMessage", e.getMessage());
        }
    }
}