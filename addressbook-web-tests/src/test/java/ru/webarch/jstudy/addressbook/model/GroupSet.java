package ru.webarch.jstudy.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.Set;


public class GroupSet extends ForwardingSet<GroupData> {

    private Set<GroupData> delegate;

    public GroupSet() {
        this.delegate = new HashSet<>();
    }

    public GroupSet(GroupSet groupSet) {
        this.delegate = new HashSet<>(groupSet.delegate());
    }

    @Override
    protected Set<GroupData> delegate() {
        return delegate;
    }

    public GroupSet with(GroupData group) {
        GroupSet groupSet = new GroupSet(this);
        groupSet.add(group);
        return groupSet;
    }

    public GroupSet without(GroupData group) {
        GroupSet groupSet = new GroupSet(this);
        groupSet.remove(group);
        return groupSet;
    }

}
