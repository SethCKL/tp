package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyHireLah;

/**
 * A class to access HireLah data stored as a json file on the hard disk.
 */
public class JsonHireLahStorage implements HireLahStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonHireLahStorage.class);

    private Path filePath;

    public JsonHireLahStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getHireLahFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyHireLah> readHireLah() throws DataConversionException {
        return readHireLah(filePath);
    }

    /**
     * Similar to {@link #readHireLah()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyHireLah> readHireLah(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableHireLah> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableHireLah.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }
        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveHireLah(ReadOnlyHireLah hireLah) throws IOException {
        saveHireLah(hireLah, filePath);
    }

    /**
     * Similar to {@link #saveHireLah(ReadOnlyHireLah)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveHireLah(ReadOnlyHireLah hireLah, Path filePath) throws IOException {
        requireNonNull(hireLah);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableHireLah(hireLah), filePath);
    }

}
