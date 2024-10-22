package be.rubus.microstream.training.export.model;

import one.microstream.storage.types.StorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class InitData {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitData.class);

    private InitData() {
    }

    public static void ensureDefaultData(StorageManager storageManager) {
        Root root = (Root) storageManager.root();
        root.setStorageManager(storageManager);

        if (!root.getUsers().isEmpty()) {
            LOGGER.info("!!!! Data already available, nothing to do.");
            // We have already users and thus assume the database has its default.
            return;
        }
        LOGGER.info("!!!! Adding the default data.");

        User johnDoe = new User("John Doe", "john.doe@acme.org");
        User janeDoe = new User("Jane Doe", "jane.doe@acme.org");

        root.addUser(johnDoe);
        root.addUser(janeDoe);

        addBook(root, "9780140434132", "Northanger Abbey", "Austen, Jane", 1814);
        addBook(root, "9780007148387", "War and Peace", "Tolstoy, Leo", 1865);
        addBook(root, "9780141182490", "Mrs. Dalloway", "Woolf, Virginia", 1925);
        addBook(root, "9780312243029", "The Hours", "Cunnningham, Michael", 1999);
        addBook(root, "9780141321097", "Huckleberry Finn", "Twain, Mark", 1865);
        addBook(root, "9780141439723", "Bleak House", "Dickens, Charles", 1870);
        addBook(root, "9780520235755", "The adventures of Tom Sawyer", "Twain, Mark", 1862);
        addBook(root, "9780156030410", "A Room of One's Own", "Woolf, Virginia", 1922);

        addBook(root, "9780140707342", "Hamlet, Prince of Denmark", "Shakespeare", 1603);
        addBook(root, "9780395647400", "Lord of the Rings", "Tolkien, J.R.", 1937);

        Book annaKarenina = addBook(root, "9780679783305", "Anna Karenina", "Tolstoy, Leo", 1875);
        janeDoe.addBook(annaKarenina, storageManager);

        Book book = addBook(root, "9780060114183", "One Hundred Years of Solitude", "Marquez", 1967);
        janeDoe.addBook(book, storageManager);

        Book harryPotter = addBook(root, "9780747532743", "Harry Potter", "Rowling, J.K.", 2000);
        johnDoe.addBook(harryPotter, storageManager);


    }

    private static Book addBook(Root root, String isbn, String name, String author, int year) {
        Book result = new Book(isbn, name, author, year);
        root.addBook(result);
        return result;
    }
}
