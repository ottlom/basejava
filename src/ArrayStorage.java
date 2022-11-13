import java.util.Arrays;
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
            System.out.println("В массиве закончилось место");
        } else {
            int sameResume = IntStream.range(0, size)
                    .filter(resume -> r.uuid.equals(storage[resume].uuid))
                    .findFirst()
                    .orElse(-1);
            if (sameResume == -1) {
                storage[size] = r;
                System.out.println("resume " + r.uuid + " was added");
                size++;
            } else {
                System.out.println("List's already have resume with uuid = " + r.uuid);
            }
        }
    }

    Resume get(String uuid) {
        Resume returnResume = Arrays.stream(storage, 0, size)
                .filter(resume -> resume.uuid.equals(uuid))
                .findFirst()
                .orElse(null);
        return returnResume;
    }

    void delete(String uuid) {
        int index = IntStream.range(0, size)
                .filter(resume -> storage[resume].uuid.equals(uuid))
                .findFirst()
                .orElse(-1);
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
        return Arrays.copyOfRange(storage, 0, size);
    }

    int size() {
        return size;
    }
}
