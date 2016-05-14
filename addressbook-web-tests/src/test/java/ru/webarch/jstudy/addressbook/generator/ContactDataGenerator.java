package ru.webarch.jstudy.addressbook.generator;

import ru.webarch.jstudy.addressbook.model.ContactData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator extends GeneratorBase {

    public static void main(String[] args) throws IOException {
        run(args, new ContactDataGenerator());
    }

    protected String getItemsName() {
        return "Contacts";
    }

    @Override
    protected Class getType() {
        return ContactData.class;
    }

    protected List generate(int count) {
        List<ContactData> list = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            list.add(
                    new ContactData()
                        .withFirstName("FirstName " + i)
                    .withLastName("LastName "+i)
                    .withEmail(String.format("email%d@example.com", i))
                    .withMobilePhone(String.format("+798590%d0%d0%d", i,i ,i ))
                    .withNotes("Generated Contact #" + i)
            );
        }

        return list;
    }
}
