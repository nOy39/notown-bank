package org.a2lpo.bank.notownbank.service;

import org.a2lpo.bank.notownbank.model.message.AdminMessage;
import org.a2lpo.bank.notownbank.repos.AdminMessageRepo;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final AdminMessageRepo adminMessageRepo;

    public MessageService(AdminMessageRepo adminMessageRepo) {
        this.adminMessageRepo = adminMessageRepo;
    }

    public void createServiceMessage(String serviceComment, String location) {
        String message = "Create new manager, view details info " + location;
        AdminMessage adminMessage = new AdminMessage(message, serviceComment);
        adminMessageRepo.save(adminMessage);
    }
}
