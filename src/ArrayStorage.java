import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;
    void clear() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                storage[i] = null;
            } else {
                break;
            }
        }
        size = 0;
    }

    void save(Resume r) {
        int lastIndex = size;
        int sameResume = IntStream.range(0, lastIndex)
                .filter(resume -> r.uuid.equals(storage[resume].uuid))
                .findFirst()
                .orElse(-1);
        if (lastIndex > storage.length) {
            System.out.println("В массиве закончилось место");
        } else {
            if (sameResume == -1) {
                storage[lastIndex] = r;
                System.out.println("resume " + r.uuid + " was added");
                size++;
            } else {
                System.out.println("List's already have resume with uuid = " + r.uuid);
            }
        }
    }

    Resume get(String uuid) {
        try {
            Resume returnResume = Arrays.stream(storage)
                    .filter(resume -> resume.uuid.equals(uuid))
                    .findFirst()
                    .get();
            return returnResume;
        } catch (NullPointerException e) {
            System.out.println("such resume with uuid = " + uuid + " not found in list");
        }
        return null;
    }

    void delete(String uuid) {
        try {
            int index = IntStream.range(0, storage.length)
                    .filter(resume -> storage[resume].uuid.equals(uuid))
                    .findFirst()
                    .orElse(-1);
            Resume[] copy = Arrays.copyOfRange(storage, index + 1, storage.length);
            System.arraycopy(copy, 0, storage, index, copy.length);
            size--;
        } catch (NullPointerException e) {
            System.out.println("such resume with uuid = " + uuid + " not found in list");
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int index = IntStream.range(0, storage.length)
                .filter(resume -> storage[resume] == null)
                .findFirst()
                .orElse(-1);
        return Arrays.copyOfRange(storage, 0, index);
    }

    int size() {
        return size;
    }
}
