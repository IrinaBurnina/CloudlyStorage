package ir.bu.cloudlystorage.dto;

public class ErrorDto {
    private final String message;
    private final int id;

    public ErrorDto(String message, int id) {
        this.message = message;
        this.id = id;
    }
}