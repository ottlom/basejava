package com.urise.webapp.storage;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.exception.ExistStorageException;
import com.urise.webapp.storage.exception.NotExistStorageException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static com.urise.webapp.ResumeTestData.createResume;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("C:\\Java\\projects\\basejava\\storage");
    protected Storage storage;

    private static final Resume RESUME_1 = createResume(UUID.randomUUID().toString(), "Alex");
    private static final Resume RESUME_2 = createResume(UUID.randomUUID().toString(), "Joe");
    private static final Resume RESUME_3 = createResume(UUID.randomUUID().toString(), "Marlin");
    private static final Resume RESUME_4 = createResume(UUID.randomUUID().toString(), "Elena");
    private static final Resume UUID_NOT_EXIT = createResume("null", "null");
    private static final Comparator<Resume> RESUME_UUID_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        RESUME_1.addContact(ContactType.PHONE_NUMBER,"6666666666");
        storage.save(RESUME_1);
        RESUME_2.addContact(ContactType.PHONE_NUMBER,"8888888888");
        storage.save(RESUME_2);
        RESUME_3.addContact(ContactType.PHONE_NUMBER,"9999999999");
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        Resume newResume = createResume(RESUME_1.getUuid(), "new resume");
        storage.update(newResume);
        assertEquals(newResume, storage.get(RESUME_1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.get(UUID_NOT_EXIT.getUuid());
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        Assert.assertEquals(3, storage.size());
        List<Resume> sortedResumes = new ArrayList<>(Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
        sortedResumes.sort(RESUME_UUID_COMPARATOR);
        Assert.assertEquals(sortedResumes, list);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(RESUME_1.getUuid());
        assertSize(2);
        storage.get(RESUME_1.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_NOT_EXIT.getUuid());
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXIT.getUuid());
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }
}