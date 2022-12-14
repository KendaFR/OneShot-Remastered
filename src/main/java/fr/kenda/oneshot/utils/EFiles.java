package fr.kenda.oneshot.utils;

public enum EFiles {
    LOCATIONS("locations", "locations");

    private final String folder;
    private final String fileName;

    EFiles(String folder, String fileName) {
        this.folder = folder;
        this.fileName = fileName;
    }

    public final String getFileName() {
        return fileName;
    }

    public final String getFolder() {
        return folder;
    }
}
