package ir.bu.cloudlystorage.service;

import ir.bu.cloudlystorage.dto.TokenDto;
import ir.bu.cloudlystorage.model.CloudUser;
import org.springframework.stereotype.Service;

@Service
public interface FileService {
    TokenDto login(CloudUser cloudUser);
}
