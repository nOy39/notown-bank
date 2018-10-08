package org.a2lpo.bank.notownbank.service;

import org.a2lpo.bank.notownbank.model.message.AdminMessage;
import org.a2lpo.bank.notownbank.repos.AdminMessageRepo;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class MessageService {

    private final AdminMessageRepo adminMessageRepo;

    public MessageService(AdminMessageRepo adminMessageRepo) {
        this.adminMessageRepo = adminMessageRepo;
    }

    public void createServiceMessage(String serviceComment, URI location, Object object) {
        String message = "Create new manager, view details info "+location;
        AdminMessage adminMessage = new AdminMessage(message, serviceComment);
        adminMessageRepo.save(adminMessage);
    }
}
