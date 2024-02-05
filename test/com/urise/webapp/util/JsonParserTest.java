package com.urise.webapp.util;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.TextSection;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class JsonParserTest {

    @Test
    public void testSerializeResume() throws Exception {
        Resume resume = ResumeTestData.createResume("uuid1", "Alex");
        String json = JsonParser.write(resume);
        System.out.println(json);
        Resume resumeRead = JsonParser.read(json, Resume.class);
        Assert.assertEquals(resumeRead, resume);
    }

    @Test
    void write() {
        AbstractSection section = new TextSection("Some text");
        String json = JsonParser.write(section, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section, section2);
    }
}