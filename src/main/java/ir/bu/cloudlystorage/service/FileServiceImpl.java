package ir.bu.cloudlystorage.service;

import ir.bu.cloudlystorage.dto.TokenDto;
import ir.bu.cloudlystorage.model.CloudUser;
import ir.bu.cloudlystorage.repository.FileRepositoryImpl;


public class FileServiceImpl implements FileService {
    private final FileRepositoryImpl fileRepositoryImpl;

    public FileServiceImpl(FileRepositoryImpl fileRepositoryImpl) {
        this.fileRepositoryImpl = fileRepositoryImpl;
    }

    @Override
    public TokenDto login(CloudUser cloudUser) {
        return new TokenDto();
    }
}
