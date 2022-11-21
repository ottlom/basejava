import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size;
    private int index;

    void clear() {
        Arrays.stream(storage, 0, size)
                .forEach(resume -> resume = null);
        size = 0;
    }

    void save(Resume r) {
        if (size > storage.length) {
            System.out.println("Array ran out of space");
        } else if (getIndex(r.uuid) == -1) {
            storage[size] = r;
            System.out.println("resume " + r.uuid + " was added");
            size++;
        } else {
            System.out.println("List's already have resume with uuid = " + r.uuid);
        }
    }

    Resume get(String uuid) {
        index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        {
            return null;
        }
    }

    void delete(String uuid) {
        index = getIndex(uuid);
        if (index == -1) {
            System.out.println("You try delete element such not found in list");
        } else {
            System.arraycopy(storage, index + 1, storage, index, size);
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }

    private int getIndex(String uuid) {
        return IntStream.range(0, size)
                .filter(resume -> storage[resume].uuid.equals(uuid))
                .findFirst()
                .orElse(-1);
    }
}
