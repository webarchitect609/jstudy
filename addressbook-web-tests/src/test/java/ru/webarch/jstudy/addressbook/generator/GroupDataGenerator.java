package ru.webarch.jstudy.addressbook.generator;

import ru.webarch.jstudy.addressbook.model.GroupData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator extends GeneratorBase {

    public static void main(String[] args) throws IOException {
        run(args, new GroupDataGenerator());
    }

    protected String getItemsName() {
        return "Groups";
    }

    @Override
    protected Class getType() {
        return GroupData.class;
    }

    protected List generate(int count) {
        List<GroupData> list = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            list.add(
                    new GroupData()
                    .withName(String.format("Sample Group %d", i))
                    .withHeader(String.format("Samle Header %d", i))
                    .withFooter(String.format("Samle Footer %d", i))
            );
        }

        return list;
    }
}
