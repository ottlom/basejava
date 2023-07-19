package com.urise.webapp.storage.serialization;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializations {
    private void writeLocalDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
        dos.writeInt(date.getDayOfMonth());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(),dis.readInt(),dis.readInt());
    }

    @Override
    public void doWrite(Resume r, OutputStream os) {
        try (DataOutputStream st = new DataOutputStream(os)) {
            st.writeUTF(r.getUuid());
            st.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            st.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                st.writeUTF(entry.getKey().name());
                st.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> sections = r.getSections();
            st.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                st.writeUTF(entry.getKey().name());
                SectionType typeSection = entry.getKey();
                switch (typeSection) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection textSection = (TextSection) entry.getValue();
                        st.writeUTF(textSection.getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection listSection = (ListSection) entry.getValue();
                        for (String contentListSection : listSection.getList()) {
                            st.writeUTF(contentListSection);
                        }
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        CompanySection companySection = (CompanySection) entry.getValue();
                        for (Company company : companySection.getCompanies()) {
                            st.writeUTF(company.getName());
                            st.writeUTF(company.getWebsite());
                            for (Company.Period period : company.getCompanyPeriods()) {
                                writeLocalDate(st,period.getStartPeriod());
                                writeLocalDate(st,period.getEndPeriod());
                                st.writeUTF(period.getDescription());
                                st.writeUTF(period.getTitle());
                            }
                        }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resume doRead(InputStream is) {
        Resume resume = new Resume();
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
                        ArrayList<String> listSectionContent = new ArrayList<>();
                        listSectionContent.add(dis.readUTF());
                        resume.addSection(sectionType, new ListSection(listSectionContent));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Company> companySectionContent = new ArrayList<>();
                        Company company = new Company(dis.readUTF(), dis.readUTF());

                        Company.Period period = new Company.Period(readLocalDate(dis),
                                readLocalDate(dis),
                                dis.readUTF(),
                                dis.readUTF());
                        company.getCompanyPeriods().add(period);
                        companySectionContent.add(company);
                        resume.addSection(sectionType, new CompanySection(companySectionContent));
                        break;
                }
            }
            return resume;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resume;
    }
}

