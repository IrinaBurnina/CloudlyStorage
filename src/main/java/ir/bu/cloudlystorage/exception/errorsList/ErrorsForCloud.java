package ir.bu.cloudlystorage.exception.errorsList;

import lombok.Getter;

@Getter
public enum ErrorsForCloud {
    BAD_CREDENTIALS("Bad credentials. Authorization error. The entered login or password isn't corrected.", 1),
    ERROR_INPUT_DATA("Error input data. The entered data isn't corrected.", 2),
    UNAUTHORIZED_ERROR("Unauthorized error. You should to authorized. ", 3),
    ERROR_DELETE_FILE("Error delete file. File isn't deleted.", 4),
    ERROR_UPLOAD_FILE("Error upload file. File isn't upload.", 5),
    ERROR_UPLOAD_DATA("Error upload data. Uploading data isn't corrected.", 6),
    ERROR_GETTING_FILE_LIST("Error getting file list. Files aren't getting.", 7),
    TOKEN_NOT_FOUND_EXCEPTION("Token is not found.", 8);

    private final Integer id;
    private final String message;

    ErrorsForCloud(String message, Integer id) {
        this.message = message;
        this.id = id;
    }

    @Override
    public String toString() {
        return id + ": " + message;
    }

}
