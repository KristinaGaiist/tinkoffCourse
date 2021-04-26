package unit1.task5;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The {@code Note} class represents data about a note
 * The class {@code Note} includes fields id, name, filling a note, date the note was created
 * and date the note was modified. It also includes methods for these fields that allow to get and set field values
 *
 * @author Kristina Kirinyuk
 */
public class Note {

    /**
     * The value is used to identify a note
     */
    private UUID id;
    /**
     * The value is used to name a note. Name must be unique and not null
     */
    private String name;
    /**
     * The value is used to full a note
     */
    private String content;
    /**
     * The value is used to indicate. The time when the note was created
     */
    private LocalDateTime createdDate;
    /**
     * The value is used to indicate. The time when the note was last time modified
     */
    private LocalDateTime modifiedDate;

    /**
     * Returns the unique ID 64 bits of this UUID's 128 bit value.
     *
     * @return The unique {@code id} of note
     */
    public UUID getId() {
        return id;
    }

    /**
     * Set value for note ID
     *
     * @param id - the unique ID 64 bits of this UUID's 128 bit value
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Returns the name of note String value
     *
     * @return The {@code name} of note
     */
    public String getName() {
        return name;
    }

    /**
     * Set value for note name.
     *
     * @param name - String value.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the text of note String value.
     *
     * @return The {@code content} of note
     */
    public String getContent() {
        return content;
    }

    /**
     * Set value for note content.
     *
     * @param content - the text of note
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the date when note was created. A date-time without a time-zone in the ISO-8601 calendar system,
     * such as {@code 2020-12-03T10:15:30}.
     *
     * @return The {@code createdDate} of note
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Set value for note created date.
     *
     * @param createdDate - a date-time without a time-zone in the ISO-8601 calendar system
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Returns the date when note was modified. A date-time without a time-zone in the ISO-8601 calendar system,
     * such as {@code 2020-12-03T10:15:30}.
     *
     * @return The {@code modifiedDate} of note
     */
    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Set value for note modified date.
     *
     * @param modifiedDate - a date-time without a time-zone in the ISO-8601 calendar system
     */
    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        String modifiedDateString = modifiedDate == null ? "" : ", modifiedDate=" + modifiedDate;
        String createdDateString = createdDate == null ? "" : ", createdDate=" + createdDate;
        return "name='" + name + '\'' +
                ", content='" + content + '\'' +
                createdDateString +
                modifiedDateString;
    }
}
