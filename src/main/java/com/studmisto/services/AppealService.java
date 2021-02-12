package com.studmisto.services;

import com.studmisto.entities.Appeal;
import com.studmisto.entities.User;
import com.studmisto.entities.enums.Status;
import com.studmisto.repositories.AppealRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppealService {
    private final AppealRepository appealRepository;

    public AppealService(AppealRepository appealRepository) {
        this.appealRepository = appealRepository;
    }

    public Map<String, Object> getAppealInfo(Appeal appeal) {
        Map<String, Object> answer = new HashMap<>();
        answer.put("id", appeal.getId());
        answer.put("author", appeal.getAuthor().toString());
        answer.put("title", appeal.getTitle());
        answer.put("appealCategory", appeal.getAppealCategory().getName());
        answer.put("appealStatus", appeal.getAppealStatus().getName());
        answer.put("creationDate", appeal.getCreationDate());
        answer.put("messages", appeal.getMessages());
        return answer;
    }

    public List<Map<String, Object>> getUsersAppeals(User user) {
        List<Map<String, Object>> answer = new ArrayList<>();
        for(Appeal appeal : appealRepository.findAppealsByAuthor(user)) {
            answer.add(getAppealInfo(appeal));
        }
        return answer;
    }

    public List<Map<String, Object>> getAdminsAppeals(User user) {
        List<Map<String, Object>> answer = new ArrayList<>();
        for(Appeal appeal : appealRepository.findAppealsByRecipient(user.getStatus())) {
            answer.add(getAppealInfo(appeal));
        }
        return answer;
    }

    public List<Map<String, Object>> getAllAppeals() {
        List<Map<String, Object>> answer = new ArrayList<>();
        for(Appeal appeal : appealRepository.findAll()) {
            answer.add(getAppealInfo(appeal));
        }
        return answer;
    }

    public Status getRecipientForAppeal(User user) throws IllegalArgumentException {
        switch(user.getRoom().getDorm()) {
            case DORM_3:
                return Status.STAFF_3;
            case DORM_5:
                return Status.STAFF_5;
            case DORM_6:
                return Status.STAFF_6;
            case DORM_7:
                return Status.STAFF_7;
        }
        throw new IllegalArgumentException("Даному користувачу заборонено відрпавляти звернення");
    }

    public void saveAppeal(Appeal appeal) {
        appealRepository.save(appeal);
    }
}
