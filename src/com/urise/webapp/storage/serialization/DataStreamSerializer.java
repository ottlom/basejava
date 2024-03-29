package com.urise.webapp.storage.serialization;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializations {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream st = new DataOutputStream(os)) {
            st.writeUTF(r.getUuid());
            st.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeWithException(contacts.entrySet(), st, (entryContactType) -> {
                st.writeUTF(entryContactType.getKey().name());
                st.writeUTF(entryContactType.getValue());
            });

            Map<SectionType, AbstractSection> sections = r.getSections();
            writeWithException(sections.entrySet(), st, (entrySection) -> {
                st.writeUTF(entrySection.getKey().name());
                SectionType typeSection = entrySection.getKey();
                switch (typeSection) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection textSection = (TextSection) entrySection.getValue();
                        st.writeUTF(textSection.getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection listSection = (ListSection) entrySection.getValue();
                        writeWithException(listSection.getList(), st, st::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        CompanySection companySection = (CompanySection) entrySection.getValue();
                        writeWithException(companySection.getCompanies(), st, (entryCompanySection) -> {
                            st.writeUTF(entryCompanySection.getName());
                            st.writeUTF(entryCompanySection.getWebsite());
                            writeWithException(entryCompanySection.getCompanyPeriods(), st, (entryPeriods) -> {
                                writeLocalDate(st, entryPeriods.getStartPeriod());
                                writeLocalDate(st, entryPeriods.getEndPeriod());
                                st.writeUTF(entryPeriods.getDescription());
                                st.writeUTF(entryPeriods.getTitle());
                            });
                        });
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        Resume resume;
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            resume = new Resume(uuid, fullName);
            readWithException(dis, () -> {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            });

            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ArrayList<String> listSectionContent = new ArrayList<>();
                        readWithException(dis, () -> {
                            listSectionContent.add(dis.readUTF());
                            resume.addSection(sectionType, new ListSection(listSectionContent));
                        });
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Company> companySectionContent = new ArrayList<>();
                        readWithException(dis, () -> {
                            Company company = new Company(dis.readUTF(), dis.readUTF());
                            readWithException(dis, () -> {
                                Company.Period period = new Company.Period(readLocalDate(dis),
                                        readLocalDate(dis),
                                        dis.readUTF(),
                                        dis.readUTF());
                                company.getCompanyPeriods().add(period);
                            });
                            companySectionContent.add(company);
                        });
                        resume.addSection(sectionType, new CompanySection(companySectionContent));
                        break;
                }
            });
            return resume;
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
        dos.writeInt(date.getDayOfMonth());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, CollectionWriter<T> collectionElement) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            collectionElement.acceptElement(element);
        }
    }

    private void readWithException(DataInputStream dis, ElementReader collectionElement) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            collectionElement.acceptElement();
        }
    }
}


