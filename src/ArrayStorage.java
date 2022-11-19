import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size;

    void clear() {
        Arrays.stream(storage, 0, size)
                .forEach(resume -> resume = null);
        size = 0;
    }

    void save(Resume r) {
        if (size > storage.length) {
            System.out.println("Array ran out of space");
        } else {
            if (getIndex(r.uuid) == -1) {
                storage[size] = r;
                System.out.println("resume " + r.uuid + " was added");
                size++;
            } else {
                System.out.println("List's already have resume with uuid = " + r.uuid);
            }
        }
    }

    Resume get(String uuid) {
        if (getIndex(uuid) != -1) {
            return storage[getIndex(uuid)];
        } else {
            return null;
        }
    }

    void delete(String uuid) {
        if (getIndex(uuid) == -1) {
            System.out.println("You try delete element such not found in list");
        } else {
            System.arraycopy(storage, getIndex(uuid) + 1, storage, getIndex(uuid), size);
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
        int index = IntStream.range(0, size)
                .filter(resume -> storage[resume].uuid.equals(uuid))
                .findFirst()
                .orElse(-1);
        return index;
    }
}
