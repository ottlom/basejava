package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.storage.exception.ExistStorageException;
import com.urise.webapp.storage.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {
    protected final Storage storage;

    private static final Resume RESUME_1 = ResumeTestData.createResume("uuid1", "Alex");
    private static final Resume RESUME_2 = ResumeTestData.createResume("uuid2", "Joe");
    private static final Resume RESUME_3 = ResumeTestData.createResume("uuid3", "Marlin");
    private static final Resume RESUME_4 = ResumeTestData.createResume("uuid4", "Elena");
    private static final Resume UUID_NOT_EXIT = ResumeTestData.createResume("null", "null");

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
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
        storage.update(RESUME_1);
        Assert.assertSame(RESUME_1, storage.get(RESUME_1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(UUID_NOT_EXIT);
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        Assert.assertEquals(3, storage.size());
        Assert.assertEquals(list, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    public void save() {
        int size = storage.size();
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(++size);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        int size = storage.size();
        storage.delete(RESUME_1.getUuid());
        assertSize(--size);
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