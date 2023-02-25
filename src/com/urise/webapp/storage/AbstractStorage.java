package com.urise.webapp.storage;import com.urise.webapp.exception.ExistStorageException;import com.urise.webapp.exception.NotExistStorageException;import com.urise.webapp.model.Resume;import java.util.Comparator;import java.util.List;public abstract class AbstractStorage implements Storage {    @Override    public void save(Resume r) {        doSave(getNotExistingSearchKey(r.getUuid()), r);    }    @Override    public void delete(String uuid) {        doDelete(getExistingSearchKey(uuid));    }    @Override    public void update(Resume r) {        doUpdate(getExistingSearchKey(r.getUuid()), r);    }    @Override    public Resume get(String uuid) {        return doGet(getExistingSearchKey(uuid));    }    private Object getExistingSearchKey(String uuid) {        Object searchKey = getSearchKey(uuid);        if (!isExist(searchKey)) {            throw new NotExistStorageException(uuid);        } else {            return searchKey;        }    }    private Object getNotExistingSearchKey(String uuid) {        Object searchKey = getSearchKey(uuid);        if (isExist(searchKey)) {            throw new ExistStorageException(uuid);        }        return searchKey;    }    //comparator    public static final Comparator<Resume> COMPARATOR = new Comparator<Resume>() {        @Override        public int compare(Resume o1, Resume o2) {            if (o1.getFullName().equals(o2.getFullName())) {                return o1.getUuid().compareTo(o2.getUuid());            } else return o1.getFullName().compareTo(o2.getFullName());        }    };    @Override    public List<Resume> getAllSorted() {        List<Resume> returnAllResume = getCopyStorage();        returnAllResume.sort(COMPARATOR);        return returnAllResume;    }    protected abstract List<Resume> getCopyStorage();    protected abstract Object getSearchKey(String uuid);    protected abstract boolean isExist(Object searchKey);    protected abstract void doSave(Object searchKey, Resume resume);    protected abstract void doDelete(Object searchKey);    protected abstract void doUpdate(Object searchKey, Resume resume);    protected abstract Resume doGet(Object searchKey);}