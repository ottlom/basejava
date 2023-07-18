package com.urise.webapp.storage.serialization;

import com.urise.webapp.model.*;
import com.urise.webapp.util.LocalDateAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializations {

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
            LocalDateAdapter localDateAdapter = new LocalDateAdapter();
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                st.writeUTF(entry.getKey().name());
                SectionType typeSection = entry.getKey();
                switch (typeSection) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection textSection = (TextSection) entry.getValue();
                        st.writeUTF(textSection.toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection listSection = (ListSection) entry.getValue();
                        st.writeUTF(listSection.toString());
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        CompanySection companySection = (CompanySection) entry.getValue();
                        for (int i = 0; i < companySection.getCompanies().size(); i++) {
                            st.writeUTF(companySection.getCompanies().get(i).getName());
                            st.writeUTF(companySection.getCompanies().get(i).getWebsite());
                            for (int j = 0; j < companySection.getCompanies().get(i).getCompanyPeriods().size(); j++) {
                                try {
                                    st.writeUTF(localDateAdapter.marshal(companySection.getCompanies().get(i).getCompanyPeriods().get(j).getStartPeriod()));
                                    st.writeUTF(localDateAdapter.marshal(companySection.getCompanies().get(i).getCompanyPeriods().get(j).getEndPeriod()));
                                    st.writeUTF(companySection.getCompanies().get(i).getCompanyPeriods().get(j).getDescription());
                                    st.writeUTF(companySection.getCompanies().get(i).getCompanyPeriods().get(j).getTitle());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                }
            }
        } catch (IOException e) {
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
                        LocalDateAdapter localDateAdapter = new LocalDateAdapter();
                        Company company = new Company(dis.readUTF(), dis.readUTF());
                        try {
                            Company.Period period = new Company.Period(localDateAdapter.unmarshal(dis.readUTF()),
                                    localDateAdapter.unmarshal(dis.readUTF()),
                                    dis.readUTF(),
                                    dis.readUTF());
                            company.getCompanyPeriods().add(period);
                            companySectionContent.add(company);
                            resume.addSection(sectionType, new CompanySection(companySectionContent));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
            return resume;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resume;
    }
}
