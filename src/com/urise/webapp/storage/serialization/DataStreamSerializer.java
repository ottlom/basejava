package com.urise.webapp.storage.serialization;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializations {
    private void writeLocalDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
        dos.writeInt(date.getDayOfMonth());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
    }


    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, ConsumerCollectionElement<T> collectionElement) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            try {
                collectionElement.acceptElement(element);
            } catch (IOException e) {
                throw new IOException("IO exception while write to DataStream");
            }
        }
    }

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
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int sizeContentListSection = dis.readInt();
                        ArrayList<String> listSectionContent = new ArrayList<>();
                        for (int j = 0; j < sizeContentListSection; j++) {
                            listSectionContent.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(listSectionContent));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int sizeContentCompanySection = dis.readInt();
                        List<Company> companySectionContent = new ArrayList<>();
                        for (int j = 0; j < sizeContentCompanySection; j++) {
                            Company company = new Company(dis.readUTF(), dis.readUTF());
                            int sizePeriodsOfCompany = dis.readInt();
                            for (int k = 0; k < sizePeriodsOfCompany; k++) {
                                Company.Period period = new Company.Period(readLocalDate(dis),
                                        readLocalDate(dis),
                                        dis.readUTF(),
                                        dis.readUTF());
                                company.getCompanyPeriods().add(period);
                            }
                            companySectionContent.add(company);
                        }
                        resume.addSection(sectionType, new CompanySection(companySectionContent));
                        break;
                }
            }
            return resume;
        }
    }
}

